package com.example.nfc_app;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String Error_Detected = "No NFC on phone";
    public static final String Write_Success = "Text Written Successfully";
    public static final String Write_Error = "Error";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writing_tag_fillter[];
    boolean writemode;
    Tag mytag;
    Context context;
    int wrong = 0;
    TextView wrong5times;
    TextView nfc_content;
    TextView UID;
    Button btnOk;
    DatabaseReference reference, reference1, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        wrong5times = findViewById(R.id.tvTime);
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    if (mytag == null) {
//                        Toast.makeText(context, Error_Detected, Toast.LENGTH_SHORT).show();
//                    } else
//                        write(edit_message.getText().toString(), mytag);
//                    Toast.makeText(context, Write_Success, Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                } catch (FormatException e) {
//                    Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        });
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "No NFC on this phone", Toast.LENGTH_SHORT).show();
            finish();
        }
        readfromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writing_tag_fillter = new IntentFilter[]{tagDetected};
    }

    private void readfromIntent(Intent intent) {
        if (wrong != 5) {
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                Parcelable[] rawMsg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                NdefMessage[] msgs = null;
                if (rawMsg != null) {
                    msgs = new NdefMessage[rawMsg.length];
                    for (int i = 0; i < rawMsg.length; i++) {
                        msgs[i] = (NdefMessage) rawMsg[i];
                    }
                }
                buildTagView(msgs);
                String noidung = this.ByteArrayToHexString(getIntent().getByteArrayExtra(NfcAdapter.EXTRA_ID));
                if (getIntent().getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkuser = reference.orderByChild("uid").equalTo(noidung);
                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String UIDFromDB = snapshot.child(noidung).child("uid").getValue(String.class);
                                String NameFromDB = snapshot.child(noidung).child("name").getValue(String.class);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy 'lúc' HH:mm:ss");
                                String currentTime = sdf.format(new Date());
                                reference1 = FirebaseDatabase.getInstance().getReference("History");
                                UserTimeHelperClass timehelperClass = new UserTimeHelperClass(currentTime, NameFromDB, UIDFromDB);
                                reference1.child(currentTime).setValue(timehelperClass);
                                if (UIDFromDB.equals("3ACF6A81")) {
                                    Intent intent = new Intent(getApplicationContext(), FirstFragment.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Welcome " + NameFromDB, Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), GuestActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Welcome " + NameFromDB, Toast.LENGTH_SHORT).show();
                                }
                            } else
                                wrong++;
                            Toast.makeText(getApplicationContext(), "User không tồn tại", Toast.LENGTH_SHORT).show();
                            if (wrong == 5) {
                                wrong5times.setVisibility(View.VISIBLE);
                                new CountDownTimer(30000, 1000) {
                                    @Override
                                    public void onTick(long l) {
                                        wrong5times.setText("Bạn đã sai NFC 5 lần. Xin thử lại sau " + l / 1000 + " giây");
                                    }

                                    @Override
                                    public void onFinish() {
                                        wrong = 0;
                                        wrong5times.setVisibility(View.GONE);
                                    }
                                }.start();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        }
    }


    private void buildTagView(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0)
            return;
        String text = "";
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        try {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
        //nfc_content.setText(text);
    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        payload[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readfromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        writemodeOff();
    }

    @Override
    protected void onResume() {
        super.onResume();
        writemodeOn();
    }

    @Override
    protected void onDestroy() {
        Set0();
        super.onDestroy();
    }

    private void Set0() {
        reference2 = FirebaseDatabase.getInstance().getReference("Close");
        reference2.setValue("0");
        reference2 = FirebaseDatabase.getInstance().getReference("Open");
        reference2.setValue("0");
        reference2 = FirebaseDatabase.getInstance().getReference("Hold");
        reference2.setValue("0");
    }

    private void writemodeOff() {
        writemode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void writemodeOn() {
        writemode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writing_tag_fillter, null);
    }

    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";
        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }
}