package com.example.e_voting_samadhan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    ArrayList<model> datalist;
    ArrayList<String> votedUserList;
    ArrayList<String> docID;
    myAdapter myAdapter;
    ShimmerFrameLayout layout;
   // TextView t1;

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
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        layout = view.findViewById(R.id.shimmer);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        datalist = new ArrayList<model>();
        votedUserList = new ArrayList<String>();
        docID = new ArrayList<String>();
        myAdapter = new myAdapter(datalist,votedUserList,docID,getActivity());
        recyclerView.setAdapter(myAdapter);
        //t1 = view.findViewById(R.id.t1);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //<---------------------------------------------------------------------->
        String uid="",temp;
        temp=firebaseUser.getUid();
        for (int i=0;i<8;i++)
        {
            char c = temp.charAt(i);
            uid =uid+String.valueOf(c);
        }
        String finaluid=uid;
        // for adding data from firestore


        layout.startShimmer();

        db.collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();


                        for(DocumentSnapshot dc:documentSnapshots)
                        {
                            /// cheking for eligible for vote or not


                            if (dc.contains(finaluid))
                            {

                                db.collection("users")
                                        .document(dc.getId())
                                        .collection("overview")
                                        .document("document")
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                    {
                                         model obj = documentSnapshot.toObject(model.class);
                                         datalist.add(obj);
                                         docID.add(dc.getId());
                                         votedUserList.add(dc.getString(finaluid));
                                         myAdapter.notifyDataSetChanged();

                                    }
                                });

                            }
                            //myAdapter.notifyDataSetChanged();

                        }
                        layout.stopShimmer();
                        layout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        myAdapter.notifyDataSetChanged();



                    }

                });


        return view;

    }
   
}