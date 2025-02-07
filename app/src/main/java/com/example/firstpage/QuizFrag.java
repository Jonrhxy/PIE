package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class QuizFrag extends Fragment {

    private Button btnstartqz1; // Declare button

    public QuizFrag() {
        // Required empty public constructor
    }

    public static QuizFrag newInstance(String param1, String param2) {
        QuizFrag fragment = new QuizFrag();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Initialize the button
        btnstartqz1 = view.findViewById(R.id.btnstartqz1);

        // Navigate to next activity on button click
        btnstartqz1.setOnClickListener(v -> navigateToNext());

        return view; // Return the view after initializing components
    }

    private void navigateToNext() {
        Intent intent = new Intent(getActivity(), Quiz1of1.class);
        startActivity(intent);
    }
}
