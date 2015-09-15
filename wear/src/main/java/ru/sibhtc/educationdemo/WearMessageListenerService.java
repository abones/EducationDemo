package ru.sibhtc.educationdemo;

import android.content.Intent;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Антон on 15.09.2015.
 **/
public class WearMessageListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Intent intent = new Intent( this, MainActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.putExtra("event", messageEvent.getData());
        startActivity( intent );
    }

}
