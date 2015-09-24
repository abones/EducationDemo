package ru.sibhtc.educationdemo.services;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by AbdrashitovNI on 24.09.2015.
 */
public class SiteData extends AsyncTask<String, Void, String> implements ISiteData {

    @Override
    public void callbackFunction(String result){
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";

        try {
            URL url = new URL(urls[0]);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader in = new BufferedReader(inputStreamReader);
            String str;
            while ((str = in.readLine()) != null){
                response += str;
            }
            in.close();
        }
        catch (MalformedURLException e){

        }
        catch (IOException e){

        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        callbackFunction(result);
    }

}
