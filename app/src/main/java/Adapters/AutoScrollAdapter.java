package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.naiifi.R;
import com.google.android.material.textview.MaterialTextView;
import java.util.ArrayList;
import Models.SalonAdModel;



public class AutoScrollAdapter extends RecyclerView.Adapter<AutoScrollAdapter.ViewHolder> {

    private ArrayList<SalonAdModel> arrayList = new ArrayList<>();
    private Context mContext ;

    public AutoScrollAdapter(ArrayList<SalonAdModel> arrayList, Context mContext){
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    public AutoScrollAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salon_ad_layout, parent, false);
        return new AutoScrollAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoScrollAdapter.ViewHolder holder, int position) {



        SalonAdModel salonAdModel = arrayList.get(position%arrayList.size());

        holder.name.setText(salonAdModel.getSalonName());
        holder.discount.setText(salonAdModel.getDiscount());
        holder.salonImage.setImageResource(R.drawable.dd);


    }

    @Override
    public int getItemCount() {
        //return arrayList.size();

        return arrayList == null?0:Integer.MAX_VALUE;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView name, discount;
        private ImageView salonImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            discount = itemView.findViewById(R.id.discount_per);
            salonImage = itemView.findViewById(R.id.salonImage);

        }
    }


}


