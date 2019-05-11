package com.example.contactlistapplication.Adater;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.contactlistapplication.DetailsActivity;
//import com.example.contactlistapplication.Models.UserInfo;
import com.example.contactlistapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.Holder> {


    List<String>namelist=new ArrayList();
    List<Bitmap>imagelist= new ArrayList<>();
    List<String>email=new ArrayList<>();
    List<String>phone_no= new ArrayList<>();

    public RecyclerviewAdapter(List<String> name, List<Bitmap>imagelist2,List<String>email1,List<String>phone_no1) {
        this.email=email1;
        this.phone_no=phone_no1;
        this.namelist = name;
        this.imagelist=imagelist2;
    }


    @NonNull
    @Override
    public RecyclerviewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item,viewGroup,false);
        return new Holder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.Holder holder, final int i) {
        String name= namelist.get(i);
        //String phone = .get(i).getPhone();
        if(imagelist.get(i)!=null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
            holder.imageView.setLayoutParams(layoutParams);
            holder.imageView.setImageBitmap(imagelist.get(i));}
//        }else {
//            holder.imageView.setIMa
//        }

        holder.name.setText(name);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent= new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("name",namelist.get(i));

                intent.putExtra("email",email.get(i));
                intent.putExtra("phone",phone_no.get(i));
                if(imagelist.get(i)!=null)
                {
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    imagelist.get(i).compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    intent.putExtra("image", byteArray);
                }else {
                    intent.putExtra("image","");
                }

                view.getContext().startActivity(intent);
                ((Activity)view.getContext()).finish();


            }
        });
    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        LinearLayout layout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name= (TextView)itemView.findViewById(R.id.name);
            imageView=(ImageView)itemView.findViewById(R.id.image_contact);
            layout=(LinearLayout)itemView.findViewById(R.id.layout_click);
        }
    }
}
