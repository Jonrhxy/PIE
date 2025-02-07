package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Question4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question4);

        // Find the RadioGroup
        RadioGroup radioGroup = findViewById(R.id.radioGroupQ1);

        // Set listener for RadioGroup selection
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) { // Ensure a valid selection
                Intent intent = new Intent(Question4.this,Question5.class);
                startActivity(intent);
            }
        });
    }
}