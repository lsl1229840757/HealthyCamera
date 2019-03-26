package com.lsl.healthycamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lsl.healthycamera.util.Dish;

import org.json.JSONException;
import org.json.JSONObject;

public class thirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        TextView textView = findViewById(R.id.textView8);
        try {
            JSONObject json = new JSONObject(Dish.dish("C:\\Users\\dell\\Desktop\\b.jpg"));
            textView.setText(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
