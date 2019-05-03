package help.pawanchouhan.orangeportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class contactus extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ImageView support;
    CardView call, email;
    String number = "7976557329";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        support = findViewById(R.id.support);
        call = findViewById(R.id.call);
        email = findViewById(R.id.email);

        call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"email Us now",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"aukshaykar@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "About the Orange Portal Application");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });



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
