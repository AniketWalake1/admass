package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRecordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecordAdapter recordAdapter;
    private List<RecordModel> recordList;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordList = new ArrayList<>();
        recordAdapter = new RecordAdapter(recordList);
        recyclerView.setAdapter(recordAdapter);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            // User is not authenticated, handle accordingly
            // For example, redirect to login screen
        } else {
            // User is authenticated, proceed to fetch records
            fetchRecordsFromDatabase();
        }
    }

    private void fetchRecordsFromDatabase() {
        // Adjust the reference path based on your database structure
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Breakdown");

        // Add a listener to fetch data from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recordList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Map the data to your RecordModel class
                    RecordModel record = snapshot.getValue(RecordModel.class);
                    if (record != null) {
                        recordList.add(record);
                    }
                }
                recordAdapter.notifyDataSetChanged(); // Notify the adapter of the data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ViewRecordActivity", "Failed to read value.", error.toException());
            }
        });
    }
}