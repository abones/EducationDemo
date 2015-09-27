package ru.sibhtc.educationdemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.AppMode;


/**
 * Created by Антон on 16.09.2015.
 **/
public class LearningActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    private static final String TAG = "junk";
    private Date learningStartDate;
    private SettingsFragment settingsFragment;
    private LearningFragment learningFragment;
    private FragmentTransaction fTrans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.application_name);
        bar.setSubtitle(R.string.application_name_demo);
        bar.setDisplayHomeAsUpEnabled(true);

        settingsFragment = new SettingsFragment();
        settingsFragment.setAppMode(AppMode.LEARNING);
        learningFragment = new LearningFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fragmentManager.beginTransaction();
        fTrans.add(R.id.learningTabFrame, settingsFragment, "SETTINGS");
        fTrans.commit();
    }

    //NEW
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.itemStudents:{
                Intent intent =  new Intent(this, StudentsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.itemExam:{
                Intent intent =  new Intent(this, ExamActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    public void startLearning(int programId, int studentId){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Bundle bundle = new Bundle();
        learningStartDate = new Date();
        bundle.putInt("programId", programId);
        bundle.putInt("studentId", studentId);
        bundle.putString("date", dateFormat.format(learningStartDate));
        learningFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fragmentManager.beginTransaction();
        GlobalHelper.CurrentAppMode = AppMode.LEARNING;
        GlobalHelper.setLearningFragment(learningFragment);
        fTrans.replace(R.id.learningTabFrame, learningFragment, "LEARNING");
        fTrans.commit();

    }
}
