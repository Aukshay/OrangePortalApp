package help.pawanchouhan.orangeportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class signup extends AppCompatActivity {

    TextInputEditText Email,FirstName,LastName,Contact,Password;
    TextInputLayout EmailLayout,FirstNameLayout,LastNameLayout,ContactLayout,PasswordLayout;
    Button Choose,finish;
    ImageView UserImage;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    String email,firstName,lastName,contact,image,img,userid;
    Uri filepath;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    String id = "users";
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Email = findViewById(R.id.Email);
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        Contact = findViewById(R.id.Contact);
        Password = findViewById(R.id.Password);
        UserImage = findViewById(R.id.dp);
        Choose = findViewById(R.id.choose);
        finish = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });
    }







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
                UserImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadImage();
        }
    }

    private void uploadImage() {

        if (filepath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("uploading....");
            progressDialog.show();



            UserImage.setDrawingCacheEnabled(true);
            UserImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) UserImage.getDrawable()).getBitmap();
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
                    Log.d("Image bhi hai", img);


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


    private void registeruser() {

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();


        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            Email.setError("Please enter a valid Email");
            Email.requestFocus();
            return;
        }


        if(password.isEmpty()){
            Password.setError("Password can't be empty");
            Password.requestFocus();
            return;
        }

        if(password.length()<6){
            Password.setError("Minimum length of password should be 6");
            Password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    FirebaseUser user = mAuth.getCurrentUser();
                    userid = user.getUid().toString();
                    Log.d("User aa gya hath mai", userid);

                    uploadingtask();

                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void uploadingtask() {

        progressBar.setVisibility(View.VISIBLE);

        email = Email.getText().toString();
        firstName = FirstName.getText().toString();
        lastName = LastName.getText().toString();
        contact = Contact.getText().toString();
        image = img;

        if(email.isEmpty()){
            Email.setError("Field can't be empty");
            Email.requestFocus();
            return;
        }
        if(firstName.isEmpty()){
            FirstName.setError("Field can't be empty");
            FirstName.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            LastName.setError("Field can't be empty");
            LastName.requestFocus();
            return;
        }
        if(contact.isEmpty()){
            Contact.setError("Field can't be empty");
            Contact.requestFocus();
            return;
        }
        if(image.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Choose Image",Toast.LENGTH_SHORT).show();
            return;
        }

        ref = database.getReference("users");
        signup_adapter signupAdapter = new signup_adapter();
        signupAdapter.setEmail(email);
        signupAdapter.setFirstName(firstName);
        signupAdapter.setLastName(lastName);
        signupAdapter.setContact(contact);
        signupAdapter.setImage(image);
        ref.child(userid).setValue(signupAdapter);

        Log.d("Firebase Done", "Hari OOOOM ");

        Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.GONE);

        Email.setText(null);
        FirstName.setText(null);
        LastName.setText(null);
        Contact.setText(null);
        Password.setText(null);
        UserImage.setImageDrawable(null);



    }
}
