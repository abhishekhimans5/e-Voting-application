package com.example.e_voting_samadhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    TextView name;
    FirebaseAuth mAuth;
    BottomNavigationView bnv;
//    ArrayList<model> datalist;
//    ArrayList<String> votedUserList;
//    ArrayList<String> docID;
//    myAdapter myAdapter;
//    RecyclerView recyclerView;
//    LinearLayoutManager lm;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new homeFragment()).commit();

        name = findViewById(R.id.name);
        bnv = findViewById(R.id.bottom_navigation);
        mAuth = FirebaseAuth.getInstance();

        //-------------------------------------------------------------
//        datalist = new ArrayList<model>();
//        votedUserList = new ArrayList<String>();
//        docID = new ArrayList<String>();
//        myAdapter = new myAdapter(datalist,votedUserList,docID,getApplicationContext());
//        recyclerView = findViewById(R.id.recyclerview);
//        lm = new LinearLayoutManager(home.this,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(lm);
//        recyclerView.setAdapter(myAdapter);
        //--------------------------------------------------------

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(signInAccount != null)
        {
            uid=firebaseUser.getUid();
            name.setText(firebaseUser.getDisplayName());
        }

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment temp=null;
                switch (item.getItemId())
                {
                    case R.id.home_menu:
                        temp=new homeFragment();
                        break;
                    case R.id.history_menu:
                        temp=new historyFragment();
                        break;
                    case R.id.vote_menu:
                        temp=new createVoteFragment();
                        break;
                    case R.id.account_menu:
                        temp=new accountFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,temp).commit();
                return true;
            }
        });











        //----------------------------------------------------------------------------------



//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                clearAppData();
//                finish();
//
//            }
//        });

    }

//    private void clearAppData() {
//        try {
//            // clearing app data
//            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
//                ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
//            } else {
//                String packageName = getApplicationContext().getPackageName();
//                Runtime runtime = Runtime.getRuntime();
//                runtime.exec("pm clear "+packageName);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // fitting data to recycler view--------------------




}