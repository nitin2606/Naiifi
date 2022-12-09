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


public class FetchSalons {

    private FirebaseAuth firebaseAuth ;
    private DatabaseReference databaseReference ;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;

    public void fetchAll(){

        databaseReference = FirebaseDatabase.getInstance().getReference("Salons");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value = snapshot.getValue().toString();
                try{
                    String[] data = value.split("");
                    Log.d("arrayData", "onDataChange: "+ data[0]);

                }
                catch (Exception e){
                    Log.d("arrayExcep", "onDataChange: "+e.getMessage());
                }


                Log.d("newData", "onDataChange: "+value);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void fetchWithFilter(String filter){

    }

    public void fetchWithMultipleFilters(String[] filterArr){

    }

}
