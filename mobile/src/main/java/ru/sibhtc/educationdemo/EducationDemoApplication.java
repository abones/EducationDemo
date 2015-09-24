package ru.sibhtc.educationdemo;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.AppMode;

/**
 * Created by Антон on 24.09.2015.
 **/
public class EducationDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalHelper.CurrentAppMode = AppMode.INFORMATION_SENDER;
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        GlobalHelper.apiClient = new GoogleApiClient.Builder(this)
                .addApi( Wearable.API )
                .build();
        GlobalHelper.apiClient.connect();
    }

    @Override
    public void onTerminate() {
        GlobalHelper.apiClient.disconnect();
        super.onTerminate();
    }
}
