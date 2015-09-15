package ru.sibhtc.educationdemo;


import android.content.Intent;
import android.os.Bundle;
import android.app.TabActivity;
import android.widget.TabHost;



public class MainActivity extends TabActivity{


    private static final String TAG = "junk";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // получаем TabHost
        TabHost tabHost = getTabHost();

        // инициализация была выполнена в getTabHost
        // метод setup вызывать не нужно

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("students");
        tabSpec.setIndicator("Студенты");
        tabSpec.setContent(new Intent(this, FirstFragmentActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("exam");
        tabSpec.setIndicator("Экзамен");
        tabSpec.setContent(new Intent(this, SecondFragmentActivity.class));
        tabHost.addTab(tabSpec);

        //setUpTabs(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //            save the selected tab's index so it's re-selected on orientation change
 //       outState.putInt("tabIndex", getSupportActionBar().getSelectedNavigationIndex());
    }

}
