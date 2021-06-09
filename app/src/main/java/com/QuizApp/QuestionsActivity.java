package com.QuizApp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.Collections;

public class QuestionsActivity extends AppCompatActivity {

    @Override
    public void onBackPressed(){
    }

    private ImageView mImView;
    private AdView mAdView;
    TextView tv;
    Button submitbutton, quitbutton, ansbutton, helpbutton, button50, buttonhide, buttonclick;
    RadioGroup radio_g;
    RadioButton rb1, rb2, rb3, rb4;

    boolean helpIsUsing = false;
    int pressHelp = 3;
    boolean pressQuit = false;
    boolean press50 = false;

    String ans1, ans2, ans3, ans4;
    String imgURL;
    String rightAnswer;
    String question;

    //FirebaseDatabase Quiz = FirebaseDatabase.getInstance()
    FirebaseFirestore questions = FirebaseFirestore.getInstance();

    ArrayList<String> numbers = new ArrayList<String>();
    ArrayList<String> numbersAns = new ArrayList<String>();

    int flag = 0;
    public static int marks = 0,correct = 0,wrong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        CollectionReference col = questions.collection("questions");


        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");
        numbers.add("10");

        numbersAns.add("1"); numbersAns.add("2"); numbersAns.add("3"); numbersAns.add("4");

        Collections.shuffle(numbers);
        Collections.shuffle(numbersAns);

        DocumentReference doc = col.document(numbers.get(flag));

        getAnswers(doc, numbersAns);

        mAdView = findViewById(R.id.adView);
        mImView = findViewById(R.id.imView);
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

        final TextView score = (TextView)findViewById(R.id.textView4);

