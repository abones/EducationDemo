package ru.sibhtc.educationdemo.services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import ru.sibhtc.educationdemo.MainActivity;
import ru.sibhtc.educationdemo.constants.IntentTypes;
import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.models.TestSendModel;

/**
 * Created by Антон on 15.09.2015.
 **/
public class WearMessageListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        byte[] data =  messageEvent.getData();
        String message = "";
        switch (messageEvent.getPath()){
            case MessagePaths.TEST_MESSAGE_PATH:{
                message = new String(data);
                showToast(message);
                break;
            }
            case MessagePaths.OBJECT_MESSAGE_PATH:{
                try
                {
                    TestSendModel test = (TestSendModel) BytesHelper.toObject(data);
                    message = "From object:" + test.Message;
                }
                catch (Exception ex)
                {
                    message = "Ошибка распаковки";
                }
                showToast(message);
                break;
            }
            case MessagePaths.ERROR_MESSAGE_PATH:{
                message = new String(data);
                showToast(message);
                break;
            }
            case MessagePaths.INFO_MESSAGE_PATH:{
                Intent intent =  new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", IntentTypes.Info);
                bundle.putByteArray("infoArray", data);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case MessagePaths.PROGRESS_MESSAGE_PATH:{
                Intent intent =  new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", IntentTypes.Progress);
                bundle.putByteArray("infoArray", data);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case MessagePaths.LOGICAL_MESSAGE_PATH:{
                Intent intent =  new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", IntentTypes.Logical);
                bundle.putByteArray("infoArray", data);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case MessagePaths.EXAM_MESSAGE_PATH:{
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", IntentTypes.Exam);
                bundle.putByteArray("infoArray", data);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
