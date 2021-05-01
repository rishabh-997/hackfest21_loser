package com.rishabh.medidocapp.Aptos.MVP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ss.com.bannerslider.Slider;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Aptos.Model.AptosResponse;
import com.rishabh.medidocapp.R;
import com.rishabh.medidocapp.SkinCancer.MVP.MainSliderAdapter2;
import com.rishabh.medidocapp.SkinCancer.MVP.PicassoImageLoadingService2;
import com.rishabh.medidocapp.SkinCancer.MVP.SkinActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class AptosActivity extends AppCompatActivity implements AptosContract.view
{
    AptosContract.presenter presenter;
    String image_url = "http://10.42.0.1:5000/get-image/output_heat_map.png";

    TextView sendurl;
    TextView result,select_image, generatereport,report;
    ProgressBar progressBar;
    View overlay;
    Slider slider;

    FirebaseStorage firebasestorage= FirebaseStorage.getInstance();
    StorageReference storageReference=firebasestorage.getReference().child("Aptos/"+System.currentTimeMillis()+".png");

    String doc_url="";
    Uri doc_data=null;
    private int check=0;
    String link="null";

    String final_report = "";

    int capture = 1000;
    ImageView stage1, stage2, stage3;


    MainSliderAdapter3 mainSliderAdapter3 = new MainSliderAdapter3();
    PicassoImageLoadingService3 picassoImageLoadingService3 = new PicassoImageLoadingService3(AptosActivity.this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aptos);

        presenter = new AptosPresenter(this);

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

        Slider.init(picassoImageLoadingService3);
        slider.setAdapter(mainSliderAdapter3);

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

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,capture);
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
    public void showResult(AptosResponse body) {
        progressBar.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
        result.setVisibility(View.VISIBLE);
        generatereport.setVisibility(View.VISIBLE);
        int severity = body.getNum();

        result.setText("The blindness level is "+severity+" %");

        if(severity<=20){
            final_report = "Your eyes are safe";
        }
        else if((severity>20)&&(severity<=40)){
            final_report = "You are Mildly Blind";
        }
        else if((severity>40)&&(severity<=60)){
            final_report = "Your Eyes are Moderately Blind";
        }
        else if((severity>60)&&(severity<=80)){
            final_report = "Your eyes have incurred a severe blindness and you need to consult a doctor immediately";
        }
        else{
            final_report = "Your eyes are growing or multiply by rapidly producing new tissue, parts, cells, or offspring\n\n You need to visit an expert immediately";
        }
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        overlay.setVisibility(View.GONE);
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
                            Toast.makeText(AptosActivity.this, "File uploaded on server...\n you can Proceed to stage 2", Toast.LENGTH_SHORT).show();
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

