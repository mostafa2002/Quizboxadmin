package com.lamba.quizboxadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddQuiz extends Fragment {

    View view;

    FloatingActionButton create;
    EditText name;
    Spinner subject;
    String thesubject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addquiz,container,false);

        create = view.findViewById(R.id.floatingActionButton);
        name = view.findViewById(R.id.quizasm);
        subject = view.findViewById(R.id.subjects);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.subjects, R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(adapter);


        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] array = getResources().getStringArray(R.array.subjects);
                    thesubject = array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "please type the quiz name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    intent.putExtra("subject",thesubject);
                    intent.putExtra("quizname",name.getText().toString());
                    startActivity(intent);
                }
                

            }
        });


        return view;
    }
}
