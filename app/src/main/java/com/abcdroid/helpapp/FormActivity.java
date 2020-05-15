package com.abcdroid.helpapp;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class FormActivity extends AppCompatActivity {
    String description="";
    EditText desc;
    FragmentManager fm = getSupportFragmentManager();
    ImageView iv;
    LinearLayout l2, l1;
    private Bitmap bitmap;
    ImageButton animalBtn, childBtn, educationBtn, foodBtn, healthBtn, smokingBtn;
    String toMail;
    String fromMail;
    int selection=0;

    private int PICK_IMAGE_REQUEST = 1;

    private String filePath;

    FloatingActionButton fab;
    public static final String UPLOAD_URL = "http://techjeans.in/helpapp/upload.php";
    public static final String UPLOAD_KEY = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        l1 = (LinearLayout) findViewById(R.id.ll1);
        l2 = (LinearLayout) findViewById(R.id.ll2);
        desc = (EditText) findViewById(R.id.editText);

        animalBtn = (ImageButton)findViewById(R.id.ib_animal);
        childBtn = (ImageButton) findViewById(R.id.ib_child);
        educationBtn = (ImageButton) findViewById(R.id.ib_education);
        foodBtn = (ImageButton) findViewById(R.id.ib_food);
        healthBtn = (ImageButton) findViewById(R.id.ib_health);
        smokingBtn = (ImageButton) findViewById(R.id.ib_smoking);

        animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMail="report@animalwelfare.com";
                selection=1;
                deselectAll();
                animalBtn.setImageResource(R.drawable.ic_animals_chosen);
            }
        });

        childBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMail="report@childcare.com";
                selection=1;
                deselectAll();
                childBtn.setImageResource(R.drawable.ic_children_chosen);
            }
        });

        educationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMail="report@educateindia.com";
                selection=1;
                deselectAll();
                educationBtn.setImageResource(R.drawable.ic_education_chosen);
            }
        });

        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMail="report@sendfood.com";
                selection=1;
                deselectAll();
                foodBtn.setImageResource(R.drawable.ic_food_chosen);
            }
        });

        healthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMail="report@cureindia.com";
                selection=1;
                deselectAll();
                healthBtn.setImageResource(R.drawable.ic_health_chosen);
            }
        });

        smokingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMail="report@nomoredomesticviolence.com";
                selection=1;
                deselectAll();
                smokingBtn.setImageResource(R.drawable.ic_domestic_chosen);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //showFileChooser();
            }
        });
        iv = (ImageView) findViewById(R.id.imageView);

    }

    public void deselectAll(){
        animalBtn.setImageResource(R.drawable.ic_animals);
        childBtn.setImageResource(R.drawable.ic_children);
        educationBtn.setImageResource(R.drawable.ic_education);
        foodBtn.setImageResource(R.drawable.ic_food);
        healthBtn.setImageResource(R.drawable.ic_health);
        smokingBtn.setImageResource(R.drawable.ic_domestic);
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        desc.setVisibility(View.VISIBLE);
        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.VISIBLE);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selection == 1) {
                    uploadImage();
                } else
                    Toast.makeText(getApplicationContext(), "Please fill the form", Toast.LENGTH_SHORT).show();
            }
        });

   bitmap = (Bitmap) data.getExtras().get("data");
        try {
            saveToInternalStorage(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.parse(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        iv.setImageBitmap(bitmap);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData().toString();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(filePath));
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            iv.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            filePath=directory.getAbsolutePath();
            Toast.makeText(getApplicationContext(),"saved"+filePath,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return directory.getAbsolutePath();
    }
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FormActivity.this, "Uploading...", null,true,true);
               description=desc.getText().toString();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                DialoagFragment alertdFragment = new DialoagFragment();
                alertdFragment.show(fm, "Alert Dialog Fragment");

            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put("message",description);
                data.put("tomail",toMail);
                data.put("frommail",EmailFetcher.getEmail(getApplicationContext()));

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
