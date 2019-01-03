package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import br.com.schneiderapps.jokedisplay.JokeDisplayActivity;

public class JokesAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static final String API_URL = "http://10.0.2.2:8080/_ah/api/";

    private ProgressBarVisibilityHandler progressBarVisibilityHandler;
    private static MyApi myApiService = null;
    private Context context;

    public JokesAsyncTask(ProgressBarVisibilityHandler ProgressBarVisibilityHandler){
        this.progressBarVisibilityHandler = ProgressBarVisibilityHandler;
    }

    @Override
    protected void onPreExecute() {
        progressBarVisibilityHandler.showProgressBar();

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Pair<Context, String>... pairs) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(API_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = pairs[0].first;

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        progressBarVisibilityHandler.hideProgressBar();

        if (result != null) {
            launchJokeActivity(result);
        }
    }


    public void launchJokeActivity(String result) {

        String intentKey = context.getString(R.string.key_joke);
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(intentKey, result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
