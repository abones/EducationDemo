package ru.sibhtc.educationdemo.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.sibhtc.educationdemo.R;
import ru.sibhtc.educationdemo.constants.MessageStrings;
import ru.sibhtc.educationdemo.fragments.ExamFragment;
import ru.sibhtc.educationdemo.fragments.SettingsFragment;
import ru.sibhtc.educationdemo.helpers.GlobalHelper;
import ru.sibhtc.educationdemo.mock.AppMode;

/**
 * Created by Антон on 16.09.2015.
 **/
public class ExamActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    private static final String TAG = "junk";

    private Date examStartDate;
    private SettingsFragment settingsFragment;
    private ExamFragment examFragment;


    private FragmentTransaction fTrans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.application_name);
        bar.setSubtitle(R.string.application_name_demo);
        bar.setDisplayHomeAsUpEnabled(true);

        settingsFragment = new SettingsFragment();
        settingsFragment.setAppMode(AppMode.EXAMINE);
        examFragment = new ExamFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fragmentManager.beginTransaction();
        fTrans.add(R.id.examTabFrame, settingsFragment, "SETTINGS");
        fTrans.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(MessageStrings.EXAM_PROCESSED)
                    .setMessage(MessageStrings.EXAM_ARE_YOU_SHURE_FINISHED)
                    .setCancelable(false)
                    .setPositiveButton(MessageStrings.MAIN_YES, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ExamActivity.this.finish();
                        }
                    })
                    .setNegativeButton(MessageStrings.MAIN_NO, null);
            AlertDialog alert = builder.create();
            alert.show();

            return true;
        } else{
            return super.onKeyDown(keyCode,event);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context that = getApplicationContext();
        switch (item.getItemId()) {
            case R.id.itemStudents: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(MessageStrings.EXAM_PROCESSED)
                        .setMessage(MessageStrings.EXAM_ARE_YOU_SHURE_FINISHED)
                        .setCancelable(false)
                        .setPositiveButton(MessageStrings.MAIN_YES, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(that, StudentsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(MessageStrings.MAIN_NO, null);
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }
            case R.id.itemLearning: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(MessageStrings.EXAM_PROCESSED)
                        .setMessage(MessageStrings.EXAM_ARE_YOU_SHURE_FINISHED)
                        .setCancelable(false)
                        .setPositiveButton(MessageStrings.MAIN_YES, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(that, LearningActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(MessageStrings.MAIN_NO, null);
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }
            case android.R.id.home:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(MessageStrings.EXAM_PROCESSED)
                        .setMessage(MessageStrings.EXAM_ARE_YOU_SHURE_FINISHED)
                        .setCancelable(false)
                        .setPositiveButton(MessageStrings.MAIN_YES, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ExamActivity.this.finish();
                            }
                        })
                        .setNegativeButton(MessageStrings.MAIN_NO, null);
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void startExam(int programId, int studentId) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Bundle bundle = new Bundle();
        examStartDate = new Date();
        bundle.putInt("programId", programId);
        bundle.putInt("studentId", studentId);
        bundle.putString("date", dateFormat.format(examStartDate));
        examFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fragmentManager.beginTransaction();
        GlobalHelper.CurrentAppMode = AppMode.EXAMINE;
        GlobalHelper.setExamFragment(examFragment);
        fTrans.replace(R.id.examTabFrame, examFragment, "LEARNING");
        fTrans.commit();

    }
}
