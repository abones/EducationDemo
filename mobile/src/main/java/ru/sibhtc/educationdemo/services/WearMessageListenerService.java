package ru.sibhtc.educationdemo.services;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import ru.sibhtc.educationdemo.MainActivity;

/**
 * Created by Антон on 20.09.2015.
 **/
public class WearMessageListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        byte[] data =  messageEvent.getData();
        String message = new String(data);
        showToast(message);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}