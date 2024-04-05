package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivityin extends AppCompatActivity {

    private Button comp, logout, submitRecord;
    private FirebaseUser user;
    private TextView textView, textView1, shift, time;
    private FirebaseFirestore fStore;
    private String userId;

    // EditText references for data collection
    private EditText e1, e3, e4, e5, e6, e13, e14, e15, e16, e17, e18, e20, e21, e22, e26;

    // CheckBox references for Yes/No data
    private CheckBox e2, e7, e8, e9, e10, e11, e12, e19, e23, e24, e25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_activityin);

        // Initialize button, text view, and checkbox references
        comp = findViewById(R.id.b2);
        logout = findViewById(R.id.button7);
        textView = findViewById(R.id.user1);
        textView1 = findViewById(R.id.user2);
        shift = findViewById(R.id.shiftshow);
        time = findViewById(R.id.time1);
        submitRecord = findViewById(R.id.SubmitRecord); // Assuming submit record button ID

        // Initialize edit text references
        e1 = findViewById(R.id.e1);
        e3 = findViewById(R.id.e3);
        e4 = findViewById(R.id.e4);
        e5 = findViewById(R.id.e5);
        e6 = findViewById(R.id.e6);
        e13 = findViewById(R.id.e13);
        e14 = findViewById(R.id.e14);
        e15 = findViewById(R.id.e15);
        e16 = findViewById(R.id.e16);
        e17 = findViewById(R.id.e17);
        e18 = findViewById(R.id.e18);
        e20 = findViewById(R.id.e20);
        e21 = findViewById(R.id.e21);
        e22 = findViewById(R.id.e22);
        e26 = findViewById(R.id.e26);

        // Initialize check box references
        e2 = findViewById(R.id.e2); // Replace with actual IDs for your check boxes
        e7 = findViewById(R.id.e7);
        e8 = findViewById(R.id.e8);
        e9 = findViewById(R.id.e9);
        e10 = findViewById(R.id.e10);
        e11 = findViewById(R.id.e11);
        e12 = findViewById(R.id.e12);
        e19 = findViewById(R.id.e19);
        e23 = findViewById(R.id.e23);
        e24 = findViewById(R.id.e24);
        e25 = findViewById(R.id.e25);



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

        submitRecord.setOnClickListener(v -> {
            saveReportToFirestore();
            clearFields(); // Call clearFields method after successful save
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
    private void saveReportToFirestore() {
        // Create a Map to store report data
        Map<String, Object> reportData = new HashMap<>();

        // Get values from EditTexts and format checkboxes
        String e1Value = e1.getText().toString().trim(); // Assuming e1 is the first EditText
        String e3Value = e3.getText().toString().trim();
        String e4Value = e4.getText().toString().trim();
        String e5Value = e5.getText().toString().trim();
        String e6Value = e6.getText().toString().trim();
        String e13Value = e13.getText().toString().trim();
        String e14Value = e14.getText().toString().trim();
        String e15Value = e15.getText().toString().trim();
        String e16Value = e16.getText().toString().trim();
        String e17Value = e17.getText().toString().trim();
        String e18Value = e18.getText().toString().trim();
        String e20Value = e20.getText().toString().trim();
        String e21Value = e21.getText().toString().trim();
        String e22Value = e22.getText().toString().trim();
        String e26Value = e26.getText().toString().trim();



        boolean e2Checked = e2.isChecked();
        boolean e7Checked = e7.isChecked();
        boolean e8Checked = e8.isChecked();
        boolean e9Checked = e9.isChecked();
        boolean e10Checked = e10.isChecked();
        boolean e11Checked = e11.isChecked();
        boolean e12Checked = e12.isChecked();
        boolean e19Checked = e19.isChecked();
        boolean e23Checked = e23.isChecked();
        boolean e24Checked = e24.isChecked();
        boolean e25Checked = e25.isChecked();



        // Add data to the report map
        reportData.put("e1", e1Value);
        reportData.put("e2", e2Checked ? "Yes" : "No");
        reportData.put("e3", e3Value);
        reportData.put("e4", e4Value);
        reportData.put("e5", e5Value);
        reportData.put("e6", e6Value);
        reportData.put("e7", e7Checked ? "Yes" : "No");
        reportData.put("e8", e8Checked ? "Yes" : "No");
        reportData.put("e9", e9Checked ? "Yes" : "No");
        reportData.put("e10", e10Checked ? "Yes" : "No");
        reportData.put("e11", e11Checked ? "Yes" : "No");
        reportData.put("e12", e12Checked ? "Yes" : "No");
        reportData.put("e13", e13Value);
        reportData.put("e14", e14Value);
        reportData.put("e15", e15Value);
        reportData.put("e16", e16Value);
        reportData.put("e17", e17Value);
        reportData.put("e18", e18Value);
        reportData.put("e19", e19Checked ? "Yes" : "No");
        reportData.put("e20", e20Value);
        reportData.put("e21", e21Value);
        reportData.put("e22", e22Value);
        reportData.put("e23", e23Checked ? "Yes" : "No");
        reportData.put("e24", e24Checked ? "Yes" : "No");
        reportData.put("e25", e25Checked ? "Yes" : "No");
        reportData.put("e26", e26Value);


        // Get a reference to the "InProcessReport" collection
        CollectionReference reportsCollection = fStore.collection("InProcessReport");

        // Add the report data to the collection
        reportsCollection.add(reportData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MainActivityin.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivityin.this, "Failed to submit report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        e1.setText("");
        e3.setText("");
        e4.setText("");
        e5.setText("");
        e6.setText("");
        e13.setText("");
        e14.setText("");
        e15.setText("");
        e16.setText("");
        e17.setText("");
        e18.setText("");
        e20.setText("");
        e21.setText("");
        e22.setText("");
        e26.setText("");



        // Uncheck all checkboxes
        e2.setChecked(false);
        e7.setChecked(false);
        e8.setChecked(false);
        e9.setChecked(false);
        e10.setChecked(false);
        e11.setChecked(false);
        e12.setChecked(false);
        e19.setChecked(false);
        e23.setChecked(false);
        e24.setChecked(false);
        e25.setChecked(false);

    }
}

