package ru.sibhtc.educationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import static com.google.android.gms.internal.zzhu.runOnUiThread;

/**
 * Created by Антон on 15.09.2015.
 **/
public class FirstFragmentActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {
    private final String TEST_MESSAGE_PATH = "/message";
    private static final String START_ACTIVITY = "/start_activity";

    private Button sendButton;
    private TextView receivedMessagesTextView;
    private EditText sendMessage;
    private ListView listView;

    private GoogleApiClient apiClient;
    private ArrayAdapter<String> mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_one);

        init();
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        apiClient = new GoogleApiClient.Builder(this)
                .addApi( Wearable.API )
                .build();

        apiClient.connect();
    }

    private void init(){
        sendButton = (Button) findViewById(R.id.test_btn);
        sendMessage = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 );
        listView.setAdapter(mAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = sendMessage.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    mAdapter.add(text);
                    mAdapter.notifyDataSetChanged();

                    sendMessage(TEST_MESSAGE_PATH, text);
                }
            }
        });

    }

    private void sendMessage( final String path, final String text ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( apiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            apiClient, node.getId(), path, text.getBytes() ).await();
                }

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        sendMessage.setText( "" );
                    }
                });
            }
        }).start();
    }

    @Override
    public void onConnected(Bundle bundle) {
        sendMessage(START_ACTIVITY, "");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        apiClient.disconnect();
    }
}
