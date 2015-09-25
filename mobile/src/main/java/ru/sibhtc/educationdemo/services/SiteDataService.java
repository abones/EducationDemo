package ru.sibhtc.educationdemo.services;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import ru.sibhtc.educationdemo.constants.ApplicationConfigs;

/**
 * Created by AbdrashitovNI on 24.09.2015.
 */
public class SiteDataService extends AsyncTask<String, Void, String> implements ISiteData {

    private Map<String, String> serverParameters;

    @Override
    public void callbackFunction() {
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
            while ((str = in.readLine()) != null) {
                response += str;
            }
            in.close();
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        parseResult(result);
        callbackFunction();
    }

    /*
    * Парсит ответ от сервака
     */
    protected void parseResult(String parameters) {
        StringTokenizer parametersTokenizer = new StringTokenizer(parameters, ApplicationConfigs.SERVER_DECIMAL_SEPARATOR_PARAMETER);
        String parameter = "";
        String value = "";
        String key = "";
        if (serverParameters == null)
            serverParameters = new HashMap<String, String>();

        try {
            while (parametersTokenizer.hasMoreTokens()) {
                parameter = parametersTokenizer.nextToken();
                StringTokenizer parameterTokenizer = new StringTokenizer(parameter, ApplicationConfigs.SERVER_DECIMAL_SEPARATOR_VALUE);

                while (parameterTokenizer.hasMoreTokens()) {
                    key = parameterTokenizer.nextToken();
                    value = parameterTokenizer.nextToken();

                    serverParameters.put(key, value);
                }
            }
        }
        catch (Exception e){

        }

    }

    public Map<String, String> getServerParameters() {
        return serverParameters;
    }

    public void setServerParameters(Map<String, String> serverParameters) {
        this.serverParameters = serverParameters;
    }
}
