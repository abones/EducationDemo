package ru.sibhtc.educationdemo.helpers;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import ru.sibhtc.educationdemo.models.AppState;

/**
 * Created by Антон on 24.09.2015.
 **/
public class GlobalHelper {
    public static AppState appState;
    public static GoogleApiClient apiClient;
    public static String nodeId;

    public static void sendMessage( final String path, final byte[] data ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                        apiClient, nodeId, path, data ).await();
            }
        }).start();
    }

}
