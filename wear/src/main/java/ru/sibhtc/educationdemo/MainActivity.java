package ru.sibhtc.educationdemo;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.CapabilityInfo;

import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.Set;

import ru.sibhtc.educationdemo.constants.IntentTypes;

public class MainActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks {

    private static final String WEAR_MESSAGE_PATH = "/message";
    private static final String EDUCATION_DEMO_CAPABILITY_NAME = "EDUCATION_DEMO";
    private int selectedLabel;

    String LOG_TAG = MainActivity.class.getSimpleName();

    private GoogleApiClient apiClient;
    private Spinner spinner;
    private Button button;
    private String nodeId;

    private static final long CONNECTION_TIME_OUT_MS = 100;
    private static final String MOBILE_PATH = "/mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initGoogleApiClient();
        init();
    }


    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (intent != null){
            String type = intent.getStringExtra("type");
            if (type != null) {
                switch (type) {
                    case IntentTypes.Info: {
                        Fragment fragment = new InfoFragment();
                        Bundle bundle = intent.getExtras();
                        byte[] bytes = bundle.getByteArray("infoArray");
                        Bundle fragBundle = new Bundle();
                        fragBundle.putByteArray("info", bytes);
                        fragment.setArguments(fragBundle);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        if (fragmentManager.findFragmentById(R.id.watchDataFrame) != null) {
                            fragmentTransaction.replace(R.id.watchDataFrame, fragment, "INFO");
                        } else {
                            fragmentTransaction.add(R.id.watchDataFrame, fragment, "INFO");
                        }
                        fragmentTransaction.commit();
                    }
                }
            }

        }
    }

    private void initGoogleApiClient() {
        apiClient = getGoogleApiClient(this);
        apiClient.connect();
        retrieveDeviceNode();
    }

    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();

    }

    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(apiClient).await();

                List<Node> nodes = result.getNodes();

                if (nodes.size() > 0)
                    nodeId = nodes.get(0).getId();

                if (nodeId.equals("cloud") && nodes.size() > 1){
                    nodeId = nodes.get(1).getId();
                }

                Log.v(LOG_TAG, "Node ID of phone: " + nodeId);
            }
        }).start();

       /*new Thread(new Runnable() {
            @Override
            public void run() {
                CapabilityApi.GetCapabilityResult result =
                        Wearable.CapabilityApi.getCapability(
                                apiClient, EDUCATION_DEMO_CAPABILITY_NAME,
                                CapabilityApi.FILTER_REACHABLE).await();

                updateTranscriptionCapability(result.getCapability());

                Log.d(LOG_TAG, "Node ID of phone: " + nodeId);
            }
        }).start();*/


    }

    private void updateTranscriptionCapability(CapabilityInfo capability) {
        Set<Node> connectedNodes = capability.getNodes();
        nodeId = pickBestNodeId(connectedNodes);
    }

    private String pickBestNodeId(Set<Node> nodes) {
        String bestNodeId = null;
        // Find a nearby node or pick one arbitrarily
        for (Node node : nodes) {
            if (node.isNearby()) {
                return node.getId();
            }
            bestNodeId = node.getId();
        }
        return bestNodeId;
    }

    private void init(){
        spinner = (Spinner)findViewById(R.id.spinner);
        button = (Button)findViewById(R.id.button);

        String[] data = new String[LabelMock.labels.length];

        for (int index = 0; index < LabelMock.labels.length; index++){
            data[index] = LabelMock.labels[index].Name;
        }

        ArrayAdapter labelsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);
        labelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(labelsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLabel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(WEAR_MESSAGE_PATH, Integer.toString(selectedLabel));

            }
        });
    }

    private void sendMessage( final String path, final String text ) {
        if (nodeId != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                        String message = "My message";
                        Wearable.MessageApi.sendMessage(apiClient, nodeId, MOBILE_PATH, message.getBytes()).await();
                }
            }).start();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        //sendMessage(START_ACTIVITY, "");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        apiClient.disconnect();
    }


    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
