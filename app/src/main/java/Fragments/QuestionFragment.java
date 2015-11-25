package Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.locus.app.R;

import controller.GameRegistrationManager;
import controller.QuestionManager;
import model.GameRegistration;
import model.Question;

/**
 * Created by Melker on 2015-11-16.
 */
public class QuestionFragment extends Fragment{

     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootVier = inflater.inflate(R.layout.question_page
                , container, false);

        String grNumber = getArguments().getString("GrNumber");

        final FragmentManager fm = getFragmentManager();

        TextView questionTextView = (TextView) rootVier.findViewById(R.id.textView_question);
        final EditText answerEditText = (EditText) rootVier.findViewById(R.id.editText_answer);

         GameRegistrationManager gm = new GameRegistrationManager();

         final GameRegistration gr = gm.getGameRegistrationByObjectId(grNumber);


         QuestionManager qm = new QuestionManager();
         final Question q = qm.getQuestionByNumber(gr.getCurrentQuestionNo(), getActivity());

         questionTextView.setText(q.getQuestionText());


        ImageButton submitBtn = (ImageButton) rootVier.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String answer = answerEditText.getText().toString();

                if(answer.equalsIgnoreCase(q.getAnswerText())){
                    Fragment fr=new AnswerFragment();
                    android.app.FragmentTransaction ft=fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("GrNumber", gr.getObjectId());
                    fr.setArguments(args);
                    ft.replace(R.id.content_frame, fr);
                    ft.commit();
                }
                else{
                    Toast.makeText(getActivity(), "Wrong Answer", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return rootVier;


    }
}
