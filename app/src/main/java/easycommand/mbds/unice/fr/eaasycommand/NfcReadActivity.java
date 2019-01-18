package easycommand.mbds.unice.fr.eaasycommand;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class NfcReadActivity extends MyNfcBaseActivity {
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String MIME_APP = "application/octet-stream";
    public static final String TAG = "NfcDemo";

    private String type;
    private String[] technology;
    private int size;
    private byte[] ID_tag;

    static String copyIdResto = "";
    static String copyIdTable = "";

    private LinearLayout mContentLayout;
    private RelativeLayout mScanLayout;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_read);

        mScanLayout = (RelativeLayout) findViewById(R.id.scan_layout);
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
        info = (TextView)findViewById(R.id.info_textView);
    }



    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            technology = tag.getTechList();
            Ndef ndef = Ndef.get(tag);
            size = ndef.getMaxSize();
            type = intent.getType();
            ID_tag = tag.getId();

            if (MIME_TEXT_PLAIN.equals(type) || MIME_APP.equals(type)) {
                new NdefReaderTask().execute(tag);
                Intent it = new Intent(NfcReadActivity.this,MenuActivity.class);
                startActivity(it);
            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            technology = tag.getTechList();
            Ndef ndef = Ndef.get(tag);
            size = ndef.getMaxSize();
            type = intent.getType();
            ID_tag = tag.getId();
            String searchedTech = Ndef.class.getName();

            for (String tech : technology) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
        else if (intent.getType() != null && intent.getType().equals("application/easycommand.mbds.unice.fr.eaasycommand")) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefRecord relayRecord = ((NdefMessage) rawMsgs[0]).getRecords()[0];
            NdefRecord relayRecord1 = ((NdefMessage) rawMsgs[0]).getRecords()[1];

            /*String idRest = new String(relayRecord.getPayload());
            String idTab = new String(relayRecord1.getPayload());

            Toast.makeText(this, "idResto: "+nfcData+", idTable: "+nfcData1, Toast.LENGTH_SHORT).show();*/
        }

    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Tag... params) {
            Tag tag = params[0];
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
            NdefRecord[] records = ndefMessage.getRecords();//all records
            ArrayList<String> list = new ArrayList<>();
            int i = 0;
            for (NdefRecord ndefRecord : records) {
                try {
                    list.add(readText(ndefRecord));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            byte[] payload = record.getPayload();
            // Get the Text Encoding
            String textEncoding = ((payload[0] & 128) == 0) ? new String("UTF-8") : "UTF-16";
            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;
            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);

        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            StringBuilder sb = new StringBuilder();
            if (result != null) {
                info.setText(" Scan successfuly !!! ");

                //transfert data nfc tag to MenuActivity
                copyIdResto = result.get(0);
                copyIdTable = result.get(1);

                /*Intent intent = new Intent(NfcReadActivity.this,MenuActivity.class);
                intent.putExtra("idResto", copyIdResto);
                intent.putExtra("idTable", copyIdTable);
                Bundle bundle = new Bundle();
                bundle.putString("idResto",copyIdResto);
                bundle.putString("idTable",copyIdTable);
                intent.putExtras(bundle);*/

            } else {
                Toast.makeText(getApplicationContext(), "TAG vide", Toast.LENGTH_SHORT).show();

            }

            mScanLayout.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.VISIBLE);
        }
    }

    public void onNewTag(Tag tag) {

        handleIntent(getIntent());

        /*Intent it = new Intent(NfcReadActivity.this,MenuActivity.class);
        startActivity(it);*/
    }
}
