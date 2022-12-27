package Codes;


import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import Models.SalonListModel;


public class FetchSalons {

    private FirebaseAuth firebaseAuth ;
    private DatabaseReference databaseReference ;
    private FirebaseFirestore firebaseFirestore ;
    private FirebaseStorage firebaseStorage ;

    private SalonListModel salonListModel;


    public ArrayList<SalonListModel> fetchAll(){

        ArrayList<SalonListModel> salonList = new ArrayList<SalonListModel>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Salons");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot child : snapshot.getChildren()){

                    String key = child.getKey().toString();
                    Log.d("iterator", "onDataChange: "+key);

                    SalonListModel salonListModel = child.getValue(SalonListModel.class);
                    //Log.d("modelTest", "onDataChange: "+salonListModel.getAnInt());

                    salonList.add(salonListModel);


                    Log.d("modelTest", "onDataChange: "+salonList.get(0).getName());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Log.d("modelTest", "fetchAll: "+salonList.get(0).getName());
        Log.d("array", "fetchAll: "+salonList.size());

        return salonList;

    }

    public void fetchWithFilter(String filter){

    }

    public void fetchWithMultipleFilters(String[] filterArr){

    }

}
