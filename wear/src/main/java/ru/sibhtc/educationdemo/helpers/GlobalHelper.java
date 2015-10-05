package ru.sibhtc.educationdemo.helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import ru.sibhtc.educationdemo.MainActivity;
import ru.sibhtc.educationdemo.models.AppState;

/**
 * Created by Антон on 24.09.2015.
 **/
public class GlobalHelper {
    public static AppState appState;
    public static GoogleApiClient apiClient;
    public static String nodeId;

    public static MainActivity mainActivity;


    private static final int sizeOfIntInHalfBytes = 8;
    private static final int numberOfBitsInAHalfByte = 4;
    private static final int halfByte = 0x0F;
    private static final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static Integer fragmentNumber = 0;

    public static void sendMessage( final String path, final byte[] data ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                        apiClient, nodeId, path, data ).await();
            }
        }).start();
    }

    public static String decToHex(int dec) {
        StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
        hexBuilder.setLength(sizeOfIntInHalfBytes);
        for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i)
        {
            int j = dec & halfByte;
            hexBuilder.setCharAt(i, hexDigits[j]);
            dec >>= numberOfBitsInAHalfByte;
        }
        return hexBuilder.toString();
    }



}
