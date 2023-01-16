package Codes;


import android.os.AsyncTask;
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

    ArrayList<SalonListModel> salonList ;


    public void fetchAll(){


        new AsyncCaller().execute();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread

        }
        @Override
        protected Void doInBackground(Void... params) {

            salonList = new ArrayList<>();

            databaseReference = FirebaseDatabase.getInstance().getReference("Salons");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot child : snapshot.getChildren()){

                        String key = child.getKey().toString();
                        Log.d("iterator", "onDataChange: "+key);

                        SalonListModel salonListModel = child.getValue(SalonListModel.class);
                        salonList.add(salonListModel);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




            return null;

        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread

        }

    }




    public void fetchWithFilter(String filter){

    }

    public void fetchWithMultipleFilters(String[] filterArr){

    }

}
