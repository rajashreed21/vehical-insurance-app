package com.example.vehicalinsurance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class claimfilling extends AppCompatActivity {

    private static final String TAG="claimfilling";

    private EditText Name,Date,Number,Police,Place,Damage;

    private Button addButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_filling);

        Name = findViewById(R.id.holder_name);
        Date = findViewById(R.id.date);
        Number = findViewById(R.id.number);
        Police = findViewById(R.id.police);
        Place = findViewById(R.id.place);
        Damage = findViewById(R.id.damage);
        addButton = findViewById(R.id.submit_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                claimfilling1();
            }
        });
    }
    private void claimfilling1() {

        String name1= Name.getText().toString();
        String date1 = Date.getText().toString();
        String number1 = Number.getText().toString();
        String police1 = Police.getText().toString();
        String place1 = Place.getText().toString();
        String damage1 = Damage.getText().toString();

        JSONObject userData = new JSONObject();

        try {

            userData.put("holdername", name1);
            userData.put("date of acciendent", date1);
            userData.put("mobile number", number1);
            userData.put("police report", police1);
            userData.put("place of incident", place1);
            userData.put("damage occured", damage1);

        } catch (JSONException e) {

            e.printStackTrace();

        }

        // Send POST request to API endpoint

        new claimfillingAsyncTask().execute(userData.toString());

    }

    private class claimfillingAsyncTask extends AsyncTask<String, Void, String> {

        @SuppressLint("RestrictedApi")
        @Override

        protected String doInBackground(String... params) {

            String jsonData = params[0];

            HttpURLConnection connection = null;

            StringBuilder response = new StringBuilder();

            try {

                URL url = new URL("https://demoproj-1.onrender.com/Claim");

                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");

                connection.setRequestProperty("Content-Type", "application/json");

                connection.setDoOutput(true);

                BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());

                outputStream.write(jsonData.getBytes());

                outputStream.flush();

                outputStream.close();

                // Read response

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;

                while ((line = reader.readLine()) != null) {

                    response.append(line);

                }

                reader.close();

            } catch (IOException e) {

                Log.e(TAG, "Error sending POST request: " + e.getMessage());

            } finally {

                if (connection != null) {

                    connection.disconnect();

                }

            }

            return response.toString();

        }

        @Override

        protected void onPostExecute(String result) {

            if (!result.isEmpty()) {

                Toast.makeText(claimfilling.this, "calim form filled sucessfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(claimfilling.this, claimstatus.class);

                startActivity(intent);

            } else {

                Toast.makeText(claimfilling.this, "failed to fill the form", Toast.LENGTH_SHORT).show();

            }

            Intent intent = new Intent(claimfilling.this, claimstatus.class);

            startActivity(intent);

            finish();

        }

    }

}