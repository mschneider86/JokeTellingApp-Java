package com.udacity.gradle.builditbigger.paid;

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

import com.udacity.gradle.builditbigger.JokesAsyncTask;
import com.udacity.gradle.builditbigger.MyAsyncTask;
import com.udacity.gradle.builditbigger.OnEventListener;
import com.udacity.gradle.builditbigger.ProgressBarVisibilityHandler;
import com.udacity.gradle.builditbigger.R;

import br.com.schneiderapps.jokedisplay.JokeDisplayActivity;

public class MainActivity extends AppCompatActivity implements ProgressBarVisibilityHandler{

    private static ProgressBar loadingProgressBar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);
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

    @Override
    public void showProgressBar() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    protected void tellJoke(View view) {

        getJokes();
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
        }).execute(new Pair<Context, String>(getApplicationContext(), "paid"));
    }

    public void launchJokeActivity(String result) {

        String intentKey = getString(R.string.key_joke);
        Intent intent = new Intent(mContext, JokeDisplayActivity.class);
        intent.putExtra(intentKey, result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

