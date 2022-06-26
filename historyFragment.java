package com.example.e_voting_samadhan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link historyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class historyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public historyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment historyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static historyFragment newInstance(String param1, String param2) {
        historyFragment fragment = new historyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView historyrecyclerview;
    ArrayList<model> datalist2;
    ArrayList<String> docIDlist;
    ArrayList<String> Leading;
    historyAdapter adapter;



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

        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        historyrecyclerview = view.findViewById(R.id.historyrecyclerview);
        historyrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        datalist2 = new ArrayList<model>();
        docIDlist = new ArrayList<String>();
        Leading = new ArrayList<String>();
        adapter = new historyAdapter(datalist2,getActivity(),docIDlist);
        historyrecyclerview.setAdapter(adapter);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String uid="",temp;
        temp=user.getUid();
        for (int i=0;i<8;i++)
        {
            char c = temp.charAt(i);
            uid=uid+String.valueOf(c);
        }

        String finalUid = uid;
        db.collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> ds = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot eachdocument:ds)
                        {
                            if (eachdocument.getId().contains(finalUid))
                            {

                                db.collection("users")
                                        .document(eachdocument.getId())
                                        .collection("overview")
                                        .document("document")
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot)
                                            {
                                               model model = documentSnapshot.toObject(model.class);
                                               datalist2.add(model);
                                               docIDlist.add(eachdocument.getId());
                                               adapter.notifyDataSetChanged();
                                            }

                                        });

                           }
                        }
                        adapter.notifyDataSetChanged();
                    }

                });


        return  view;
    }
}