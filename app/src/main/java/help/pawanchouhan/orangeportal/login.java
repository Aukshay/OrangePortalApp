package help.pawanchouhan.orangeportal;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout EmailLayout,PasswordLayout;
    TextInputEditText Email,Password;
    Button login,skip;
    TextView signup;
    ProgressBar progressbar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EmailLayout = findViewById(R.id.EmailLayout);
        PasswordLayout = findViewById(R.id.PasswordLayout);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        skip = findViewById(R.id.skip);

        mAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressbar);

        signup.setOnClickListener(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipit();
            }
        });

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void skipit() {
        Intent services = new Intent(getApplicationContext(),RecyclerViewMain.class);
        startActivity(services);
    }

    private void  userLogin(){

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
            Password.setError("wrong Password");
            Password.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    progressbar.setVisibility(View.GONE);
                    Intent profile = new Intent(getApplicationContext(),RecyclerViewMain.class);
                    profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(profile);

                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.signup:
                Intent signup = new Intent(this,signup.class);
                startActivity(signup);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("EXIT")
                .setMessage("Are you sure you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(mAuth == null | mAuth != null) {
                            finishAffinity();
                        }
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
