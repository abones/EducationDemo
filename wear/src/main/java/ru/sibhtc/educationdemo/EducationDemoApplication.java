package ru.sibhtc.educationdemo;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.models.AppState;

/**
 * Created by Антон on 24.09.2015.
 **/
public class EducationDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalHelper.appState = AppState.Helper;
        initGoogleApiClient();
        retrieveDeviceNode();
    }

    private void initGoogleApiClient() {
        GlobalHelper.apiClient = new GoogleApiClient.Builder(this)
                .addApi( Wearable.API )
                .build();
        GlobalHelper.apiClient.connect();
    }

    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(GlobalHelper.apiClient).await();
                String node = "";


                List<Node> nodes = result.getNodes();

                if (nodes.size() > 0)
                    node = nodes.get(0).getId();

                if (node.equals("cloud") && nodes.size() > 1){
                    node = nodes.get(1).getId();
                }

                GlobalHelper.nodeId = node;

                Log.v("EducationDemo", "Node ID of phone: " + node);
            }
        }).start();
    }

    @Override
    public void onTerminate() {
        GlobalHelper.apiClient.disconnect();
        super.onTerminate();
    }
}
