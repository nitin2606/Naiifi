package Adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.naiifi.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import Models.SalonListModel;

public class SalonListAdapter extends RecyclerView.Adapter<SalonListAdapter.ViewHolder> {
    private ArrayList<SalonListModel> arrayList = new ArrayList<>() ;
    private Context mContext;

    public SalonListAdapter(ArrayList<SalonListModel> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SalonListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salon_list_layout, parent, false);
        return new SalonListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonListAdapter.ViewHolder holder, int position) {

        SalonListModel salonListModel = arrayList.get(position);

        holder.salonName.setText(salonListModel.getName());
        holder.ratingBar.setRating(3);
        holder.salonImage.setImageResource(R.drawable.ic_sample);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView salonName, phoneNo;
        private ImageView salonImage;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            salonName = itemView.findViewById(R.id.salonName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            salonImage = itemView.findViewById(R.id.salonImage);

        }
    }
}
