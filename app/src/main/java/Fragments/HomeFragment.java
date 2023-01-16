package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.example.naiifi.R;
import com.example.naiifi.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import Adapters.AutoScrollAdapter;
import Adapters.SalonListAdapter;
import Codes.FetchSalons;
import Models.SalonAdModel;
import Models.SalonListModel;


public class HomeFragment extends Fragment {

    FragmentHomeBinding fragmentHomeBinding;
    private AutoScrollAdapter autoScrollAdapter;
    private SalonListAdapter salonListAdapter;

    private ArrayList<SalonAdModel> salonAdList = new ArrayList<>();
    private ArrayList<SalonListModel> salonList = new ArrayList<>();

    Timer timer;
    TimerTask timerTask;
    int position;
    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManagerList;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = fragmentHomeBinding.getRoot();

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fragmentHomeBinding.adRecyclerView.setLayoutManager(layoutManager);
        autoScrollAdapter = new AutoScrollAdapter(salonAdList, getContext());
        fragmentHomeBinding.adRecyclerView.setAdapter(autoScrollAdapter);

        if (salonAdList != null) {
            position = Integer.MAX_VALUE / 2;
            fragmentHomeBinding.adRecyclerView.scrollToPosition(position);
        }

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(fragmentHomeBinding.adRecyclerView);
        fragmentHomeBinding.adRecyclerView.smoothScrollBy(5, 0);

        fragmentHomeBinding.adRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollBanner();
                } else if (newState == 0) {
                    position = layoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollBanner();
                }
            }
        });


        salonAdList.add(new SalonAdModel("ABCD", "25%", "xyz"));
        salonAdList.add(new SalonAdModel("QWER", "75%", "xyz"));
        salonAdList.add(new SalonAdModel("DFGH", "33%", "xyz"));
        salonAdList.add(new SalonAdModel("VCXD", "78%", "xyz"));
        salonAdList.add(new SalonAdModel("BGRF", "42%", "xyz"));
        salonAdList.add(new SalonAdModel("WSDE", "50%", "xyz"));
        autoScrollAdapter.notifyDataSetChanged();



        FetchSalons fetchSalons = new FetchSalons();
        fetchSalons.fetchAll();



        layoutManagerList = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fragmentHomeBinding.recyclerViewList.setLayoutManager(layoutManagerList);
        salonListAdapter = new SalonListAdapter(salonList, getContext());
        fragmentHomeBinding.recyclerViewList.setAdapter(salonListAdapter);












        return view;

    }

    private void stopAutoScrollBanner() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = layoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void runAutoScrollBanner() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position == Integer.MAX_VALUE) {
                        position = Integer.MAX_VALUE / 2;
                        fragmentHomeBinding.adRecyclerView.scrollToPosition(position);
                        fragmentHomeBinding.adRecyclerView.smoothScrollBy(5, 0);
                    } else {
                        position++;
                        fragmentHomeBinding.adRecyclerView.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 8000, 2500);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        runAutoScrollBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScrollBanner();
    }
}