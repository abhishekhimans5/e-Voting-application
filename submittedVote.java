package com.example.e_voting_samadhan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class submittedVote extends AppCompatActivity {

    TextView username,confirm,done,primary,secondary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_vote);

        username = findViewById(R.id.username);
        confirm = findViewById(R.id.confirm);
        done = findViewById(R.id.done);
        primary = findViewById(R.id.primary);
        secondary = findViewById(R.id.secomdary);


        String primary_doc = getIntent().getStringExtra("primary_docID");
        String secondary_doc = getIntent().getStringExtra("secondary_docID");
        String name = getIntent().getStringExtra("name");


        username.setText("Voted To ---> "+name);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        primary.setText("Voted by : "+user.getDisplayName());
        secondary.setText(secondary_doc);

        //------------------------------------------------------//
        String uid="",temp;
        temp=user.getUid();
        for (int i=0;i<8;i++)
        {
            char c = temp.charAt(i);
            uid =uid+String.valueOf(c);
        }
        String finaluid=uid;

        //-----------------------------------------------------//


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("users")
                        .document(primary_doc)
                        .collection("options_candidate_name")
                        .document(secondary_doc)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot)
                            {
                               int number=6;
                               number = Integer.parseInt(documentSnapshot.getString("vote_count"))+1;


                                HashMap<String,Object> count = new HashMap<>();
                                count.put("vote_count",String.valueOf(number));
                                count.put("name",documentSnapshot.getString("name"));

                                db.collection("users")
                                        .document(primary_doc)
                                        .collection("options_candidate_name")
                                        .document(secondary_doc)
                                        .set(count)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {



                                            }
                                        });

                                HashMap<String,Object> uid = new HashMap<>();
                                uid.put(finaluid,"true");

                                db.collection("users")
                                        .document(primary_doc)
                                        .set(uid, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused)
                                            {
                                                Toast.makeText(submittedVote.this, "Confirmation Successfull", Toast.LENGTH_SHORT).show();
                                                done.setVisibility(View.VISIBLE);
                                                confirm.setVisibility(View.INVISIBLE);
                                            }
                                        });
                            }
                        });

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(submittedVote.this,home.class));
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please confirm first", Toast.LENGTH_SHORT).show();
    }
}