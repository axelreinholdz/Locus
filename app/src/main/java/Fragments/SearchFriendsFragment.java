package Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.locus.app.R;
import com.locus.app.StringArrayAdapter;

import java.util.ArrayList;

import controller.FriendsManager;
import controller.UserManager;
import model.User;

/**
 * Created by axelreinholdz on 2015-11-18.
 */
public class SearchFriendsFragment extends Fragment{

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootVier = inflater.inflate(R.layout.searchfriendfragment, container, false);
        final FragmentManager fm = getFragmentManager();

        final String email = getActivity().getIntent().getStringExtra("email");

        final UserManager userManager = new UserManager();
        final ArrayList<User> userList = userManager.getAllUsers(getActivity());

        final FriendsManager friendsManager = new FriendsManager();

        String [] usersArray = new String [userList.size()];

        int i = 0;

        for(User u : userList){
            usersArray [i] = u.getEmail();
            i++;
        }

        final ListView lv = (ListView) rootVier.findViewById(R.id.listView_usersList);

        final EditText editTextAdd = (EditText) rootVier.findViewById(R.id.EditText_userEmail);

        StringArrayAdapter ad = new StringArrayAdapter(usersArray, getActivity());
        lv.setAdapter(ad);



        //add button add friend

        ImageButton addBtn = (ImageButton) rootVier.findViewById(R.id.imageButton_addFriend);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String friendEmail = editTextAdd.getText().toString();

                User checkUser = userManager.getUserByEmail(friendEmail,getActivity());

                if(checkUser.getName()!= null){
                    friendsManager.addFriends(email,friendEmail);
                    Toast.makeText(getActivity(), userManager.getUserByEmail(friendEmail,getActivity()).getName() +" added as friend", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "No user found", Toast.LENGTH_SHORT).show();
                }



            }
        });








        return rootVier;
    }
}
