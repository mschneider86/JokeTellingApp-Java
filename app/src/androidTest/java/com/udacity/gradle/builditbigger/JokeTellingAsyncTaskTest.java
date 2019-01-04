package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class JokeTellingAsyncTaskTest implements ProgressBarVisibilityHandler{

    @Test
    public void testNonEmptyString() {
        //JokesAsyncTask endpointsAsyncTask = new JokesAsyncTask(this);

        //endpointsAsyncTask.execute(new Pair<>(InstrumentationRegistry.getTargetContext(), "test"));

        MyAsyncTask myEndpointsAsyncTask = new MyAsyncTask(InstrumentationRegistry.getTargetContext(), this, new OnEventListener<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(Exception e) {


            }
        });

        myEndpointsAsyncTask.execute(new Pair<>(InstrumentationRegistry.getTargetContext(), "test"));

        String result = "";
        try {
            result = myEndpointsAsyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        assertTrue(result.length() > 0);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
