package com.spottechnicians.caudit.ModuleCT;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.spottechnicians.caudit.Activities.Home;
import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.ModuleHK.HKQuestions;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.GetLocationService;

public class Photo_Of_CT extends AppCompatActivity {

    public ImageView selectedImageView;
    ImageView ivCtPhoto1, ivCtPhoto2, ivCtPhoto3;
    Bitmap bitmap[] = new Bitmap[3];
    int imageViewIds[] = {R.id.ivCtPhoto1, R.id.ivCtPhoto2, R.id.ivCtPhoto3};
    EditText etName, etNumber;
    VisitSingleton visit;
    DbHelper dbHelper;
    private byte[] img = null;

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

        if (visit.getCt()[1].trim().toString().equals("NA")) {
            ivCtPhoto1.setImageResource(R.mipmap.ic_launcher);
            bitmap[0] = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);


            ivCtPhoto1.setClickable(false);
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
                    startActivity(intent);
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
                if (visit.getSiteType() != null && visit.getSiteType().equals("CTHK")) {
                    visit.setPicList("ctimg0", bitmap[0]);
                    visit.setPicList("ctimg1", bitmap[1]);
                    visit.setPicList("ctimg2", bitmap[2]);

                } else {
                visit.setPicList("img0",bitmap[0]);
                visit.setPicList("img1",bitmap[1]);
                    visit.setPicList("img2", bitmap[2]);
                }

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


    public void imageClicked(View view) {
        selectedImageView = (ImageView) view;
        if (isStoragePermissionGranted()) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "app donot have the permision", Toast.LENGTH_SHORT).show();
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


}
