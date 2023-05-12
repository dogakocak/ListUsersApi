package com.example.listusersapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private TextView warningMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.numberInput);
        warningMsg = findViewById(R.id.warningText);

    }

    public void listUserBtn(View view){
        String inputText = input.getText().toString();
        Log.d("EditText Log:",inputText);

        if (inputText.equals("1") || inputText.equals("2")){
            Intent intent = new Intent(this, ListUsers.class);
            intent.putExtra("pageNumber", inputText);
            startActivity(intent);
        }else{
            warningMsg.setText("You have to enter 1 or 2");
        }



    }
}