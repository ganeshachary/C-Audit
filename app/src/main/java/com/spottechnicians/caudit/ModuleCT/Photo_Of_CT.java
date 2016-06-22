package com.spottechnicians.caudit.ModuleCT;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.spottechnicians.caudit.R;
import com.spottechnicians.caudit.models.VisitSingleton;
import com.spottechnicians.caudit.utils.GetLocationService;

public class Photo_Of_CT extends AppCompatActivity {

    public ImageView selectedImageView;
    ImageView ivCtPhoto1, ivCtPhoto2, ivCtPhoto3;
    Bitmap bitmap[] = new Bitmap[3];
    int imageViewIds[] = {R.id.ivCtPhoto1, R.id.ivCtPhoto2, R.id.ivCtPhoto3};
    VisitSingleton visit;
    private byte[] img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo__of__ct);
        visit = VisitSingleton.getInstance();

       // visit = getIntent().getExtras().getParcelable("Visit");
        Toast.makeText(this, visit.getSiteId(), Toast.LENGTH_SHORT).show();

        ivCtPhoto1 = (ImageView) findViewById(R.id.ivCtPhoto1);

        ivCtPhoto2 = (ImageView) findViewById(R.id.ivCtPhoto2);

        ivCtPhoto3 = (ImageView) findViewById(R.id.ivCtPhoto3);

        Toast.makeText(this, "Latitude: " + GetLocationService.LATITUDE_FROM_SERVICE + ", Longitude: " +
                GetLocationService.LONGITUDE_FROM_SERVICE, Toast.LENGTH_LONG).show();

        visit.setLatitude(GetLocationService.LATITUDE_FROM_SERVICE);
        visit.setLongitude(GetLocationService.LONGITUDE_FROM_SERVICE);


    }

    public void next(View view) {
        boolean status = true;
        for (int i = 0; i < bitmap.length; i++) {
            if (bitmap[i] == null) {

                status = false;
            }
        }


        if (status) {
            try
            {
                visit.setPicList("img0",bitmap[0]);
                visit.setPicList("img1",bitmap[1]);
                visit.setPicList("img2",bitmap[2]);
                Intent intent = new Intent(this, Signatuere_Of_CT.class);
                Log.v("atmid", visit.getAtmId());
               // intent.putExtra("Visit2", visit);
                startActivity(intent);

            }
            catch (Exception e)
            {
                Log.v("bitmap",e.toString());
            }

        } else {
            Toast.makeText(this, "Take all the photo", Toast.LENGTH_SHORT).show();
        }


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
            canvas.drawText("ATMID"+visit.getAtmId(), 10, 30, paint);
            canvas.drawText("DATE"+visit.getDate()+"Time"+visit.getTime(), 10, 50, paint);
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
