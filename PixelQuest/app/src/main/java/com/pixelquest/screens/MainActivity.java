package com.pixelquest.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.pixelquest.GameView;
import com.pixelquest.R;

public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        gameView = new GameView(this);
        setContentView(gameView);
        gameView.requestFocus();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.gc();

        synchronized(gameView.getGameLoop())
        {
            gameView.setContext(null);
            gameView.getGameLoop().setRunning(false);
            gameView = null;
        }
    }
}
