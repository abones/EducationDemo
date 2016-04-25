package ru.sibhtc.educationdemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.sibhtc.educationdemo.constants.IntentTypes;
import ru.sibhtc.educationdemo.constants.MessagePaths;
import ru.sibhtc.educationdemo.fragments.ExamWearFragment;
import ru.sibhtc.educationdemo.fragments.ExamWearResultFragment;
import ru.sibhtc.educationdemo.fragments.InfoFragment;
import ru.sibhtc.educationdemo.fragments.LearningWearFragment;
import ru.sibhtc.educationdemo.fragments.WaitingFragment;
import ru.sibhtc.educationdemo.helpers.BytesHelper;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.LabelsMock;
import ru.sibhtc.educationdemo.models.MessageModel;

public class MainActivity extends FragmentActivity implements
        View.OnTouchListener, View.OnLongClickListener {
    String LOG_TAG = MainActivity.class.getSimpleName();
    // list of NFC technologies detected:
    private final String[][] techList = new String[][]{
            new String[]{
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };

    private DismissOverlayView mDismissOverlayView;
    private GestureDetector mDetector;



    private GoogleApiClient apiClient;
    private FrameLayout frameLayout;
    private String nodeId;
    private FragmentManager fragmentManager;
    private String intentType;

    private Boolean isIntentWasDestroid = false;

    public Boolean getIsIntentWasDestroid() {
        return isIntentWasDestroid;
    }

    public void setIsIntentWasDestroid(Boolean isIntentWasDestroid) {
        this.isIntentWasDestroid = isIntentWasDestroid;
    }

    public void setIntentType(String intentType) {
        this.intentType = intentType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        onNewIntent(getIntent());
        frameLayout = (FrameLayout) findViewById(R.id.watchDataFrame);

        final Fragment fragment = new WaitingFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentById(R.id.watchDataFrame) != null) {
            fragmentTransaction.replace(R.id.watchDataFrame, fragment, "INFO");
        } else {
            fragmentTransaction.add(R.id.watchDataFrame, fragment, "INFO");
        }
        fragmentTransaction.commit();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initGoogleApiClient();
        if (GlobalHelper.mainActivity != null)
            this.setIsIntentWasDestroid(GlobalHelper.mainActivity.getIsIntentWasDestroid());

        GlobalHelper.mainActivity = this;
        mDismissOverlayView = new DismissOverlayView(this);
        // Attach Long Click detector
        frameLayout.setOnLongClickListener(this);

        frameLayout.addView(mDismissOverlayView, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        // Configure a gesture detector
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent ev) {
                mDismissOverlayView.show();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        apiClient.disconnect();
    }

    private void initGoogleApiClient() {
        apiClient = getGoogleApiClient(this);
        apiClient.connect();
        retrieveDeviceNode();
    }

    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();

    }

    private void retrieveDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(apiClient).await();

                List<Node> nodes = result.getNodes();

                if (nodes.size() > 0)
                    nodeId = nodes.get(0).getId();

                if (nodeId != null && nodeId.equals("cloud") && nodes.size() > 1) {
                    nodeId = nodes.get(1).getId();
                }
                GlobalHelper.nodeId = nodeId;
                GlobalHelper.apiClient = apiClient;
                Log.v(LOG_TAG, "Node ID of phone: " + nodeId);
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Все для nfc
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);

        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null && nfcAdapter.isEnabled())
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);

        //если на часах приложение было закрыто, то открываем в нужном режиме
        if (getIntent().getAction() == null && getIsIntentWasDestroid()) {
            setIsIntentWasDestroid(false);
            wakeUpIntent();
        }

    }

    private void wakeUpIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            intentType = intent.getStringExtra("type");
            if (intentType == null)
                intentType = IntentTypes.Info;

            switch (intentType) {
                case IntentTypes.Info: {
                    Fragment fragment;
                    Bundle bundle = intent.getExtras();
                    byte[] bytes = null;
                    if (bundle != null)
                        bytes = bundle.getByteArray("infoArray");

                    Bundle fragBundle = new Bundle();
                    if (bundle != null) {
                        fragBundle.putByteArray("info", bytes);
                        fragment = new InfoFragment();
                    } else {
                        //если первый запуск то окно ожидания сигнала метки
                        fragment = new WaitingFragment();
                    }

                    fragment.setArguments(fragBundle);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    if (fragmentManager.findFragmentById(R.id.watchDataFrame) != null) {
                        fragmentTransaction.replace(R.id.watchDataFrame, fragment, "INFO");
                    } else {
                        fragmentTransaction.add(R.id.watchDataFrame, fragment, "INFO");
                    }
                    fragmentTransaction.commit();
                    break;
                }

                case IntentTypes.Learning: {
                    Fragment fragment = new LearningWearFragment();
                    Bundle bundle = intent.getExtras();
                    byte[] bytes = bundle.getByteArray("infoArray");
                    Bundle fragBundle = new Bundle();
                    fragBundle.putByteArray("info", bytes);
                    fragment.setArguments(fragBundle);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (fragmentManager.findFragmentById(R.id.watchDataFrame) != null) {
                        fragmentTransaction.replace(R.id.watchDataFrame, fragment, "LEARNING");
                    } else {
                        fragmentTransaction.add(R.id.watchDataFrame, fragment, "LEARNING");
                    }
                    fragmentTransaction.commit();
                    break;
                }


                case IntentTypes.Exam: {
                    Fragment fragment = new ExamWearFragment();
                    Bundle bundle = intent.getExtras();
                    byte[] bytes = bundle.getByteArray("infoArray");
                    Bundle fragBundle = new Bundle();
                    fragBundle.putByteArray("info", bytes);
                    fragment.setArguments(fragBundle);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (fragmentManager.findFragmentById(R.id.watchDataFrame) != null) {
                        fragmentTransaction.replace(R.id.watchDataFrame, fragment, "EXAM");
                    } else {
                        fragmentTransaction.add(R.id.watchDataFrame, fragment, "EXAM");
                    }
                    fragmentTransaction.commit();
                    break;
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null && nfcAdapter.isEnabled())
            nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            Parcelable mytag = intent.getParcelableExtra(nfcAdapter.EXTRA_TAG);  // get the detected tag
            String tagId = "";
            for (int i = 0; i < 4; i++) {
                tagId = tagId + ":" + GlobalHelper.decToHex(((Tag) mytag).getId()[i]).toString();
            }
            StringBuilder stringBuilder = new StringBuilder(tagId);
            stringBuilder.deleteCharAt(0);
            nfcSendIdToPhone(stringBuilder.toString());
        }

    }

    private void nfcSendIdToPhone(String tagId) {
        MessageModel messageModel = new MessageModel();
        messageModel.labelCode = tagId;
        try {
            //провибрировать на приложенную метку
            Vibrator vibratorNFCCheck = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibratorNFCCheck.vibrate(100);

            GlobalHelper.sendMessage(MessagePaths.LABEL_MESSAGE_PATH, BytesHelper.toByteArray(messageModel));
        } catch (IOException e) {
            //
        }
    }


    //метод будет заменять инфу в открытом фрагменте или же сменит фрагмент
    //если сменился режим приложения
    public void changeInformation(byte[] data) {
        switch (intentType) {
            case IntentTypes.Waiting: {
                if (this.fragmentManager.getFragments().get(this.fragmentManager.getFragments().size() - 1) instanceof WaitingFragment) {

                } else {
                    //отображение фрагмента ожидания метки
                    Bundle fragBundle = new Bundle();
                    fragBundle.putByteArray("info", data);
                    Fragment fragment = new WaitingFragment();
                    fragment.setArguments(fragBundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.watchDataFrame, fragment, "Waiting");

                    fragmentTransaction.commit();
                }

                break;

            }

            case IntentTypes.Info: {
                if (this.fragmentManager.getFragments().get(0) instanceof WaitingFragment) {
                    //отображение фрагмента ожидания метки
                    Bundle fragBundle = new Bundle();
                    fragBundle.putByteArray("info", data);
                    Fragment fragment = new InfoFragment();
                    fragment.setArguments(fragBundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.watchDataFrame, fragment, "INFO");

                    fragmentTransaction.commit();
                } else if (this.fragmentManager.getFragments().get(0) instanceof InfoFragment) {
                    //вывод информации на фрайм
                    ((InfoFragment) this.fragmentManager.getFragments().get(0)).changeInformation(data);
                }

                break;

            }
            case IntentTypes.Learning: {
                if (this.fragmentManager.getFragments().get(0) instanceof LearningWearFragment) {
                    //вывод информации на фрайм
                    ((LearningWearFragment) this.fragmentManager.getFragments().get(0)).changeInformation(data);
                } else {
                    //отображение фрагмента ожидания метки
                    Bundle fragBundle = new Bundle();
                    fragBundle.putByteArray("info", data);
                    Fragment fragment = new LearningWearFragment();
                    fragment.setArguments(fragBundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.watchDataFrame, fragment, "LearningModel");
                    fragmentTransaction.commit();
                }

                break;
            }
            case IntentTypes.Exam: {
                if (this.fragmentManager.getFragments().get(this.fragmentManager.getFragments().size() - 1) instanceof ExamWearFragment) {
                    //вывод информации на фрайм
                    Bundle fragBundle = new Bundle();
                    fragBundle.putByteArray("info", data);
                    Fragment fragment = new ExamWearResultFragment();
                    fragment.setArguments(fragBundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.watchDataFrame, fragment, "ExamModelResult");
                    fragmentTransaction.commit();
                } else {
                    //отображение фрагмента ожидания метки
                    Bundle fragBundle = new Bundle();
                    fragBundle.putByteArray("info", data);
                    Fragment fragment = new ExamWearFragment();
                    fragment.setArguments(fragBundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.watchDataFrame, fragment, "ExamModel");
                    fragmentTransaction.commit();
                }
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        mDismissOverlayView.show();
        return true;
    }

    public boolean onTouch(View v, MotionEvent event) {
        //...
        return false;
    }

    // Capture long presses
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }
}
