package com.example.grampanchayat_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView phone, test;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    char netLine[];
    String nextLine;

    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db1;
//    ProgressDialog progressDialog = new ProgressDialog(this);
    String userId;
//    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Fetching Data:");
//        progressDialog.show();

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Select, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //test = findViewById(R.id.textView15);

        phone = findViewById(R.id.phnNum);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db1 = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<User>();
        myAdapter = new MyAdapter(MainActivity.this,userArrayList);

        recyclerView.setAdapter(myAdapter);


        EventChangeListener();

//        DocumentReference documentReference = fStore.collection("Notebook").document("10");
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
////                nextLine = value.getString("Title");
////                phone.setText(value.getString("Title"));
////                test.setText("James\n Vam\n Sam");
//
//
////                nextLine = value.getString("Title");
////                nextLine = nextLine.replace("~","\n * ");
////                nextLine = nextLine.substring(4);
////
////                phone.setText(nextLine);
//                if(value.exists()) {
//                    //phone.setText(value.getString("Title"));
//                    phone.setText(userId);
//                } else  {
//                    Log.d("tag","onEvent:Document do not exists");
//                }
////                netLine = value.getString("Title").toCharArray();
////                phone.setText(netLine.toString());
//            }
//        });
//        final String TAG = "MainActivity";
//        ArrayList<String> ar = new ArrayList<>();
//        db.collection("Notebook")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                ar.add(String.valueOf(document.getData()));
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//        System.out.println("Array");
//        System.out.println(ar.toString());
//        phone.setText(ar.toString());

//        final String TAG = "MainActivity";
//        DocumentReference docRef = fStore.collection("Notebook").document("HI");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

    }

    private void EventChangeListener(){
        System.out.println("Function Called");
        try{
            db1.collection("Sentences")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){

//                                if(progressDialog.isShowing())
//                                    progressDialog.dismiss();

                                Log.e("FireStore Error", error.getMessage());
                                return;
                            }

                            for(DocumentChange dc : value.getDocumentChanges()){
                                if(dc.getType() == DocumentChange.Type.ADDED){

                                    userArrayList.add(dc.getDocument().toObject(User.class));


                                }
//                                if(progressDialog.isShowing())
//                                    progressDialog.dismiss();
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}