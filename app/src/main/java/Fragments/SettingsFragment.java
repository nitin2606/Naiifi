package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naiifi.DashBoardActivity;
import com.example.naiifi.LoginActivity;
import com.example.naiifi.R;
import com.example.naiifi.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FragmentSettingsBinding fragmentSettingsBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentSettingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        View view = fragmentSettingsBinding.getRoot();


        firebaseAuth = FirebaseAuth.getInstance();
        fragmentSettingsBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                try {
                    SettingsFragment.this.finalize();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });



        return view;
    }
}