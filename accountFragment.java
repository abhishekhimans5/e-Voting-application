package com.example.e_voting_samadhan;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public accountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static accountFragment newInstance(String param1, String param2) {
        accountFragment fragment = new accountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    TextView name,email,uid,voterid;
    Button logout;

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
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        name = view.findViewById(R.id.account_name);
        voterid = view.findViewById(R.id.account_voterID);
        uid = view.findViewById(R.id.account_uid);
        email = view.findViewById(R.id.account_email);
        logout = view.findViewById(R.id.logout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        name.setText(user.getDisplayName());
        email.setText(user.getEmail());
        uid.setText(user.getUid());

        String uid="",temp;
        temp=user.getUid();
        for (int i=0;i<8;i++)
        {
            char c = temp.charAt(i);
            uid=uid+String.valueOf(c);
        }
        String finaluid=uid;
        voterid.setText(finaluid);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                try {
                    // clearing app data
                    String packageName = getContext().getPackageName();
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("pm clear "+packageName);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



        return view;
    }
}