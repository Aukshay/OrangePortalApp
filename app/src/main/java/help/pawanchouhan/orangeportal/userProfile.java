package help.pawanchouhan.orangeportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class userProfile extends AppCompatActivity {

   android.support.v7.widget.Toolbar toolbar;
    CardView card;
    ImageView image1;
    TextView firstName,email,contact;
    DatabaseReference ref;
    FirebaseUser users;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String userid;
    signup_adapter use;
    String email1,firstName1,lastName1,contact1,image2;
    ProgressBar progressBar,progressBar2;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        card = findViewById(R.id.card);
        image1 = findViewById(R.id.image);
        firstName = findViewById(R.id.firstName);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressbar);
        progressBar2 = findViewById(R.id.progressbar2);
        logout = findViewById(R.id.logout);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                users = FirebaseAuth.getInstance().getCurrentUser();
                if (users != null) {
                    userid = users.getUid();

                    ref.child(userid).addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            Log.d("CHAL GYA SHAYAD", dataSnapshot.getKey());
                            if(dataSnapshot.exists()) {
                                progressBar.setVisibility(View.VISIBLE);
                                signup_adapter use = dataSnapshot.getValue(signup_adapter.class);

                                email1 = use.getEmail();
                                firstName1 = use.getFirstName();
                                lastName1 = use.getLastName();
                                contact1 = use.getContact();
                                image2 = use.getImage();


                                firstName.setText(firstName1 + " " + lastName1);
                                email.setText(email1);
                                contact.setText(contact1);
                                Log.d("EMAILS", email1);
                                Log.d("CONTACT", contact1);

                                progressBar2.setVisibility(View.VISIBLE);
                                Picasso.get().load(image2).into(image1, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                        progressBar2.setVisibility(View.GONE);

                                    }
                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Log.d("user ID", userid);
                } else {
                    Intent home = new Intent(getApplicationContext(),login.class);
                    startActivity(home);
                }




            }
        };

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                Intent home = new Intent(getApplicationContext(),login.class);
                startActivity(home);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.home:
                Intent home = new Intent(this,RecyclerViewMain.class);
                startActivity(home);
                return true;

            case R.id.contactus:
                Intent contactus = new Intent(this,contactus.class);
                startActivity(contactus);
                return true;

            case R.id.about:
                Intent about = new Intent(this,aboutus.class);
                startActivity(about);
                return true;

            case R.id.register1:
                Intent register = new Intent(this,Register_your_service.class);
                startActivity(register);
                return true;

            case R.id.feedback:
                Intent feedback = new Intent(this, help.pawanchouhan.orangeportal.feedback.class);
                startActivity(feedback);
                return true;

            case R.id.profile:
                Intent profile = new Intent(this, help.pawanchouhan.orangeportal.userProfile.class);
                startActivity(profile);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
