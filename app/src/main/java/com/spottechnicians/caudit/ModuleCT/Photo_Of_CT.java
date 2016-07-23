package com.spottechnicians.caudit.ModuleCT;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spottechnicians.caudit.Activities.Home;
import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.ModuleHK.HKQuestions;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.GetLocationService;
import com.spottechnicians.caudit.utils.UtilCT;
import com.spottechnicians.caudit.utils.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo_Of_CT extends AppCompatActivity {

    public ImageView selectedImageView;
    ImageView ivCtPhoto1, ivCtPhoto2, ivCtPhoto3;
    Bitmap bitmap[] = new Bitmap[3];
    int imageViewIds[] = {R.id.ivCtPhoto1, R.id.ivCtPhoto2, R.id.ivCtPhoto3};
    EditText etName, etNumber;
    VisitSingleton visit;
    DbHelper dbHelper;
    String mCurrentPhotoPath;//this will hold of the path of currently clicked image
    Uri currentUri;//this will hold the Uri of currently clicked image
    private byte[] img = null;
    private String backMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo__of__ct);
        visit = VisitSingleton.getInstance();

       // visit = getIntent().getExtras().getParcelable("Visit");
        Toast.makeText(this, visit.getSiteId(), Toast.LENGTH_SHORT).show();

        dbHelper = new DbHelper(this);

        ivCtPhoto1 = (ImageView) findViewById(R.id.ivCtPhoto1);

        ivCtPhoto2 = (ImageView) findViewById(R.id.ivCtPhoto2);

        ivCtPhoto3 = (ImageView) findViewById(R.id.ivCtPhoto3);

        etName = (EditText) findViewById(R.id.etCtName);
        etNumber = (EditText) findViewById(R.id.etCtNumber);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnUpload = (Button) findViewById(R.id.btnUpload);
        Button btnToHK = (Button) findViewById(R.id.btnHkQuestions);

        if (visit.getSiteType() != null && visit.getSiteType().equals("CTHK")) {
            btnSave.setVisibility(View.GONE);
            btnUpload.setVisibility(View.GONE);
            btnToHK.setVisibility(View.VISIBLE);

        }

        Toast.makeText(this, "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();

        visit.setLatitude(GetLocationService.LATITUDE_FROM_SERVICE);
        visit.setLongitude(GetLocationService.LONGITUDE_FROM_SERVICE);
        getSupportActionBar().setTitle(visit.getAtmId() + " : CT");

        if (visit.getCt()[1].trim().toString().equals("NA")) {
            ivCtPhoto1.setImageResource(R.mipmap.ic_launcher);
            bitmap[0] = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);
            ivCtPhoto1.setClickable(false);
        }

        backMessage = getString(R.string.backMessageFromPhoto);

        if (Utility.getLanguage(this) != null && Utility.getLanguage(this).equals("Hindi")) {
            backMessage = getString(R.string.backMessageFromPhotoHindi);
            changeTextToHindi();
        }

    }

    private void changeTextToHindi() {

        ((TextView) findViewById(R.id.tvCTPhotoHeading)).setText(R.string.photoHeadingHindi);

        for (int i = 0; i < UtilCT.getPhotoStringHidniIds().length; i++) {
            ((TextView) findViewById(UtilCT.getPhotoTextIds()[i])).setText(UtilCT.getPhotoStringHidniIds()[i]);
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (visit.checkEmptyImage(bitmap)) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(backMessage)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Photo_Of_CT.this.finish();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }
    }





    private boolean validateNameNo() {
        boolean status = true;


        String ctName = etName.getText().toString();
        String ctNumber = etNumber.getText().toString();

        if (ctName.equals("") && ctNumber.equals("")) {
            status = false;
            Toast.makeText(this, "Enter Name and Number", Toast.LENGTH_LONG).show();

        } else if (ctName.equals("")) {
            status = false;
            Toast.makeText(this, "Enter the Name", Toast.LENGTH_LONG).show();
        } else if (ctNumber.equals("")) {
            status = false;
            Toast.makeText(this, "Enter the Phone Number", Toast.LENGTH_LONG).show();
        } else if (ctNumber.length() != 10) {
            status = false;
            Toast.makeText(this, "Enter valid Phone Number", Toast.LENGTH_LONG).show();
        } else {
            visit.setCaretakerName(ctName);
            visit.setCaretakerNumber(ctNumber);
        }

        return status;
    }

    public void onSave(View view) {

        if (checkImage()) {

            if (validateNameNo()) {
                if (dbHelper.insertCTReport(visit)) {
                    Toast.makeText(this, "CT Report inserted successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // call this to finish the current activity
                } else {
                    Toast.makeText(this, "CT Report inserted unsuccessfull try again", Toast.LENGTH_LONG).show();
                }


            }
        } else {
            Home.printToast("Take All photos", this);
        }


    }

    public void onUpload(View view) {
        onSave(view);
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    public void proceedToHk(View view) {

        if (checkImage()) {

            if (validateNameNo()) {
                startActivity(new Intent(this, HKQuestions.class));
            }
        } else {
            Home.printToast("Take All photos", this);
        }


    }

    public boolean checkImage() {
        boolean status = true;
        for (int i = 0; i < bitmap.length; i++) {
            if (bitmap[i] == null) {

                status = false;
            }
        }


        if (status) {
            try
            {

                    visit.setPicList("ctimg0", bitmap[0]);
                    visit.setPicList("ctimg1", bitmap[1]);
                    visit.setPicList("ctimg2", bitmap[2]);
                return status;

            }
            catch (Exception e)
            {
                Log.v("bitmap",e.toString());
            }

        } else {
//            Toast.makeText(this, "Take all the photo", Toast.LENGTH_SHORT).show();
            return status;
        }

        return status;
    }

    public void captureImage(View v) {

        selectedImageView = (ImageView) v;


    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission", "Permission is granted");
                return true;
            } else {

                Log.v("permission", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission", "Permission is granted");
            return true;
        }


    }

/*

    public void imageClicked(View view) {
        selectedImageView = (ImageView) view;
        if (isStoragePermissionGranted()) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
           // startActivityForResult(intent, 0);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 0);
            }


        } else {
            Toast.makeText(this, "app donot have the permision", Toast.LENGTH_SHORT).show();
        }


    }
*/

    public void imageClicked(View view) {
        selectedImageView = (ImageView) view;
        if (isStoragePermissionGranted()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;

                photoFile = createImageFile();

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    if (Build.VERSION.SDK_INT < 21) {
                        currentUri = Uri.fromFile(photoFile); //This Uri.fromFile may throw a security exception on build version over 23

                    } else {
                        currentUri = FileProvider.getUriForFile(this, "com.spottechnicians.caudit.fileprovider",
                                photoFile);
                    }    //This type of uri is different than Uri type used in kitkat and lower versions
                    //therefor it gives err when we use this code on Kitkat and lower


                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentUri);
                    startActivityForResult(takePictureIntent, 0);

                }
            }
        } else {
            Toast.makeText(this, "app dont have the permision", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("permission", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            //imageClicked();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {


            //    Bitmap bp = (Bitmap) data.getExtras().get("data");
//            if(data!=null){
//            Uri imageUri = data.getData();

//                if(imageUri!=null){
            if (currentUri != null) {
                Log.e("ManB", "The Uri is " + currentUri.toString());


                Bitmap bp = null;

                InputStream input = null;

                try {
                    input = this.getContentResolver().openInputStream(currentUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e("ManB", "Error while creating img file  Error: " + e.toString());
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inSampleSize = 2;

                bp = BitmapFactory.decodeStream(input, null, options);

                Utility.printToast(bp.getWidth() + "  " + bp.getHeight(), this);

         /*   options.inSampleSize=2;

            options.inJustDecodeBounds=false;

            bp=BitmapFactory.decodeStream(input,null,options);
*/


/*
            try {
                bp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bp!=null)*/
            /*if(bp.getHeight()>bp.getWidth())
            {
                bp =Bitmap.createScaledBitmap(bp ,355,632, true);
            }
            else {
                bp = Bitmap.createScaledBitmap(bp, 632, 355, true);}*/
                if (bp.getWidth() > bp.getHeight()) {
                    bp = Bitmap.createScaledBitmap(bp, 632, 355, true);
                } else {
                    bp = Bitmap.createScaledBitmap(bp, 355, 632, true);
                }


                Bitmap.Config config = bp.getConfig();
                if (config == null) {
                    config = Bitmap.Config.ARGB_8888;
                }

                Bitmap newBitmap = Bitmap.createBitmap(bp.getWidth(), bp.getHeight(), config);
                Canvas canvas = new Canvas(newBitmap);


         /*   canvas.drawBitmap(bp, 0, 0, null);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            canvas.drawText("ATMID : " + visit.getAtmId(),50,80, paint);
            canvas.drawText("DATETIME :" + visit.getDatetime(), 50, 180, paint);
            canvas.drawText("Lat: " + visit.getLatitude() + "Long: " + visit.getLongitude(),50,280, paint);*/

                canvas.drawBitmap(bp, 0, 0, null);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                //   paint.setColor(Color.parseColor("#F50057"));
                paint.setTextSize(16);
                //  paint.setTypeface(Typeface.create())
                // paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setFakeBoldText(true);
                canvas.drawText("ATMID : " + visit.getAtmId(), 5, 15, paint);
                canvas.drawText("DATETIME :" + visit.getDatetime(), 5, 35, paint);
                canvas.drawText("Lat: " + visit.getLatitude() + "Long: " + visit.getLongitude(), 5, 55, paint);


                // Log.e("compress",newBitmap.getByteCount()+"");

                for (int i = 0; i < imageViewIds.length; i++) {
                    if (imageViewIds[i] == selectedImageView.getId()) {
                        bitmap[i] = newBitmap;


                        //storing in for check in as a image file
                        try {
                            storeAsImage(bitmap[i]);//this will store img to pictures dir
                        } catch (IOException e) {
                            e.printStackTrace();
                            Home.printToast("Unable to store as JPG", this);
                        }
                    }
                }
                selectedImageView.setImageBitmap(newBitmap);


                //will check if imgUri is null
            } else {
                Log.e("ManB", "The Uri is null");
            }


//            }//change if data null
//            else{Log.e("ManB","data we got is null");}


        } else {
            Toast.makeText(this, "image cancelled", Toast.LENGTH_SHORT).show();
        }


    }


/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {

            Bitmap bp = (Bitmap) data.getExtras().get("data");


            Bitmap.Config config = bp.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }

            Bitmap newBitmap = Bitmap.createBitmap(bp.getWidth(), bp.getHeight(), config);
            Canvas canvas = new Canvas(newBitmap);

            canvas.drawBitmap(bp, 0, 0, null);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(10);
            canvas.drawText("ATMID : " + visit.getAtmId(), 10, 30, paint);
            canvas.drawText("DATETIME :" + visit.getDatetime(), 10, 50, paint);
            canvas.drawText("Lat: " + visit.getLatitude() + "Long: " + visit.getLongitude(), 10, 70, paint);



            // Log.e("compress",newBitmap.getByteCount()+"");

            for (int i = 0; i < imageViewIds.length; i++) {
                if (imageViewIds[i] == selectedImageView.getId()) {
                    bitmap[i] = newBitmap;
                }
            }
            selectedImageView.setImageBitmap(newBitmap);



        } else {
            Toast.makeText(this, "image cancelled", Toast.LENGTH_SHORT).show();
        }


    }
*/


    private void storeAsImage(Bitmap bmp) throws IOException {
        //creating jpg from bitmap
        //  String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MANB100_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            Log.e("ManB", "Error while creating img file  Error: " + e.toString());
            e.printStackTrace();

        }


    }

    private File createImageFile() {

        File storageDir = null;

        // Create an image file name
        // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //  String imageFileName = "JPEG_" + timeStamp + "_";

//        if(Build.VERSION.SDK_INT < 19)
//        {
        // storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

      /*  }
        else
        {
        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);}*/
        File image = null;
//        try {

        image = new File(storageDir, "temp.jpg");

          /*  image = File.createTempFile(
                    imageFileName,  *//* prefix *//*
                    ".jpg",         *//* suffix *//*
                    storageDir      *//* directory
            ); */

//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("ManB ","Error while cretin img "+e.toString());
//        }

        //if image exists delete and create it again
        if (image.exists()) {
            image.delete();
            image = new File(storageDir, "temp.jpg");

        }


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }




}
