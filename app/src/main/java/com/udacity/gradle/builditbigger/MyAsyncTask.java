package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class MyAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private OnEventListener<String> mCallBack;
    private Context mContext;
    public Exception mException;
    private ProgressBarVisibilityHandler mProgressBarVisibilityHandler;

    private static final String API_URL = "http://10.0.2.2:8080/_ah/api/";
    private static MyApi myApiService = null;

    public MyAsyncTask(Context context, ProgressBarVisibilityHandler progressBarVisibilityHandler, OnEventListener callback ){
        mCallBack = callback;
        mContext = context;
        mProgressBarVisibilityHandler = progressBarVisibilityHandler;
    }

    @Override
    protected void onPreExecute() {
        mProgressBarVisibilityHandler.showProgressBar();

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

        mContext = pairs[0].first;

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            mException = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {

        mProgressBarVisibilityHandler.hideProgressBar();

        if (mCallBack != null) {
            if (mException == null) {
                mCallBack.onSuccess(result);
            } else {
                mCallBack.onFailure(mException);
            }
        }
    }
}
