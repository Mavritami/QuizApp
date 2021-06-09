package com.QuizApp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;


public class TopActivity extends AppCompatActivity {

    Button menubutton;
    FirebaseFirestore questions = FirebaseFirestore.getInstance();
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }


            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        menubutton = (Button)findViewById(R.id.button);

        getTop();

        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Main.class);
                startActivity(intent);
            }

        });


    }

    public void addTableRow(String name, String score) {
        TableLayout stk = (TableLayout) findViewById(R.id.table_top);
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(name);
            t1v.setTextColor(Color.WHITE);
            t1v.setTextSize(30);
            t1v.setWidth(540);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(score);
            t2v.setTextColor(Color.WHITE);
            t2v.setTextSize(30);
            t2v.setWidth(540);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            stk.addView(tbrow);

    }


    public void getTop () {

        questions.collection("top")
                .orderBy("score", DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("READING ALL", document.getId() + " => " + document.getData());


                                addTableRow(document.get("name").toString(), document.get("score").toString());
                            }
                        } else {
                            Log.d("READING ALL", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
