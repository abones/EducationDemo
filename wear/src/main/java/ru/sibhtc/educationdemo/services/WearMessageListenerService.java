package ru.sibhtc.educationdemo.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import ru.sibhtc.educationdemo.MainActivity;
import ru.sibhtc.educationdemo.constants.IntentTypes;
import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.models.TestSendModel;

/**
 * Created by Антон on 15.09.2015.
 **/
public class WearMessageListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        byte[] data = messageEvent.getData();

        String intentType = null;

        switch (messageEvent.getPath()) {
            case MessagePaths.ERROR_MESSAGE_PATH: {
                String message = new String(data);
                Vibrator vibratorNFCCheck = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibratorNFCCheck.vibrate(300);
                showToast(message);
                break;
            }
            case MessagePaths.EXAM_EVENT_MESSAGE_PATH: {
                intentType = IntentTypes.Exam;
                break;
            }
            case MessagePaths.STUDY_EVENT_MESSAGE_PATH: {
                intentType = IntentTypes.Learning;
                break;
            }
            case MessagePaths.INFO_MESSAGE_PATH: {
                intentType = IntentTypes.Info;
                break;
            }
            case MessagePaths.INFO_START_MESSAGE_PATH:{
                intentType = IntentTypes.Waiting;
            }
        }

        if (intentType != null) {
            if (GlobalHelper.mainActivity == null || GlobalHelper.mainActivity.isDestroyed()) {
                prepareAndStartActivity(intentType, data);
            } else {
                GlobalHelper.mainActivity.setIntentType(intentType);
                GlobalHelper.mainActivity.changeInformation(data);
            }
        }
    }


    private void prepareAndStartActivity(String intentType, byte[] data) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", intentType);
        bundle.putByteArray("infoArray", data);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        GlobalHelper.mainActivity.setIsIntentWasDestroid(true);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0 );
        toast.show();
    }


}
