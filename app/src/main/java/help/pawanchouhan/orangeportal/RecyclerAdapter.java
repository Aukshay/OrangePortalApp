package help.pawanchouhan.orangeportal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{

    CardView cardView;
    ItemClickListener itemClickListener;
    ImageView imageView1;
    TextView services;
    TextView about;


    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView= itemView.findViewById(R.id.txtDescription);
        imageView1 = itemView.findViewById(R.id.image);
        services = itemView.findViewById(R.id.title);
        about = itemView.findViewById(R.id.abouttext);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){

        this.itemClickListener = itemClickListener;
    }



    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),true);

        return true;
    }
}

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

   private Context context;

    private String[] servicename, aboutservice;
    private int[] img;

    public RecyclerAdapter(Context context,int[] img, String[] servicename, String[] aboutservice) {

        this.context = context;

        this.img = img;
        this.servicename = servicename;
        this.aboutservice = aboutservice;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item_recycler_view,parent,false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {


        int image = img[position];
        String title = servicename[position];
        String about = aboutservice[position];


        holder.services.setText(title);
        holder.about.setText(about);
        holder.imageView1.setImageResource(image);



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                switch (position){

                    case 0:
                        Intent tent = new Intent(context,Tent.class);
                        context.startActivity(tent);
                        break;

                    case 1:
                        Intent water = new Intent(context,water.class);
                        context.startActivity(water);
                        break;
                    case 2:
                        Intent catering = new Intent(context,catering.class);
                        context.startActivity(catering);
                        break;
                    case 3:
                        Intent music = new Intent(context,music.class);
                        context.startActivity(music);
                        break;
                    case 4:
                        Intent printing = new Intent(context,printing.class);
                        context.startActivity(printing);
                        break;
                    case 5:
                        Intent decoration = new Intent(context, decoration.class);
                        context.startActivity(decoration);
                        break;

                     default:
                         Toast.makeText(context,"welcome",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
       return 6;
    }
}
