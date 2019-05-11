package com.example.contactlistapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.contactlistapplication.Adater.RecyclerviewAdapter;
import com.example.contactlistapplication.Models.UserInfo;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<UserInfo>userInfolist= new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerviewAdapter recyclerviewAdapter;
    UserInfo userInfo= new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView)findViewById(R.id.recyclerview_holder);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadcontact();

    }

    public void loadcontact(){
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Cursor c =  getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
      List<String>namelist=new ArrayList<>();
      List<String>phonenolist=new ArrayList<>();
        List<Bitmap>imageurl=new ArrayList<>();
        List<String>emaillist=new ArrayList<>();

        while (c.moveToNext()) {

            String phoneNumber="",emailAddress="";
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            String  contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if ( hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false" ;

            if (Boolean.parseBoolean(hasPhone))
            {
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
                while (phones.moveToNext())
                {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }

            // Find Email Addresses
            Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,null, null);
            while (emails.moveToNext())
            {
                emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emails.close();
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
            Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            Cursor image = getContentResolver().query(photoUri,
                    new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);



            //mainActivity.onBackPressed();
            // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();
//            UserInfo info = new UserInfo();
//
//            info.setName(name);
//            info.setPhone(phoneNumber);
              namelist.add(name);
              emaillist.add(emailAddress);
              phonenolist.add(phoneNumber);
              imageurl.add(openPhoto(Long.parseLong(contactId)));

            //userInfolist.add(namelist);

            Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
        }
        c.close();
      //  Log.v("mamama",userInfolist.get(0).getName());
       // Toast.makeText(getApplicationContext(),userInfolist.get(0).getName(),Toast.LENGTH_LONG).show();

        recyclerviewAdapter = new RecyclerviewAdapter(namelist,imageurl,emaillist,phonenolist);
        recyclerView.setAdapter(recyclerviewAdapter);




//        if(cursor.getCount()>0) {
//            while (cursor.moveToNext()) {
//                String phone="1";
//                int hasphone = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
//                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                String Id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                if(hasphone>0) {
//
//                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{Id}, null);
//                    while (cursor2.moveToNext()) {
//                         phone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        Log.e("name ", name);
//                        Log.e("ID", phone);
//
//
//                    }
//                    userInfo.setName(name);
//                    userInfo.setPhone(phone);
//                    userInfolist.add(userInfo);
//                    cursor2.close();
//
//                }
//            }
//        }
//        cursor.close();



    }
    public Bitmap openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }
        return null;

    }


}
