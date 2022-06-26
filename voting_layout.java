package com.example.e_voting_samadhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class voting_layout extends AppCompatActivity {

    RecyclerView evm_recycler;
    ArrayList<String> candidates;
    ArrayList<String> documents;
    String docID;
    ArrayList<String> eachvote;
    evmAdapter adapter;
    TextView topicname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_layout);

        docID = getIntent().getStringExtra("docID");

        topicname = findViewById(R.id.topic_name);
        candidates = new ArrayList<String>();
        documents = new ArrayList<String>();
        eachvote = new ArrayList<String>();
        evm_recycler = findViewById(R.id.evm_recycler);
        adapter = new evmAdapter(candidates,this,documents,docID,eachvote);
        evm_recycler.setAdapter(adapter);
        evm_recycler.setLayoutManager(new LinearLayoutManager(voting_layout.this,LinearLayoutManager.VERTICAL,false));

        FirebaseFirestore db = FirebaseFirestore.getInstance();



            db.collection("users").document(docID)
                    .collection("options_candidate_name")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                        {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d:list)
                            {
                                candidates.add(d.getString("name"));
                                documents.add(d.getId());
                                String v = d.getString("vote_count");
                                eachvote.add(v);

                            }
                            adapter.notifyDataSetChanged();


                        }
                    });
            db.collection("users")
                    .document(docID)
                    .collection("topic_name")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                        {
                           List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
                           for (DocumentSnapshot ds:documentSnapshotList)
                           {
                               topicname.setText(ds.getString("topic_name"));
                           }
                        }
                    });





    }
}