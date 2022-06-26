package com.example.e_voting_samadhan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.holder>{

    ArrayList<model> data;
    ArrayList<String> voteUserValue;
    ArrayList<String> docID;
    Context context;

    public myAdapter(ArrayList<model> data,ArrayList<String> voteUserValue,ArrayList<String> docID,Context context) {
        this.data = data;
        this.voteUserValue = voteUserValue;
        this.docID = docID;
        this.context = context;

    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eachvote,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position)
    {

        final String id = docID.get(position);
        holder.electionname.setText(": "+data.get(position).getElection_name());
        holder.winner.setText(": "+data.get(position).getWinner());
        holder.organizer.setText(": "+data.get(position).getOrganizer());

        //-----------------------------------------------------
        if (!data.get(position).getWinner().equals(""))
        {
            holder.vote.setText("vote terminated");
            holder.vote.setEnabled(false);
        }
        else if (voteUserValue.get(position).equals("true")) {
            holder.vote.setText("VOTED");
            holder.vote.setEnabled(false);
        }

        else
        {
            holder.vote.setVisibility(View.VISIBLE);
        }
        //----------------------------------
        // clickable vote button
        holder.vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,voting_layout.class);
                intent.putExtra("docID",id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        TextView organizer,winner,electionname;
        Button vote;

        public holder(@NonNull View itemView) {
            super(itemView);

            organizer = itemView.findViewById(R.id.organizer);
            winner = itemView.findViewById(R.id.winner);
            electionname = itemView.findViewById(R.id.electionname);
            vote = itemView.findViewById(R.id.vote);


        }
    }



}
