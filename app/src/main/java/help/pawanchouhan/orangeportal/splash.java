package help.pawanchouhan.orangeportal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class splash extends AppCompatActivity {
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser users;
    FirebaseAuth mAuth;
    DatabaseReference ref;


    private final int SPLASH_DISPLAY_LENGTH = 1000 * 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users");
        users = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(users == null) {

                    Intent mainIntent = new Intent(splash.this, login.class);
                    startActivity(mainIntent);
                    finish();
                }
                else{

                    Intent secondIntent = new Intent(splash.this, RecyclerViewMain.class);
                    startActivity(secondIntent);
                    finish();

                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
