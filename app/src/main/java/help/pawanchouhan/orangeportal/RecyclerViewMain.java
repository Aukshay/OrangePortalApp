package help.pawanchouhan.orangeportal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import static help.pawanchouhan.orangeportal.login.hasPermissions;

public class RecyclerViewMain extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] title = {"Tent", "Water Supply", "Catering", "Music", "Card Printing", "Decoration"};

        String[] about = {"All sort of Tents for all type of occasions!",
                " We will take care of water supply to all your important events.",
                "Need Good Food?\n" + "Choose from the best caterer around you. ",
                "Make it stand out by adding Music",
                "All kind of card printing for all kind of occasions!",
                "Choose the best decorators for your unforgettable events."};

        int[] image = {R.drawable.tent, R.drawable.water, R.drawable.catering, R.drawable.music, R.drawable.card, R.drawable.decoration};
        recyclerView.setAdapter(new RecyclerAdapter(this, image, title, about));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int PERMISSION_ALL = 1;

        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,

                Manifest.permission.CALL_PHONE

        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }





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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home:
                Intent home = new Intent(this, RecyclerViewMain.class);
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
                Intent register = new Intent(this, Register_your_service.class);
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
