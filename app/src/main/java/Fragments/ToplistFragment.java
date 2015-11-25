package Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.locus.app.R;
import com.locus.app.StringArrayAdapter;

import java.util.ArrayList;

import controller.GameRegistrationManager;
import model.GameRegistration;

/**
 * Created by Melker on 2015-11-02.
 */
public class ToplistFragment extends Fragment {

    private ArrayList<String> myData;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootVier = inflater.inflate(R.layout.toplistfragment, container, false);

        GameRegistrationManager gameRegistrationManager = new GameRegistrationManager();

        ArrayList<GameRegistration> topListTest = gameRegistrationManager.getAllGameRegistrationSortedByShortestDuration(getActivity());

        String [] topListArray = new String [topListTest.size()];

        int i = 0;

        for(GameRegistration gr: topListTest){
            topListArray [i] = gr.getUserId();
            i++;
        }

        ListView lv = (ListView) rootVier.findViewById(R.id.listView_topList);

        StringArrayAdapter ad = new StringArrayAdapter(topListArray, getActivity());
        lv.setAdapter(ad);

        return rootVier;



    }

}
