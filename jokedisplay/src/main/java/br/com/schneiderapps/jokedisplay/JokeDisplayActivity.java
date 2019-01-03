package br.com.schneiderapps.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    private TextView mTvJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        mTvJoke = findViewById(R.id.tv_joke);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String joke = extras.getString(getString(R.string.key_joke));
            mTvJoke.setText(joke);
        }

    }
}
