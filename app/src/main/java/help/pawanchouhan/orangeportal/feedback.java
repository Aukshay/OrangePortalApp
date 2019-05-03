package help.pawanchouhan.orangeportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class feedback extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    TextInputEditText uname,uemails,uthoughts;
    TextInputLayout name,emails,thoughts;
    Button submitthoughts;
    String feedbackservice;
    String username,useremail,userthoughts;
    ImageView thanks;

    FirebaseStorage storage;
    StorageReference storageReference;

   android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        storageReference = FirebaseStorage.getInstance().getReference();
       // ref.child("Feedback");
       // ref.keepSynced(true);

        uname = findViewById(R.id.uname);
        uemails = findViewById(R.id.uemails);
        uthoughts = findViewById(R.id.uthoughts);
        name = findViewById(R.id.name);
        emails = findViewById(R.id.emails);
        thoughts = findViewById(R.id.thoughts);
        toolbar = findViewById(R.id.toolbar);
        thanks = findViewById(R.id.thanks);
        setSupportActionBar(toolbar);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        submitthoughts = findViewById(R.id.submitbutton);

        feedbackservice = "Feedback";
        thanks.setVisibility(View.GONE);

        submitthoughts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = uname.getText().toString();
                useremail = uemails.getText().toString();
                userthoughts = uthoughts.getText().toString();

                if (!validateName() | !validateEmail() | !validateThoughts()) {
                    return;
                } else{

                ref = database.getReference(feedbackservice);
                feedback_adapter feedBack = new feedback_adapter(username, useremail, userthoughts);
                ref.push().setValue(feedBack);

            }

                Toast.makeText(getApplicationContext(),"Thank you",Toast.LENGTH_SHORT).show();
                uname.setText("");
                uemails.setText("");
                uthoughts.setText("");

                thanks.setVisibility(View.VISIBLE);
            }
        });


    }

    private boolean validateName(){

        if(username.isEmpty()){
            name.setError("Field can't be empty");
            return false;
        }else {
            name.setError(null);
            return true;
        }

    }

    private boolean validateEmail(){

        if(useremail.isEmpty()){
            emails.setError("Field can't be empty");
            return false;
        }else {
            emails.setError(null);
            return true;
        }

    }

    private boolean validateThoughts(){

        if(userthoughts.isEmpty()){
            thoughts.setError("Field can't be empty");
            return false;
        }else {
            thoughts.setError(null);
            return true;
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
