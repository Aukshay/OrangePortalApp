package help.pawanchouhan.orangeportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register_your_service extends AppCompatActivity {


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    TextInputEditText sname, saddress, fname, lname, contactus;
    TextInputLayout snamelayout,saddresslayout,fnamelayout,lnamelayout,contactnolayout;

    String sname1, saddress1, fname1, lname1, contactus1, imageURL;
    Button btnChoose, btnUpload, submit;
    ImageView imageView;
    Uri filepath;

    Spinner spinner;
    String img, t, stype;

    FirebaseStorage storage;
    StorageReference storageReference;
    StorageTask muploadTask;

    String File;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_your_service);

        storageReference = FirebaseStorage.getInstance().getReference();


        sname = findViewById(R.id.sname);
        saddress = findViewById(R.id.saddress);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        contactus = findViewById(R.id.contactno);
        spinner = findViewById(R.id.categories);
        submit = findViewById(R.id.submit);
        snamelayout = findViewById(R.id.snamelayout);
        saddresslayout = findViewById(R.id.saddresslayout);
        fnamelayout = findViewById(R.id.fnamelayout);
        lnamelayout = findViewById(R.id.lnamelayout);
        contactnolayout = findViewById(R.id.contactnolayout);

        //Firebase Init

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Please Fill your Information", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        t = spinner.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        t = spinner.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        t = spinner.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        t = spinner.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        t = spinner.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        t = spinner.getSelectedItem().toString();
                        Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "choose any one Category", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sname1 = sname.getText().toString();
                saddress1 = saddress.getText().toString();
                fname1 = fname.getText().toString();
                lname1 = lname.getText().toString();
                contactus1 = contactus.getText().toString();
                imageURL = img;

                if (!validateShopName() | !validateShopAddress() | !validateUserFirstName() | !validateUserLastName() | !validateContactUs()) {
                    return;
                }else{

                if (t.equals("Tent")) {
                    stype = "Services/Tent";

                } else if (t.equals("Water Supply")) {
                    stype = "Services/WaterSupply";


                } else if (t.equals("Catering")) {
                    stype = "Services/Catering";


                } else if (t.equals("Music")) {
                    stype = "Services/Music";


                } else if (t.equals("Printing")) {
                    stype = "Services/Printing";


                } else if (t.equals("Decoration")) {
                    stype = "Services/Decoration";


                } else {
                    Toast.makeText(getApplicationContext(), "Choose a category", Toast.LENGTH_SHORT).show();
                }


                    ref = database.getReference(stype);
                    firebase_adapter_tent fire = new firebase_adapter_tent(sname1, saddress1, fname1, lname1, contactus1, imageURL);
                    ref.push().setValue(fire);

                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                sname.setText("");
                saddress.setText("");
                fname.setText("");
                lname.setText("");
                contactus.setText("");
                imageView.setImageDrawable(null);
            }

            }

        });


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {

                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(muploadTask != null && muploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),"upload in Progress",Toast.LENGTH_SHORT).show();
                }else{

                    uploadImage();
                }
            }
        });

    }



                                        //UPLOADING IMAGE CODE


    private void uploadImage() {

        if (filepath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("uploading....");
            progressDialog.show();



            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();

            StorageReference reference = storageReference.child("img/" + data.toString());

            UploadTask uploadTask = reference.putBytes(data);



            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                    img = taskSnapshot.getDownloadUrl().toString();



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Upload Failure" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded" + (int) progress + "%");
                        }
                    });


        }
    }

                                                //CHOOSING IMAGE CODE........|




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            filepath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();





            }
        }
    }


    private boolean validateShopName(){

        if(sname1.isEmpty()){
            snamelayout.setError("Field can't be empty");
            return false;
        }else {
            snamelayout.setError(null);
            return true;
        }

    }

    private boolean validateShopAddress(){

        if(saddress1.isEmpty()){
            saddresslayout.setError("Field can't be empty");
            return false;
        }else {
            saddresslayout.setError(null);
            return true;
        }

    }
    private boolean validateUserFirstName(){

        if(fname1.isEmpty()){
            fnamelayout.setError("Field can't be empty");
            return false;
        }else {
            fnamelayout.setError(null);
            return true;
        }

    }
    private boolean validateUserLastName(){

        if(lname1.isEmpty()){
            lnamelayout.setError("Field can't be empty");
            return false;
        }else {
            lnamelayout.setError(null);
            return true;
        }

    }
    private boolean validateContactUs(){

        if(contactus1.isEmpty()){
            contactnolayout.setError("Field can't be empty");
            return false;
        }else {
            contactnolayout.setError(null);
            return true;
        }

    }

}
