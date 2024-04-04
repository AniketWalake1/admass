package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class MainActivityin extends AppCompatActivity {

    Button comp, in, logout;
    private FirebaseUser user;
    private TextView textView, textView1, shift, time;
    private FirebaseFirestore fStore;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_activityin);
        comp = findViewById(R.id.b2);
        in = findViewById(R.id.b4);
        logout = findViewById(R.id.button7);
        textView = findViewById(R.id.user1);
        textView1 = findViewById(R.id.user2);
        shift = findViewById(R.id.shiftshow);
        time = findViewById(R.id.time1);
        setupFirebase();
        updateShiftBasedOnTime();
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivityin.this, MainActivity.class));
            finish();
        });

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comp.setBackgroundColor(getResources().getColor(R.color.blue));
                Intent intent1 = new Intent(MainActivityin.this, MainActivity5.class);
                startActivity(intent1);
            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in.setBackgroundColor(getResources().getColor(R.color.blue));
                //back to in page
                Intent intent2 = new Intent(MainActivityin.this, MainActivityin.class);
                startActivity(intent2);
            }
        });
    }
    private void setupFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (value, error) -> {
            if (value != null) {
                textView1.setText(value.getString("fName"));
            }
        });
    }
    private void updateShiftBasedOnTime() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 8 && hourOfDay < 16) {
            shift.setText("1st Shift");
        } else if (hourOfDay >= 16 && hourOfDay < 22) {
            shift.setText("2nd Shift");
        } else {
            shift.setText("3rd Shift");
        }
    }
}
