package ru.sibhtc.educationdemo.services;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.IOException;

import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.AppMode;
import ru.sibhtc.educationdemo.models.Label;
import ru.sibhtc.educationdemo.mock.LabelsMock;
import ru.sibhtc.educationdemo.models.LabelNFC;
import ru.sibhtc.educationdemo.models.MessageModel;

/**
 * Created by Антон on 20.09.2015.
 **/
public class WearMessageListenerService extends WearableListenerService {

    private MessageModel messageModel;
    private GoogleApiClient apiClient;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(MessagePaths.LABEL_MESSAGE_PATH)) {

            if (GlobalHelper.CurrentAppMode == AppMode.INFORMATION_SENDER) {
                //отправляем сообщение о запрошенном объекте
                try {
                    byte[] object = messageEvent.getData();
                    messageModel = (MessageModel) BytesHelper.toObject(object);
                    sendInformationMessage(messageModel.labelCode);
                }
                catch (IOException e){
                    GlobalHelper.showToast(this, e.getMessage());
                }//
                catch (ClassNotFoundException e){

                }
            } else if (GlobalHelper.CurrentAppMode == AppMode.LEARNING) {
                //если обучение, то просто смотрим верно ли прислонили метку
                //если верно то помечаем и идем далее иначе продолжаем ждать верный ответ
                try {
                    byte[] object = messageEvent.getData();
                    messageModel = (MessageModel) BytesHelper.toObject(object);
                    if (messageModel.isValued) {
                        //TODO заглушка для локальной сети
                        GlobalHelper.setMockModelParameters();
                        studyAnswer();
                        /*GlobalHelper.getServerInfo(new ICallbackInterface() {
                            @Override
                            public void onDownloadFinished() {
                                studyAnswer();
                            }
                        });*/

                    }else
                    {
                        studyAnswer();
                    }

                }
                catch (IOException e){
                    GlobalHelper.showToast(this, e.getMessage());
                }//
                catch (ClassNotFoundException e){

                }
            }
            else if (GlobalHelper.CurrentAppMode == AppMode.EXAMINE) {
                //если экзамен, то всегда продолжаем выполнять задание
                //
                try {
                    byte[] object = messageEvent.getData();
                    messageModel = (MessageModel) BytesHelper.toObject(object);
                    if (messageModel.isValued) {
                        //TODO заглушка для локальной сети
                        GlobalHelper.setMockModelParameters();
                        examAnswer();
                        /*GlobalHelper.getServerInfo(new ICallbackInterface() {
                            @Override
                            public void onDownloadFinished() {
                                examAnswer();
                            }
                        });*/


                    }else
                    {
                        examAnswer();
                    }

                }
                catch (IOException e){
                    GlobalHelper.showToast(this, e.getMessage());
                }//
                catch (ClassNotFoundException e){

                }
            }
        } else {
            byte[] data = messageEvent.getData();
            String message = new String(data);
            GlobalHelper.showToast(this, message);
        }
    }

    //для ассинхронных запросов к сайту при обучении
    private void studyAnswer(){
        GlobalHelper.getLearningFragment().wearAnswer(messageModel);
    }
    //для ассинхронных запросов к сайту при обучении
    private void examAnswer(){
        GlobalHelper.getExamFragment().wearAnswer(messageModel);
    }

    private void sendInformationMessage(String code) {
        LabelNFC label = new LabelNFC();
        label.labelDescription = LabelsMock.getByCode(code).labelDescription;
        boolean isGeneratedArray;
        String path = "";
        if (label != null) {
            byte[] data;
                try {
                    data = BytesHelper.toByteArray(label);
                    isGeneratedArray = true;
                    path = MessagePaths.INFO_MESSAGE_PATH;
                } catch (Exception ex) {
                    data = new byte[]{};
                    isGeneratedArray = false;
                }
            if (isGeneratedArray) {
                GlobalHelper.sendMessage(path, data);
            }
        }
    }


}