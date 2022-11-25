package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.naiifi.R;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import Models.SalonData;


public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.ViewHolder> {
    private ArrayList<SalonData> salonDataArrayList = new ArrayList<>();
    private Context mContext;

    public SalonAdapter(ArrayList<SalonData> salonDataArrayList, Context mContext) {
        this.salonDataArrayList = salonDataArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SalonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_salon_profile, parent, false);
        return new SalonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonAdapter.ViewHolder holder, int position) {
        SalonData salonData = salonDataArrayList.get(position);

        holder.salonName.setText(salonData.getName());
        holder.salonDistance.setText(salonData.getDistance());
        holder.salonAddress.setText(salonData.getAddress());

        holder.salonBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.salonBookmark.getTag().toString().equals("true")){
                    holder.salonBookmark.setBackgroundResource(R.drawable.ic_bookmark);
                    holder.salonBookmark.setTag("false");

                }
                else if(holder.salonBookmark.getTag().toString().equals("false")){
                    holder.salonBookmark.setBackgroundResource(R.drawable.ic_bookmark_filled);
                    holder.salonBookmark.setTag("true");

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return salonDataArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private ImageView salonImage, salonBookmark;
        private MaterialTextView salonName, salonAddress, salonDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            salonImage = itemView.findViewById(R.id.salonImage);
            salonBookmark = itemView.findViewById(R.id.salonBookmark);
            salonName = itemView.findViewById(R.id.salonName);
            salonAddress = itemView.findViewById(R.id.salonAddress);
            salonDistance = itemView.findViewById(R.id.salonDistance);
        }
    }


}
