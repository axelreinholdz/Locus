package Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.locus.app.R;

import controller.GameRegistrationManager;
import controller.QuestionManager;
import model.GameRegistration;
import model.Question;

/**
 * Created by Melker on 2015-11-16.
 */
public class AnswerFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootVier = inflater.inflate(R.layout.answer_page, container, false);
        final FragmentManager fm = getFragmentManager();
        String grNumber = getArguments().getString("GrNumber");

        final TextView textViewFunFact = (TextView) rootVier.findViewById(R.id.textView_funFact);
        final TextView textViewInstruction = (TextView) rootVier.findViewById(R.id.textView_instruction);
        final TextView textViewLocation = (TextView) rootVier.findViewById(R.id.textView_location);
        ImageView questionImageView = (ImageView) rootVier.findViewById(R.id.imageView_questionPicture) ;

        GameRegistrationManager gm = new GameRegistrationManager();
        final GameRegistration gr = gm.getGameRegistrationByObjectId(grNumber);


        QuestionManager qm = new QuestionManager();
        final Question q = qm.getQuestionByNumber(gr.getCurrentQuestionNo(), getActivity());

        questionImageView.setImageBitmap(qm.loadImage(q.getQuestionPic()));

        textViewFunFact.setText(q.getHint1());

        textViewLocation.setText("Welcome to, "+q.getAnswerText());

        textViewInstruction.setText("Go to "+q.getAnswerText());

        ImageButton nextbtn = (ImageButton) rootVier.findViewById(R.id.nextqBtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr=new LocationFragment();
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