        helpbutton = (Button)findViewById(R.id.button_help);
        button50 = (Button)findViewById(R.id.button50);
        submitbutton=(Button)findViewById(R.id.button_next);
        submitbutton.setVisibility(View.GONE);
        quitbutton=(Button)findViewById(R.id.buttonquit);
        ansbutton = (Button)findViewById(R.id.button_reply);
        buttonclick = (Button)findViewById(R.id.btn_click);
        buttonhide = (Button)findViewById(R.id.btn_hide);
        ansbutton.setVisibility(View.VISIBLE);
        tv=(TextView) findViewById(R.id.tvque);

        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);
        rb1.setTextColor(Color.WHITE);
        rb2.setTextColor(Color.WHITE);
        rb3.setTextColor(Color.WHITE);
        rb4.setTextColor(Color.WHITE);


        ansbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1.setTextColor(Color.WHITE);
                rb2.setTextColor(Color.WHITE);
                rb3.setTextColor(Color.WHITE);
                rb4.setTextColor(Color.WHITE);

                buttonhide.setVisibility(View.VISIBLE);
                buttonclick.setVisibility(View.VISIBLE);

                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Выберите вариант ответа", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();

                if(ansText.equals(rightAnswer)) {
                    correct++;
                    rb1.setEnabled(false);
                    rb2.setEnabled(false);
                    rb3.setEnabled(false);
                    rb4.setEnabled(false);
                    if (rb1.isChecked()) rb1.setTextColor(Color.GREEN); else rb1.setTextColor(Color.WHITE);
                    if (rb2.isChecked()) rb2.setTextColor(Color.GREEN); else rb2.setTextColor(Color.WHITE);
                    if (rb3.isChecked()) rb3.setTextColor(Color.GREEN); else rb3.setTextColor(Color.WHITE);
                    if (rb4.isChecked()) rb4.setTextColor(Color.GREEN); else rb4.setTextColor(Color.WHITE);

                }
                else {
                    wrong++;
                    String rad1 = rb1.getText().toString();
                    String rad2 = rb2.getText().toString();
                    String rad3 = rb3.getText().toString();
                    String rad4 = rb4.getText().toString();
                    rb1.setEnabled(false);
                    rb2.setEnabled(false);
                    rb3.setEnabled(false);
                    rb4.setEnabled(false);
                    if (rb1.isChecked()) rb1.setTextColor(Color.RED);
                    if (rad1.equals(rightAnswer))rb1.setTextColor(Color.GREEN);
                    else rb1.setTextColor(Color.WHITE);
                    if (rb2.isChecked()) rb2.setTextColor(Color.RED);
                    if (rad2.equals(rightAnswer))rb2.setTextColor(Color.GREEN);
                    else rb2.setTextColor(Color.WHITE);
                    if (rb3.isChecked()) rb3.setTextColor(Color.RED);
                    if (rad3.equals(rightAnswer)){rb3.setTextColor(Color.GREEN);}
                    else rb3.setTextColor(Color.WHITE);
                    if (rb4.isChecked()) rb4.setTextColor(Color.RED);
                    if (rad4.equals(rightAnswer)){rb4.setTextColor(Color.GREEN);}
                    else rb4.setTextColor(Color.WHITE);







                }

                submitbutton.setVisibility(View.VISIBLE);
                ansbutton.setVisibility(View.GONE);


                score.setText(Integer.toString(correct));
                flag++;
            }
        });

        buttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonhide.setVisibility(View.INVISIBLE);
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rb1.setEnabled(true);
                rb2.setEnabled(true);
                rb3.setEnabled(true);
                rb4.setEnabled(true);
                rb1.setTextColor(Color.WHITE);
                rb2.setTextColor(Color.WHITE);
                rb3.setTextColor(Color.WHITE);
                rb4.setTextColor(Color.WHITE);

                press50 = false;

                if((pressHelp < 3) && (helpIsUsing)) {
                    ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();
                    radioButtons.add(rb1);
                    radioButtons.add(rb2);
                    radioButtons.add(rb3);
                    radioButtons.add(rb4);

                    for (int i = 0, a = 0; a < 2; i++) {
                        if (radioButtons.get(i).getVisibility() == View.INVISIBLE) {
                            radioButtons.get(i).setVisibility(View.VISIBLE);
                            a++;
                        }
                    }
                    helpIsUsing = false;
                }

                if (rb1.isChecked()) rb1.setTextColor(Color.WHITE);
                if (rb2.isChecked()) rb2.setTextColor(Color.WHITE);
                if (rb3.isChecked()) rb3.setTextColor(Color.WHITE);
                if (rb4.isChecked()) rb4.setTextColor(Color.WHITE);

                ansbutton.setVisibility(View.VISIBLE);
                submitbutton.setVisibility(View.GONE);

                if(flag<10) {
                    DocumentReference doc = col.document(numbers.get(flag));
                    getAnswers(doc, numbersAns);
                } else {
                    marks=correct;
                    Intent in = new Intent(getApplicationContext(),RecultActivity.class);
                    startActivity(in);
                }

                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pressQuit) {
                    pressQuit = false;
                    Intent intent = new Intent(getApplicationContext(), RecultActivity.class);
                    startActivity(intent);
                } else {
                    pressQuit = true;
                    Toast.makeText(getApplicationContext(), "Нажмите ещё раз чтобы выйти", Toast.LENGTH_SHORT).show();
                }
            }
        });

        helpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1.setTextColor(Color.WHITE);
                rb2.setTextColor(Color.WHITE);
                rb3.setTextColor(Color.WHITE);
                rb4.setTextColor(Color.WHITE);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String text1 = ""; String text2 = ""; String text3 = ""; String text4 = "";
                if(rb1.getText().toString() != "") {
                    text1 = "\n1) " + rb1.getText().toString();
                }
                if(rb2.getText().toString() != "") {
                    text2 = "\n2) " + rb2.getText().toString();
                }
                if(rb3.getText().toString() != "") {
                    text3 = "\n3) " + rb3.getText().toString();
                }
                if(rb4.getText().toString() != "") {
                    text4 = "\n4) " + rb4.getText().toString();
                }


                sendIntent.putExtra(Intent.EXTRA_TEXT, "ПАМАГИТЕ!!!\n" + question + "\nВарианты ответа" +
                         text1 + text2 + text3 + text4);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Поделиться"));
            }
        });

        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!press50) {
                    ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();
                    radioButtons.add(rb1);
                    radioButtons.add(rb2);
                    radioButtons.add(rb3);
                    radioButtons.add(rb4);
                    Collections.shuffle(radioButtons);
                    press50 = true;

                    if (pressHelp > 0) {
                        for (int i = 0, a = 0; a < 2; i++) {
                            if (!radioButtons.get(i).getText().equals(rightAnswer)) {
                                radioButtons.get(i).setText("");
                                radioButtons.get(i).setVisibility(View.INVISIBLE);
                                a++;
                            }
                        }
                        pressHelp--;
                        helpIsUsing = true;
                        Toast.makeText(getApplicationContext(), "Осталось подсказок " + pressHelp, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "У вас кончились подсказки", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Вы уже использовали подсказку", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getAnswers (DocumentReference doc, ArrayList<String> numbersAns) {

        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        rb1.setText(document.getString("ans" + numbersAns.get(0)));
                        rb2.setText(document.getString("ans" + numbersAns.get(1)));
                        rb3.setText(document.getString("ans" + numbersAns.get(2)));
                        rb4.setText(document.getString("ans" + numbersAns.get(3)));
                        ans1 = document.getString("ans" + numbersAns.get(0));
                        ans2 = document.getString("ans" + numbersAns.get(1));
                        ans3 = document.getString("ans" + numbersAns.get(2));
                        ans4 = document.getString("ans" + numbersAns.get(3));
                        question = document.getString("question");
                        tv.setText(document.getString("question"));
                        rightAnswer = document.getString("rightAnswer");
                        imgURL = (document.getString("imgURL"));
                        Glide.with(getApplicationContext())
                                .load(imgURL)
                                .into(mImView);
                    } else {
                        Log.d("READING DATA ", "No such document");
                    }
                } else {
                    Log.d("READING DATA ", "get failed with ", task.getException());
                }
            }
        });
    }
}