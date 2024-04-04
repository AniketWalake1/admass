package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_breakdown extends AppCompatActivity {

    Button back, submit, time, date, logout, history;
    TextView datetext, timetext, textView1, textView;
    EditText nature_of_problem, cause_of_bd, spares, hrstext;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_breakdown);

        submit = findViewById(R.id.submitbg);
        back = findViewById(R.id.loginb);
        time = findViewById(R.id.timeshowb);
        date = findViewById(R.id.dateb);
        timetext = findViewById(R.id.timeshow);
        datetext = findViewById(R.id.date);
        logout = findViewById(R.id.button7);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        textView = findViewById(R.id.user1);
        fStore = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();
        textView1 = findViewById(R.id.user2);
        history = findViewById(R.id.history);
        nature_of_problem = findViewById(R.id.userNameEditText);
        cause_of_bd = findViewById(R.id.entercausebd);
        spares = findViewById(R.id.enterspare);
        hrstext = findViewById(R.id.totalbdtime);

        DocumentReference documentReference = fStore.collection("users").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    textView1.setText(value.getString("fName"));
                }
            }
        });

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity_breakdown.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                openDialog1();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                processInsert();
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity_breakdown.this, MainActivity6.class);
                startActivity(i1);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_breakdown.this, ViewRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(MainActivity_breakdown.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datetext.setText(String.valueOf(dayOfMonth) + "." + String.valueOf(month + 1) + "." + String.valueOf(year));
            }
        }, 2024, 2, 1); // Month is zero-based, so 2 is March

        dialog.show();
    }

    private void openDialog1() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timetext.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        }, 15, 0, true);
        dialog.show();
    }

    private void processInsert() {
        Map<String, Object> map = new HashMap<>();
        map.put("a1", user.getUid()); // Use UID as a more secure identifier
        map.put("a2", cause_of_bd.getText().toString());
        map.put("a3", spares.getText().toString());
        map.put("a4", timetext.getText().toString());
        map.put("a5", textView1.getText().toString());
        map.put("a6", hrstext.getText().toString()); // Fixed the typo in the key
        map.put("a7", datetext.getText().toString());
        map.put("a8", nature_of_problem.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Breakdown").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();

                        // Move the logout code here
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(MainActivity_breakdown.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}