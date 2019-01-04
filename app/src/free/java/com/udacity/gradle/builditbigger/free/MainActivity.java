package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.JokesAsyncTask;
import com.udacity.gradle.builditbigger.MyAsyncTask;
import com.udacity.gradle.builditbigger.OnEventListener;
import com.udacity.gradle.builditbigger.ProgressBarVisibilityHandler;
import com.udacity.gradle.builditbigger.R;

import br.com.schneiderapps.jokedisplay.JokeDisplayActivity;

public class MainActivity extends AppCompatActivity implements ProgressBarVisibilityHandler {

    static ProgressBar loadingProgressBar;
    private InterstitialAd mInterstitialAd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mContext = this;
        mInterstitialAd = new InterstitialAd(this);

        //AdUnitId for tests only, replace it with your own Id
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        requestNewInterstitialAd();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitialAd();
                getJokes();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void requestNewInterstitialAd() {
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(request);
    }

    protected void tellJoke(View view) {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

            getJokes();
        }
    }

    public void getJokes() {
        //new JokesAsyncTask(this).execute(new Pair<Context, String>(getApplicationContext(), "free"));

        new MyAsyncTask(mContext, this, new OnEventListener<String>() {
            @Override
            public void onSuccess(String result) {
                launchJokeActivity(result);
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(mContext, getString(R.string.msg_error_retrieving_joke) + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }).execute(new Pair<Context, String>(getApplicationContext(), "free"));
    }

    @Override
    public void showProgressBar() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    public void launchJokeActivity(String result) {

        String intentKey = getString(R.string.key_joke);
        Intent intent = new Intent(mContext, JokeDisplayActivity.class);
        intent.putExtra(intentKey, result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
