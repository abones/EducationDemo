package ru.sibhtc.educationdemo.helpers;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import ru.sibhtc.educationdemo.mock.AppMode;

import static com.google.android.gms.internal.zzhu.runOnUiThread;

/**
 * Created by Антон on 23.09.2015.
 **/
public class GlobalHelper {
    public static AppMode CurrentAppMode;
    public static long CurrentProgramId;
    public static long CurrentStudentId;
    public static long CurrentStepId;
    public static String ExpectedLableId;
    public static GoogleApiClient apiClient;

    public static void sendMessage( final String path, final byte[] data ) {
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
                        //DoNothing
                    }
                });
            }
        }).start();
    }

}
