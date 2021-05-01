package com.rishabh.medidocapp.HomeScreen;

import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rishabh.medidocapp.Aptos.MVP.AptosActivity;
import com.rishabh.medidocapp.Brainy.MVP.BrainyActivity;
import com.rishabh.medidocapp.ChestCancer.MVP.ChestActivity;
import com.rishabh.medidocapp.R;
import com.rishabh.medidocapp.SkinCancer.MVP.SkinActivity;

public class HomePageActivity extends AppCompatActivity {

    TextView brainy, aptos, skin;
    ConstraintLayout brain_layout,eye_layout,skin_layout;
    TextView eyeapi, brainapi, skinapi;

    TextView eyeauth, brainauth, skinauth;

    final static String aptos_potha = "Dataset -  The HAM 10000 dataset \nBase Model Architecture  - EfficientNet-b0\nOptimizer - Ranger\n\nPERFORMANCE\n\nAccuracy - 74.18% ( 5-ary classification )\nKappa ( qwk ) - 90.42";
    final static String skinny_potha = "Dataset - 2019 diabetic retinopathy dataset\nBase Model Architecture  - EfficientNet-b0\nOptimizer - Ranger\n\nPERFORMANCE\n\nAccuracy -  87.15% ( 5-ary classification )\nRecall - 0.71\nF1 Score - 0.75";
    final static String brainy_potha = "Dataset - Brain MRI Images for Brain Tumour Detection\nBase Model Architecture  - EfficientNet-b0\nOptimizer - Ranger\n\nPERFORMANCE\n\nAccuracy - 94%\nRecall - 0.93\nF1 Score - 0.94";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        brainy = findViewById(R.id.Brainy);
        aptos = findViewById(R.id.Aptos);
        skin = findViewById(R.id.skin);
        brain_layout = findViewById(R.id.card_brain);
        eye_layout = findViewById(R.id.card_eye);
        skin_layout = findViewById(R.id.card_skin);
        eyeapi = findViewById(R.id.proceed_eyeapi);
        skinapi = findViewById(R.id.proceed_skinapi);
        brainapi = findViewById(R.id.proceed_brainapi);
        eyeauth = findViewById(R.id.eye_authenticity);
        brainauth = findViewById(R.id.brain_authenticity);
        skinauth = findViewById(R.id.skin_authenticity);

        brainapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, BrainyActivity.class));
            }
        });

        eyeapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, AptosActivity.class));
            }
        });

        skinapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, SkinActivity.class));
            }
        });

        brainy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brainy.setBackgroundColor(Color.parseColor("#F68D4F"));
                aptos.setBackgroundColor(Color.parseColor("#2AC6DC"));
                skin.setBackgroundColor(Color.parseColor("#2AC6DC"));
                brain_layout.setVisibility(View.VISIBLE);
                skin_layout.setVisibility(View.GONE);
                eye_layout.setVisibility(View.GONE);
            }
        });
        aptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aptos.setBackgroundColor(Color.parseColor("#F68D4F"));
                brainy.setBackgroundColor(Color.parseColor("#2AC6DC"));
                skin.setBackgroundColor(Color.parseColor("#2AC6DC"));
                eye_layout.setVisibility(View.VISIBLE);
                brain_layout.setVisibility(View.GONE);
                skin_layout.setVisibility(View.GONE);
            }
        });
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                skin.setBackgroundColor(Color.parseColor("#F68D4F"));
                aptos.setBackgroundColor(Color.parseColor("#2AC6DC"));
                brainy.setBackgroundColor(Color.parseColor("#2AC6DC"));
                skin_layout.setVisibility(View.VISIBLE);
                brain_layout.setVisibility(View.GONE);
                eye_layout.setVisibility(View.GONE);
            }
        });

        eyeauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue("Eye Blindness",aptos_potha);
            }
        });
        brainauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue("Brain tumour",brainy_potha);
            }
        });
        skinauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue("Skin Cancer",skinny_potha);
            }
        });

    }

    void showDialogue(String title, String message){

        final AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}
