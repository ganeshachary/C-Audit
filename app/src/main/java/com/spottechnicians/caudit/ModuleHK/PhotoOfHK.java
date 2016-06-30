package com.spottechnicians.caudit.ModuleHK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.spottechnicians.caudit.DatabaseHandler.DbHelper;
import com.spottechnicians.caudit.Home;
import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.GetLocationService;

public class PhotoOfHK extends AppCompatActivity {

    public ImageView selectedImageView;
    ImageView ivHkPhoto1, ivHkPhoto2, ivHkPhoto3, ivHkPhoto4, ivHkPhoto5,
            ivHkPhoto6, ivHkPhoto7, ivHkPhoto8, ivHkPhoto9, ivHkPhoto10, ivHkPhoto11;

    EditText etName, etNumber;
    DbHelper dbHelper;

    Bitmap bitmap[] = new Bitmap[11];
    int imageViewIds[] = {R.id.ivHkPhoto1, R.id.ivHkPhoto2, R.id.ivHkPhoto3, R.id.ivHkPhoto4, R.id.ivHkPhoto5, R.id.ivHkPhoto6,
            R.id.ivHkPhoto7, R.id.ivHkPhoto8, R.id.ivHkPhoto9, R.id.ivHkPhoto10, R.id.ivHkPhoto11};
    VisitSingleton visit;
    private byte[] img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_of_hk);
        visit = VisitSingleton.getInstance();

        // visit = getIntent().getExtras().getParcelable("Visit");
        Toast.makeText(this, visit.getSiteId(), Toast.LENGTH_SHORT).show();

        ivHkPhoto1 = (ImageView) findViewById(R.id.ivHkPhoto1);

        ivHkPhoto2 = (ImageView) findViewById(R.id.ivHkPhoto2);

        ivHkPhoto3 = (ImageView) findViewById(R.id.ivHkPhoto3);

        ivHkPhoto4 = (ImageView) findViewById(R.id.ivHkPhoto4);

        ivHkPhoto5 = (ImageView) findViewById(R.id.ivHkPhoto5);

        ivHkPhoto6 = (ImageView) findViewById(R.id.ivHkPhoto6);

        ivHkPhoto7 = (ImageView) findViewById(R.id.ivHkPhoto7);

        ivHkPhoto8 = (ImageView) findViewById(R.id.ivHkPhoto8);

        ivHkPhoto9 = (ImageView) findViewById(R.id.ivHkPhoto9);

        ivHkPhoto10 = (ImageView) findViewById(R.id.ivHkPhoto10);

        ivHkPhoto11 = (ImageView) findViewById(R.id.ivHkPhoto11);

        etName = (EditText) findViewById(R.id.etHkName);
        etNumber = (EditText) findViewById(R.id.etHkNumber);


        dbHelper = new DbHelper(this);


        Toast.makeText(this, "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();

        visit.setLatitude(GetLocationService.LATITUDE_FROM_SERVICE);
        visit.setLongitude(GetLocationService.LONGITUDE_FROM_SERVICE);

    }


    public void onSave(View view) {

        boolean status = true;

        for (int i = 0; i < bitmap.length - 4; i++) {
            if (bitmap[i] == null) {

                status = false;
            }
        }



        if (status) {
            try {
                if (visit.getSiteType() != null && visit.getSiteType().equals("CTHK")) {
                    visit.setPicList("hkimg0", bitmap[0]);
                    visit.setPicList("hkimg1", bitmap[1]);
                    visit.setPicList("hkimg2", bitmap[2]);
                    visit.setPicList("hkimg3", bitmap[3]);
                    visit.setPicList("hkimg4", bitmap[4]);
                    visit.setPicList("hkimg5", bitmap[5]);
                    visit.setPicList("hkimg6", bitmap[6]);
                    visit.setPicList("hkimg7", bitmap[7]);
                    visit.setPicList("hkimg8", bitmap[8]);
                    visit.setPicList("hkimg9", bitmap[9]);
                    visit.setPicList("hkimg10", bitmap[10]);
                } else {
                visit.setPicList("img0", bitmap[0]);
                visit.setPicList("img1", bitmap[1]);
                visit.setPicList("img2", bitmap[2]);
                visit.setPicList("img3", bitmap[3]);
                visit.setPicList("img4", bitmap[4]);
                visit.setPicList("img5", bitmap[5]);
                visit.setPicList("img6", bitmap[6]);
                visit.setPicList("img7", bitmap[7]);
                visit.setPicList("img8", bitmap[8]);
                visit.setPicList("img9", bitmap[9]);
                    visit.setPicList("img10", bitmap[10]);
                }
//                Intent intent = new Intent(this, Signatuere_Of_CT.class);
//                Log.v("atmid", visit.getAtmId());
//                // intent.putExtra("Visit2", visit);
//                startActivity(intent);

                String ctName = etName.getText().toString().trim();
                String ctNumber = etNumber.getText().toString().trim();
                if (ctName != null && ctNumber != null && !ctName.equals("") && !ctNumber.equals("")) {
                    visit.setHousekeeperName(ctName);
                    visit.setHousekeeperNumber(ctNumber);
                } else if (ctName == "") {
                    status = false;
                    Toast.makeText(this, "Enter the Name", Toast.LENGTH_LONG).show();
                } else if (ctNumber == "") {
                    status = false;
                    Toast.makeText(this, "Enter the Phone Number", Toast.LENGTH_LONG).show();
                } else {
                    status = false;
                    Toast.makeText(this, "Enter Name and Number", Toast.LENGTH_LONG).show();
                }


                if (status) {

                    if (visit.getSiteType() != null && visit.getSiteType().equals("CTHK")) {
                        if (dbHelper.insertCTHKReport(visit)) {
                            Toast.makeText(this, "CT/HK Report inserted successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this, Home.class);
                            startActivity(intent);
                            this.finish();
                        } else {
                            Toast.makeText(this, "Report insertion was unsuccessfull try again", Toast.LENGTH_LONG).show();
                        }


                    }
                    if (dbHelper.insertHKReport(visit)) {
                        Toast.makeText(this, "HK Report inserted successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, Home.class);
                        startActivity(intent);
                        this.finish();
                    } else {
                        Toast.makeText(this, "HK Report inserted unsuccessfull try again", Toast.LENGTH_LONG).show();
                    }


                }


            } catch (Exception e) {
                Log.v("bitmap", e.toString());
            }

        } else {
            Toast.makeText(this, "Take all the photo", Toast.LENGTH_SHORT).show();
        }


    }

    public void onUpload(View view) {
        onSave(view);
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        this.finish();
    }


    public void next(View view) {
        boolean status = true;


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
