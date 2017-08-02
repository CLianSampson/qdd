package com.lvgou.qdd.activity.contact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HaveContactFragment extends Fragment {

    private TextView nameTextView;

    private TextView accountTextview;

    private Button addButton;


    public HaveContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_have_contact, container, false);

        nameTextView = (TextView) view.findViewById(R.id.AddContactActivity_account);
        accountTextview = (TextView) view.findViewById(R.id.AddContactActivity_name);
        addButton = (Button) view.findViewById(R.id.AddContactActivity_add);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return  view;
    }

}
