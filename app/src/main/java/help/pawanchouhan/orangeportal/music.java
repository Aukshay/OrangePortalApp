package help.pawanchouhan.orangeportal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class music extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView4;
    RecyclerView.LayoutManager layoutManager;

    Toolbar toolbar;

    FrameLayout progressBarHolder;
    String myLog = "myLog";
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Services").child("Music");
        databaseReference.keepSynced(true);

        recyclerView4 = findViewById(R.id.recyclerView4);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBarHolder = findViewById(R.id.progressBarHolder);



        new MyTask().execute();


    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 2; i++) {
                    Log.d(myLog, "Emulating some task.. Step " + i);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
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

            case R.id.profile:
                Intent profile = new Intent(this, help.pawanchouhan.orangeportal.userProfile.class);
                startActivity(profile);
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<firebase_adapter_tent,fatViewHolder>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<firebase_adapter_tent, fatViewHolder>
                (firebase_adapter_tent.class,R.layout.tent,fatViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(fatViewHolder viewHolder, firebase_adapter_tent model, int position) {

                viewHolder.setImage(model.getImage());
                viewHolder.setShopname(model.getShopname());
                viewHolder.setAddress(model.getAddress());
                viewHolder.setFirstName(model.getFirstName());
                viewHolder.setLastName(model.getLastName());
                viewHolder.setContact(model.getContact());
            }
        };

        recyclerView4.setAdapter(firebaseRecyclerAdapter);

    }

    public static class fatViewHolder extends RecyclerView.ViewHolder {
        View view;

        public fatViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setShopname(String shopname) {
            TextView shop_name = view.findViewById(R.id.shopname);
            shop_name.setText(shopname);
        }

        public void setAddress(String address) {
            TextView shop_address = view.findViewById(R.id.shopaddress);
            shop_address.setText(address);
        }

        public void setFirstName(String FirstName) {
            TextView first_name = view.findViewById(R.id.username);
            first_name.setText(FirstName);
        }

        public void setLastName(String LastName) {
            TextView last_name = view.findViewById(R.id.username1);
            last_name.setText(LastName);
        }

        public void setContact(String contact) {
            TextView contacts = view.findViewById(R.id.contact);
            contacts.setText(contact);
        }
        public void setImage(String image) {
            ImageView img = view.findViewById(R.id.userimage);
            Picasso.get().load(image).into(img, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }




    }

}
