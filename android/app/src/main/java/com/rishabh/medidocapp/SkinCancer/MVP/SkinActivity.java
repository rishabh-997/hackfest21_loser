package com.rishabh.medidocapp.SkinCancer.MVP;

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
import com.rishabh.medidocapp.Brainy.MVP.BrainyActivity;
import com.rishabh.medidocapp.Brainy.Model.BrainyResponse;
import com.rishabh.medidocapp.R;
import com.rishabh.medidocapp.SkinCancer.Model.SkinResponse;

public class SkinActivity extends AppCompatActivity implements SkinContract.view
{
    SkinContract.presenter presenter;

    TextView sendurl;
    TextView result,select_image, generatereport,report;
    ProgressBar progressBar;
    View overlay;
    Slider slider;

    FirebaseStorage firebasestorage= FirebaseStorage.getInstance();
    StorageReference storageReference=firebasestorage.getReference().child("Skinny/"+System.currentTimeMillis()+".png");

    String doc_url="";
    Uri doc_data=null;
    private int check=0;
    String link="null";

    int capture = 1000;

    ImageView stage1, stage2, stage3;
    String final_report ="";

    MainSliderAdapter2 mainSliderAdapter2 = new MainSliderAdapter2();
    PicassoImageLoadingService2 picassoImageLoadingService2 = new PicassoImageLoadingService2(SkinActivity.this);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        presenter = new SkinPresenter(this);

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

        Slider.init(picassoImageLoadingService2);
        slider.setAdapter(mainSliderAdapter2);

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
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
    }

    @Override
    public void showResult(SkinResponse body) {
        progressBar.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
        result.setVisibility(View.VISIBLE);
        generatereport.setVisibility(View.VISIBLE);
        result.setText("Your severity is "+body.getNum()+" on the scale of 0-6");

        int severity = body.getNum();

        switch(severity){
            case 0:
                final_report = "You are having ACITINIC KERATOSES ,ie, \nRough, scaly patch on your skin that develops from years of exposure to the sun";
                break;
            case 1:
                final_report = "You are having BASAL CELL CARCINOMA ,ie, \nbasal cell carcinomas are the least dangerous of skin cancers. They rarely metastasize (spread) or become life-threatening.";
                break;
            case 2:
                final_report = "You are having BENGIN KERATOSIS ,ie, \nnoncancerous (benign) skin growths that some people develop as they age. They often appear on the back or chest, but can occur on any part of the body.";
                break;
            case 3:
                final_report = "You are having DERMATOFIBROMA ,ie, \nA dermatofibroma is a harmless, non-cancerous bump. Dermatofibromas are harmless growths within the skin that usually have a small diameter. They can vary in color, and the color may change over the years.";
                break;
            case 4:
                final_report = "You are having MELANOMA ,ie, \n it is almost always curable, but if it is not, the cancer can advance and spread to other parts of the body, where it becomes hard to treat and can be fatal.";
                break;
            case 5:
                final_report = "You are having MELANOCYTIC NEVI ,ie, \nmelanocytic nevi appear to have an increased lifetime risk of melanoma, with the risk increasing in rough proportion to the size and/or number of lesions.";
                break;
            case 6:
                final_report = "You are having VASCULAR LESSIONS ,ie, \nCutaneous vascular lesions comprise of all skin disease that originate from or affect blood or lymphatic vessels, including malignant or benign tumors, malformations and inflammatory disease";
                break;

        }

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
                            Toast.makeText(SkinActivity.this, "File uploaded on server...\n you can Proceed to stage 2", Toast.LENGTH_SHORT).show();
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
