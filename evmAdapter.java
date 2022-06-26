package com.example.e_voting_samadhan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class evmAdapter extends RecyclerView.Adapter<evmAdapter.viewholder> {

    ArrayList<String> candidates;
    ArrayList<String> documents;
    ArrayList<String> votecount;
    Context context;
    String docID;


    public evmAdapter(ArrayList<String> candidates,Context context,ArrayList<String> documents,String docID,ArrayList<String> votecount) {
        this.candidates = candidates;
        this.context = context;
        this.documents = documents;
        this.docID = docID;
        this.votecount = votecount;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.evm_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position)
    {
        int total=0;
        for (String x:votecount)
        {
            total += Integer.parseInt(x);
        }
        if (total==0)
            total=1;

        int percentage = Integer.parseInt(votecount.get(position))*100/total;

        holder.candidatename.setText(candidates.get(position));
        holder.percent_progress.setProgress(percentage);
        holder.percent.setText(String.valueOf(percentage));
        holder.percent.append("%");

        // dialog box when a vote will be submitted----------------------------->
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        holder.voteMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,submittedVote.class);
                intent.putExtra("primary_docID",docID);
                intent.putExtra("secondary_docID",documents.get(holder.getAdapterPosition()));
                intent.putExtra("name",candidates.get(holder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });
        //===============================================================================

    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView candidatename,voteMe,percent;
        ProgressBar percent_progress;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            candidatename=itemView.findViewById(R.id.candidate);
            voteMe = itemView.findViewById(R.id.voteMe);
            percent = itemView.findViewById(R.id.progress_percent);
            percent_progress = itemView.findViewById(R.id.progress_horizontal);
        }
    }
}

