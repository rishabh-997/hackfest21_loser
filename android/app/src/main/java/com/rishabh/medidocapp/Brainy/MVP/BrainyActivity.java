package com.rishabh.medidocapp.Brainy.MVP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ss.com.bannerslider.Slider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Aptos.MVP.AptosActivity;
import com.rishabh.medidocapp.Brainy.Model.BrainyResponse;
import com.rishabh.medidocapp.R;
import com.rishabh.medidocapp.SplashScreen.SplashActivity;
import com.squareup.picasso.Picasso;

public class BrainyActivity extends AppCompatActivity implements BrainyContract.view
{
    BrainyContract.presenter presenter;

    TextView sendurl;
    TextView result,select_image, generatereport,report;
    ProgressBar progressBar;
    View overlay;
    Slider slider;

    String final_report = "";

    FirebaseStorage firebasestorage= FirebaseStorage.getInstance();
    StorageReference storageReference=firebasestorage.getReference().child("Brainy/"+System.currentTimeMillis()+".png");

    String doc_url="";
    Uri doc_data=null;
    private int check=0;
    String link="null";

    int capture = 1000;

    ImageView stage1, stage2, stage3;

    MainSliderAdapter MainSliderAdapter = new MainSliderAdapter();
    PicassoImageLoadingService PicassoImageLoadingService =new PicassoImageLoadingService(BrainyActivity.this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brainy);

        presenter = new BrainyPresenter(this);

        sendurl = findViewById(R.id.sendurl);
        result = findViewById(R.id.result);
        progressBar = findViewById(R.id.progressbar);
        select_image = findViewById(R.id.select_image);
        overlay = findViewById(R.id.overlay);
        slider = findViewById(R.id.banner_slider1);
        stage1 = findViewById(R.id.stage1);
        stage2 = findViewById(R.id.stage2);
        stage3 = findViewById(R.id.stage3);
        generatereport = findViewById(R.id.generate_report);
        report = findViewById(R.id.reports);

        Slider.init(PicassoImageLoadingService);
        slider.setAdapter(MainSliderAdapter);

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, capture);
            }
        });

        sendurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                overlay.setVisibility(View.VISIBLE);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("url", link);

                presenter.getResponse(jsonObject);
            }
        });

        stage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage1.setImageResource(R.drawable.completed_circle);
                select_image.setVisibility(View.GONE);
                sendurl.setVisibility(View.VISIBLE);
            }
        });

        generatereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stage2.setImageResource(R.drawable.completed_circle);
                generatereport.setVisibility(View.GONE);
                sendurl.setVisibility(View.GONE);
                result.setVisibility(View.GONE);
                stage2.setClickable(false);

                progressBar.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        report.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        report.setText(final_report);
                        stage3.setImageResource(R.drawable.completed_circle);
                    }
                },2000);
            }
        });

    }

    @Override
    public void toast(String error_aa_gaya) {
        Toast.makeText(this, error_aa_gaya, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
    }

    @Override
    public void showResult(BrainyResponse body) {
        progressBar.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
        result.setVisibility(View.VISIBLE);
        generatereport.setVisibility(View.VISIBLE);
        result.setText("Your extent of tumour has reached "+body.getNum()+" %");

        final_report = "You have "+body.getNum()+" % chances of having a brain tumour so consult your nearest expert.";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode==capture)&&(resultCode==RESULT_OK)&&(data!=null)&&(data.getData()!=null))
        {
            progressBar.setVisibility(View.VISIBLE);
            overlay.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Uploading ...", Toast.LENGTH_SHORT).show();

            doc_data = data.getData();

            storageReference.putFile(doc_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri data=uri;
                            doc_url = data.toString();
                            link=doc_url;
                            Toast.makeText(BrainyActivity.this, "File uploaded on server...\n you can Proceed to stage 2", Toast.LENGTH_LONG).show();
                            overlay.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            sendurl.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        }
        else
        {
            Toast.makeText(this, "did not selected the image", Toast.LENGTH_SHORT).show();
        }
    }
}
