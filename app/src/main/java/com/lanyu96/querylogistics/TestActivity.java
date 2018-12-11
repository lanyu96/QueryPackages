package com.lanyu96.querylogistics;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dd.morphingbutton.MorphingButton;
import com.dd.processbutton.FlatButton;
import com.dd.processbutton.iml.ActionProcessButton;

import github.ishaan.buttonprogressbar.ButtonProgressBar;

public class TestActivity extends AppCompatActivity {

    private ActionProcessButton btnSignIn;
    private MorphingButton morBtn;
    private ButtonProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        bar = findViewById(R.id.test_btn_progressBar);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.startLoader();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bar.stopLoader();
                    }
                }, 1000);


            }
        });




//        morBtn = findViewById(R.id.act_test_morphingbtn);
//        MorphingButton.Params circle = MorphingButton.Params.create()
//                .duration(500)
//                .cornerRadius(56) // 56 dp
//                .width(56) // 56 dp
//                .height(56) // 56 dp
//                .color(Color.GREEN) // normal state color
//                .colorPressed(Color.GREEN); // pressed state color
////                .icon(R.drawable.ic_done); // icon
//        morBtn.morph(circle);


//        btnSignIn = findViewById(R.id.btnSignIn);
//
//        btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
//
//        btnSignIn.setProgress(100);
//
//        btnSignIn.setProgress(1);
//
//        btnSignIn.
//        // no progress
//        button.setProgress(0);
//// progressDrawable cover 50% of button width, progressText is shown
//        button.setProgress(50);
//// progressDrawable cover 75% of button width, progressText is shown
//        button.setProgress(75);
//// completeColor & completeText is shown
//        button.setProgress(100);
//
//// you can display endless google like progress indicator
//        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
//// set progress > 0 to start progress indicator animation
//        button.setProgress(1);



    }
}
