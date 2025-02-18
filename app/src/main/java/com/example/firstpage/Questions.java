package com.example.firstpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

public class Questions extends AppCompatActivity {

    private ImageView ImageView;
    private TextView HelloText, MotivationalText, DescriptionText;
    private Button StartButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // SharedPreferences keys
    private static final String PREFS_NAME = "loginPrefs";
    private static final String PREF_IS_QUESTIONS_COMPLETED = "isQuestionsCompleted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // Initialize the UI elements
        ImageView = findViewById(R.id.imageView);
        HelloText = findViewById(R.id.hello);  // Update to use the correct TextView ID
        MotivationalText = findViewById(R.id.motivationalText);
        StartButton = findViewById(R.id.btnstart);

        // Initialize FirebaseAuth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get the current user's username (you should have stored this when they registered)
        String username = mAuth.getCurrentUser().getDisplayName(); // Username is stored in FirebaseUser displayName

        // Fetch the first name from Firestore using the username as document ID
        DocumentReference userDocRef = db.collection("users").document(username); // Use username as the document ID
        userDocRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get the first name from Firestore
                        String firstName = task.getResult().getString("firstName");
                        if (firstName != null) {
                            // Update the HelloText with the personalized greeting
                            HelloText.setText("Hello, " + firstName + "!");
                        }
                    } else {
                        // Handle any errors
                        Toast.makeText(Questions.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Check if the user has already completed the questions in Firestore
        checkIfQuestionsCompleted();

        // Set up a listener for the calculate button
        StartButton.setOnClickListener(v -> performCalculation());
    }

    // Check if the user has completed the questions from Firestore
    private void checkIfQuestionsCompleted() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String username = currentUser.getDisplayName();

            // Retrieve completion status from Firestore
            DocumentReference userDocRef = db.collection("users").document(username);
            userDocRef.get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Boolean isQuestionsCompleted = task.getResult().getBoolean("isQuestionsCompleted");
                            if (isQuestionsCompleted != null && isQuestionsCompleted) {
                                // Redirect to homepage if questions are completed
                                Intent intent = new Intent(Questions.this, HomeFragment.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }

    // After the user completes the questions, mark them as completed in Firestore
    private void performCalculation() {
        // Save the flag in SharedPreferences to indicate questions are completed
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_QUESTIONS_COMPLETED, true);
        editor.apply();

        // Mark as completed in Firestore
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String username = currentUser.getDisplayName();
            db.collection("users").document(username)
                    .update("isQuestionsCompleted", true)
                    .addOnSuccessListener(aVoid -> {
                        // Redirect to the Homepage activity
                        Intent intent = new Intent(Questions.this, Question2.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(Questions.this, "Failed to update completion status", Toast.LENGTH_SHORT).show());
        }
    }
}
