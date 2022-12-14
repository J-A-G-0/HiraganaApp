package com.example.hiragana_homepage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment used in Lesson to give users well done.
 */
public class WellDoneFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Lesson_Activity lesson_activity;

    public WellDoneFragment() {
        // Required empty public constructor
    }


    public static WellDoneFragment newInstance(String param1, String param2) {
        WellDoneFragment fragment = new WellDoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_well_done, container, false);

        lesson_activity = (Lesson_Activity) getActivity();
        // Re-initialise up here, so =  new Hiragana that have just been studied are added to SP().
        lesson_activity.getSharedPreferencesHandler().setUpSp();

        TextView tv_current_num_studied = (TextView) view.findViewById(R.id.tv_current_num_studied);

        // Populate the page to show the user their progress, relative to a goal if they've set one.
        checkIfAt46(tv_current_num_studied);


        return view;
    }

    public void checkIfAt46(TextView tv_num_studied){
        int current_num_studied = lesson_activity.getNumberOfHiraganaStudied();
        if(current_num_studied == 46) {
            setCompletionMessageText(tv_num_studied);
            //Check if deadlineSet is true.

            if(lesson_activity.getSharedPreferencesHandler().getSp()
                    .getBoolean("deadlineSet", false)){
                // Set the special congrats for meeting their goal.
                tv_num_studied.setText(getResources().getString(R.string.doneByDeadline));
                }
        } else {
            tv_num_studied.setText
                    ("You've now studied \n" + lesson_activity.getNumberOfHiraganaStudied()
                            + " / 46 Hiragana! \n\n Keep it up!");
        }
    }


    public void setCompletionMessageText(TextView tv){
        tv.setText(getResources().getString(R.string.what_next));
    }

}
