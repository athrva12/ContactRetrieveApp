package com.example.contactlistapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name,phone,email;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        //Toast.makeText(this,intent.getStringExtra("name"),Toast.LENGTH_LONG).show();
        imageView= (ImageView)findViewById(R.id.detail_image);
        name=(TextView)findViewById(R.id.detail_name);
        email=(TextView)findViewById(R.id.detail_email);
        phone=(TextView)findViewById(R.id.detail_phone_no);
        linearLayout=(LinearLayout)findViewById(R.id.layout_email);
        name.setText(intent.getStringExtra("name"));
        Bitmap bmp;
        name.setText(intent.getStringExtra("name"));
        byte[] byteArray = intent.getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        phone.setText(intent.getStringExtra("phone"));
           if(bmp!=null)
           {
               imageView.setImageBitmap(bmp);
           }
           if(intent.getStringArrayExtra("email")==null)
           {
            linearLayout.setVisibility(View.GONE);
           }else {
               email.setText(intent.getStringExtra("email"));
           }
    }
}
