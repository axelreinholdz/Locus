package Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.locus.app.R;

import controller.GameRegistrationManager;
import controller.UserManager;
import model.GameRegistration;
import model.User;

/**
 * Created by Melker on 2015-11-01.
 */
public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootVier = inflater.inflate(R.layout.start_game, container, false);
        final FragmentManager fm = getFragmentManager();
        String email = getActivity().getIntent().getStringExtra("email");

        UserManager userManager = new UserManager();
        final User u  = userManager.getUserByEmail(email,getActivity());

        ImageButton startBtn = (ImageButton) rootVier.findViewById(R.id.newGame_button);

        final GameRegistrationManager gameRegistrationManager = new GameRegistrationManager();

        //if user already have unfinished game continue button visible true

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameRegistration gr = gameRegistrationManager.createGameRegistration(u.getUserId());

                Fragment fr=new QuestionFragment();
                android.app.FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("GrNumber", gr.getObjectId());
                fr.setArguments(args);
                ft.replace(R.id.content_frame, fr);
                ft.commit();
            }
        });

        return rootVier;
    }
}
