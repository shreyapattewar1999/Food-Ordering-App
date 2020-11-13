package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import static android.net.Uri.fromFile;

public class GiveReview extends AppCompatActivity {

    ImageView imageView, dish_photo;
    TextInputLayout description;
    Button post;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermissions;  //camera and storage
    private String[] storagePermissions; //only storage

    public Uri imageUri;
    private String description_image;
    String currentPhotoPath;

    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_review);

        imageView = (ImageView) findViewById(R.id.image_upload);
        dish_photo = (ImageView) findViewById(R.id.dish_photo);
        description = (TextInputLayout) findViewById(R.id.description);

        post = (Button)findViewById(R.id.post);

        dbHelper = new DBHelper(this);

        //init permisssion arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show image pick dialog
                imagePickDialog();


            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();


            }
        });

    }


    @Override
    public void onBackPressed(){

        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(intent);

        super.onBackPressed();
    }

    private void inputData() {
        description_image = description.getEditText().getText().toString();
        Boolean id = dbHelper.insertReviews(""+imageUri, description_image);

//        Toast.makeText(this, ""+imageUri,Toast.LENGTH_LONG).show();
        if (id==true){
            Toast.makeText(this, "You have successfully added review"+imageUri, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Record is not added", Toast.LENGTH_LONG).show();

        }


    }

    private void imagePickDialog() {
        //options to display in dialog
        String[] options = {"Camera" , "Gallery"};

        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //title
        builder.setTitle("Pick Image From");

        //set itmes/options
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //handle clicks

                if (which == 0){
                    //camera clicked
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        //camera permission granted
                        pickFromCamera();

                    }
                }

                else if (which == 1){
                    //gallery clicked
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        //storage permission granted
                        pickFromGallery();

                    }
                }

            }
        });

        //create/show dialog
        builder.create().show();

    }

    private void pickFromGallery() {
        //intent to pick image from gallery , the image will be returned in onActivityResult method
        Intent galleryIntent  = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        //intent to pick image from CAMERA, the image will be returned in onActivityResult method

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image_Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image_Description");

        //put image uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to open camera for image
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);






    }

    //to check if storage permission is enabled or  not
    private Boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private  boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //result of permission allowed denied

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length > 0){
                    //if allowed returns true otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        //both permissions allowed
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Camera and Storage Permissions are required", Toast.LENGTH_LONG).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length > 0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallery();
                    }
                    else{
                        Toast.makeText(this, "Storage Permissions are required", Toast.LENGTH_LONG).show();


                    }

                }

            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera or gallery will be received here

        if ( resultCode == RESULT_OK){
            //image is picked
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                //PICKED FROM GALLERY
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                //PICKED FROM CAMERA
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //cropped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    //set image
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    dish_photo.setImageBitmap(bitmap);

//                    File f = new File(String.valueOf(resultUri));
//                    dish_photo.setImageURI(fromFile(f));
                    dish_photo.setImageURI(resultUri);
                }
                else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //error
                Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_LONG).show();
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}