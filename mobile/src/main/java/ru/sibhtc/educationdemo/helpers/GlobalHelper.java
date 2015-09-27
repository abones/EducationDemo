package ru.sibhtc.educationdemo.helpers;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import ru.sibhtc.educationdemo.ExamFragment;
import ru.sibhtc.educationdemo.LearningFragment;
import ru.sibhtc.educationdemo.constants.ApplicationConfigs;
import ru.sibhtc.educationdemo.mock.AppMode;
import ru.sibhtc.educationdemo.services.SiteDataService;

import static com.google.android.gms.internal.zzhu.runOnUiThread;

/**
 * Created by Антон on 23.09.2015.
 **/
public class GlobalHelper {

    static ICallbackInterface callbackInterface = null;

    public static AppMode CurrentAppMode;
    public static long CurrentProgramId;
    public static long CurrentStudentId;
    public static long CurrentStepId;
    public static String ExpectedLableId;
    public static GoogleApiClient apiClient;

    private static Map<String, String> modelParameters;
    private static Boolean modelParametersLoading;


    //ссылки на фрагменты для взаимодействия с сообщеними из часов
    private static LearningFragment learningFragment;
    private static ExamFragment examFragment;

    public static ExamFragment getExamFragment() {
        return examFragment;
    }

    public static void setExamFragment(ExamFragment examFragment) {
        GlobalHelper.examFragment = examFragment;
    }

    public static LearningFragment getLearningFragment() {

        return learningFragment;
    }

    public static void setLearningFragment(LearningFragment learningFragment) {
        GlobalHelper.learningFragment = learningFragment;
    }

    public static String getModelParameterByLink(String link){
        String result = "";
        if (modelParameters == null){
            result = "";
        }
        else {
            result = modelParameters.get(link);
        }
        return result == null ? "" :result ;
    }

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

    /**
     *Опрашивает сервак о данных стенда
     *
     * */
    public static void getServerInfo(ICallbackInterface callbackInterfaceForFinished){
        callbackInterface = callbackInterfaceForFinished;
        SiteDataService SiteData = new SiteDataService(){
            @Override
            public void callbackFunction(){
                writeParameters(getServerParameters());
            }
        };
        modelParametersLoading = true;
        SiteData.execute(ApplicationConfigs.SERVER_URL);
    }
    /**
    *Метод заполнения словаря даными с сервера
     * */
    private static void writeParameters(Map<String, String> parameters){
        //TODO Возможно логичнее очищать, в общем это нужно будет обсудить
        if (parameters.isEmpty()) return;

        if (modelParameters == null)
            modelParameters = new HashMap<String, String>();

        for (Map.Entry<String, String> entry: parameters.entrySet()){
            if (modelParameters.get(entry.getKey()) == null){
                modelParameters.put(entry.getKey(), entry.getValue());
            }
        }
        modelParametersLoading = false;
        callbackInterface.onDownloadFinished();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Boolean getModelParametersLoading() {
        return modelParametersLoading;
    }
}
