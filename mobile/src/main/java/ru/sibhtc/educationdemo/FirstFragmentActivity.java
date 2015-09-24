package ru.sibhtc.educationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.InfoObject;
import ru.sibhtc.educationdemo.models.ProgressObject;
import ru.sibhtc.educationdemo.models.TestSendModel;
import ru.sibhtc.educationdemo.services.SiteData;

/**
 * Created by Антон on 15.09.2015.
 **/
public class FirstFragmentActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {
    private final String TEST_MESSAGE_PATH = "/message";
    private final String OBJECT_MESSAGE_PATH = "/object";
    private final String ERROR_MESSAGE_PATH = "/error";
    private final String INFO_MESSAGE_PATH = "/info";
    private final String PROGRESS_MESSAGE_PATH = "/progress";
    private static final String START_ACTIVITY = "/start_activity";

    private Button sendButton;
    private Button sendObject;
    private Button sendInfo;
    private Button sendProgress;
    private TextView receivedMessagesTextView;
    private EditText sendMessage;
    private Button getInfoFromServerButton;

    private GoogleApiClient apiClient;

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

    private void writeParameters(String parameters){
        String str = parameters;
    }

    private void init(){
        //тестовые кнопули
        sendButton = (Button) findViewById(R.id.test_btn);
        sendObject = (Button) findViewById(R.id.object_btn);
        sendInfo = (Button) findViewById(R.id.info_btn);
        sendProgress = (Button)findViewById(R.id.progress_btn);
        sendMessage = (EditText) findViewById(R.id.editText);
        getInfoFromServerButton = (Button) findViewById(R.id.get_info_from_server_btn);

        getInfoFromServerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SiteData SiteData = new SiteData(){
                    @Override
                    public void callbackFunction(String result){
                        writeParameters(result);
                    }
                };
                SiteData.execute(getString(R.string.url_parameters));
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = sendMessage.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    sendMessage(TEST_MESSAGE_PATH, text.getBytes());
                }
            }
        });

        sendObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestSendModel test = new TestSendModel(1, "Object");
                byte[] data;
                String path;

                try
                {
                    data = BytesHelper.toByteArray(test);
                    path = OBJECT_MESSAGE_PATH;
                }
                catch (Exception ex)
                {
                    data = ex.getMessage().getBytes();
                    path = ERROR_MESSAGE_PATH;
                }

            }
        });

        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoObject info = new InfoObject("Lorem Ipsum", "Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem");
                byte[] data;
                String path;

                try
                {
                    data = BytesHelper.toByteArray(info);
                    path = INFO_MESSAGE_PATH;
                }
                catch (Exception ex)
                {
                    data = ex.getMessage().getBytes();
                    path = ERROR_MESSAGE_PATH;
                }
                sendMessage(path, data);
            }
        });

        sendProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressObject progress = new ProgressObject("Lorem Ipsum", "Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem Lorem", 80, 100, "кг");
                byte[] data;
                String path;

                try
                {
                    data = BytesHelper.toByteArray(progress);
                    path = PROGRESS_MESSAGE_PATH;
                }
                catch (Exception ex)
                {
                    data = ex.getMessage().getBytes();
                    path = ERROR_MESSAGE_PATH;
                }
                sendMessage(path, data);
            }
        });

    }

    private void sendMessage( final String path, final byte[] data ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( apiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            apiClient, node.getId(), path, data ).await();
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
        sendMessage(START_ACTIVITY, "".getBytes());
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
