package com.example.e_voting_samadhan;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.viewholder> {

    ArrayList<model> data;
    Context context;
    ArrayList<String> doclist;

    public historyAdapter(ArrayList<model> data,Context context,ArrayList<String> doclist) {
        this.data = data;
        this.context = context;
        this.doclist = doclist;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eachhistory,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(doclist.get(holder.getAdapterPosition()))
                .collection("options_candidate_name")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        ArrayList<String> leadlist_name = new ArrayList<String>();
                        ArrayList<Integer> leadlist_count = new ArrayList<Integer>();
                        leadlist_name.clear();
                        leadlist_count.clear();
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list)
                        {
                            leadlist_name.add(d.getString("name"));
                            leadlist_count.add(Integer.parseInt(d.getString("vote_count")));
                        }
                        int max = Collections.max(leadlist_count);
                        int index = leadlist_count.indexOf(max);
                        String leading = leadlist_name.get(index);
                        holder.t4.setText(": "+leading+" ("+max+" votes)");
                        holder.t1.setText(": "+data.get(holder.getAdapterPosition()).getElection_name());
                        holder.t2.setText(": "+data.get(holder.getAdapterPosition()).getOrganizer());
                        holder.t3.setText(": "+data.get(holder.getAdapterPosition()).getWinner());

                    }
                });





        //-------------------------------------------------------------
        // for add button

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dailog_history_management);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                TextView header = dialog.findViewById(R.id.header_historydialog);
                TextView cancel = dialog.findViewById(R.id.cancel_historydialog);
                TextView execute = dialog.findViewById(R.id.execute_historydialog);
                EditText input = dialog.findViewById(R.id.input_historydialog);

                header.setText("Add new Voter");
                input.setHint("Enter the 'Voter ID No.' Here");
                execute.setText("ADD");


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                execute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String user = input.getText().toString();
                        if(!user.equals(""))
                        {
                            HashMap<String,Object> vID = new HashMap<>();
                            vID.put(user,"false");

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            String doc = doclist.get(holder.getAdapterPosition());

                            db.collection("users")
                                    .document(doc)
                                    .set(vID, SetOptions.merge())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused)
                                        {
                                            Toast.makeText(dialog.getContext(), "Voter Added Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            dialog.dismiss();
                        }
                    }
                });


                dialog.show();

            }
        });
        //--------------------------------------------------------------------------
        // for delete button

        holder.detelepoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dailog_history_management);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                TextView header = dialog.findViewById(R.id.header_historydialog);
                TextView cancel = dialog.findViewById(R.id.cancel_historydialog);
                TextView execute = dialog.findViewById(R.id.execute_historydialog);
                EditText input = dialog.findViewById(R.id.input_historydialog);

                header.setText("Delete this poll ?");
                input.setVisibility(View.INVISIBLE);
                execute.setText("DELETE");

                FirebaseFirestore db = FirebaseFirestore.getInstance();


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                execute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String doc = doclist.get(holder.getAdapterPosition());

                        db.collection("users")
                                .document(doc).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused)
                                    {
                                        Toast.makeText(dialog.getContext(), "deleted successfully", Toast.LENGTH_SHORT).show();
                                    }

                                });
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        //==========================================================================
        //for announce button

        holder.announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dailog_history_management);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                TextView header = dialog.findViewById(R.id.header_historydialog);
                TextView cancel = dialog.findViewById(R.id.cancel_historydialog);
                TextView execute = dialog.findViewById(R.id.execute_historydialog);
                EditText input = dialog.findViewById(R.id.input_historydialog);

                header.setText("Announce the winner !");
                input.setHint("Enter the name of winner here !");
                execute.setText("Announce");


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                execute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                       String str = input.getText().toString();
                       if (!str.equals(""))
                       {
                           HashMap<String,Object> winner = new HashMap<>();
                           winner.put("winner",str);

                           db.collection("users")
                                   .document(doclist.get(holder.getAdapterPosition()))
                                   .collection("overview")
                                   .document("document")
                                   .set(winner,SetOptions.merge())
                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void unused)
                                       {
                                           Toast.makeText(dialog.getContext(), "Winner Declared", Toast.LENGTH_SHORT).show();
                                       }
                                   });

                       }
                       dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3,t4,add,announce,detelepoll;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.history_electionname);
            t2 = itemView.findViewById(R.id.history_organiser);
            t3 = itemView.findViewById(R.id.history_winner);
            t4 = itemView.findViewById(R.id.history_leading);
            add = itemView.findViewById(R.id.addvoter);
            announce = itemView.findViewById(R.id.announcewinner);
            detelepoll = itemView.findViewById(R.id.deletepoll);

        }
    }
}
