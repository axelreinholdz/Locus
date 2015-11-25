package Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.locus.app.R;
import com.locus.app.StringArrayAdapter;

import java.util.ArrayList;

import controller.UserManager;
import model.User;


public class FriendsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootVier = inflater.inflate(R.layout.friendsfragment, container, false);
        final FragmentManager fm = getFragmentManager();

        String email = getActivity().getIntent().getStringExtra("email");

        UserManager userManager = new UserManager();
        ArrayList<User> friendsList = userManager.getFriendsByUserEmail(email ,getActivity());

        String [] friendsArray = new String [friendsList.size()];

        int i = 0;

        for(User u : friendsList){
            friendsArray [i] = u.getName();
            i++;
        }

        ListView lv = (ListView) rootVier.findViewById(R.id.listView_friendsList);

        StringArrayAdapter ad = new StringArrayAdapter(friendsArray, getActivity());
        lv.setAdapter(ad);

        ImageButton addBtn = (ImageButton) rootVier.findViewById(R.id.imageButton_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.content_frame, new SearchFriendsFragment()).commit();
            }
        });

        return rootVier;
    }


}
