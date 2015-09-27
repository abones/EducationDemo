package ru.sibhtc.educationdemo.services;

import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.AppMode;
import ru.sibhtc.educationdemo.models.Label;
import ru.sibhtc.educationdemo.mock.LabelsMock;
import ru.sibhtc.educationdemo.models.InfoObject;
import ru.sibhtc.educationdemo.models.LogicalObject;
import ru.sibhtc.educationdemo.models.ProgressObject;

import static com.google.android.gms.internal.zzhu.runOnUiThread;

/**
 * Created by Антон on 20.09.2015.
 **/
public class WearMessageListenerService extends WearableListenerService {


    private GoogleApiClient apiClient;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(MessagePaths.LABEL_MESSAGE_PATH)) {

            if (GlobalHelper.CurrentAppMode == AppMode.INFORMATION_SENDER) {
                //отправляем сообщение о запрошенном объекте
                String code = new String(messageEvent.getData());
                sendInformationMessage(code);
            } else if (GlobalHelper.CurrentAppMode == AppMode.LEARNING) {
                //если обучение, то просто смотрим верно ли прислонили метку
                //если верно то помечаем и идем далее иначе продолжаем ждать верный ответ
                String code = new String(messageEvent.getData());
                GlobalHelper.getLearningFragment().wearAnswer(code);
            }
        } else {
            byte[] data = messageEvent.getData();
            String message = new String(data);
            showToast(message);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void sendInformationMessage(String code) {
        Label label = LabelsMock.getByCode(code);
        boolean isGeneratedArray = false;
        String path = "";
        if (label != null) {
            byte[] data;
            if (!label.IsValued) {
                InfoObject info = new InfoObject(label.LabelName, label.LabelDescription);
                try {
                    data = BytesHelper.toByteArray(info);
                    isGeneratedArray = true;
                    path = MessagePaths.INFO_MESSAGE_PATH;
                } catch (Exception ex) {
                    data = new byte[]{};
                    isGeneratedArray = false;
                }
            } else {
                if (label.IsBool) {
                    LogicalObject logicalObject = new LogicalObject(label.LabelName, label.LabelDescription, true, label.TrueValue, label.FalseValue);
                    try {
                        data = BytesHelper.toByteArray(logicalObject);
                        isGeneratedArray = true;
                        path = MessagePaths.LOGICAL_MESSAGE_PATH;
                    } catch (Exception ex) {
                        data = new byte[]{};
                        isGeneratedArray = false;
                    }
                } else {
                    ProgressObject progressObject = new ProgressObject(label.LabelName, label.LabelDescription, 0.4, 1, label.ValueMeasure);
                    try {
                        data = BytesHelper.toByteArray(progressObject);
                        isGeneratedArray = true;
                        path = MessagePaths.PROGRESS_MESSAGE_PATH;
                    } catch (Exception ex) {
                        data = new byte[]{};
                        isGeneratedArray = false;
                    }
                }
            }
            if (isGeneratedArray) {
                GlobalHelper.sendMessage(path, data);
            }
        }
    }


}