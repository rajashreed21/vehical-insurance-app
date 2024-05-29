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


public class vehicaldetails extends AppCompatActivity {

    private static final String TAG="vehicaldetails";

    private EditText model,year,vehicalno;

    private Button addButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehical_details);

        model = findViewById(R.id.TextModel);
        year = findViewById(R.id.TextYear);
        vehicalno = findViewById(R.id.vehical_no);
        addButton = findViewById(R.id.Save_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddetails();
            }
        });
    }
    private void adddetails() {

        String Model= model.getText().toString();

        String Year = year.getText().toString();

        String vehinum = vehicalno.getText().toString();

        JSONObject userData = new JSONObject();

        try {

            userData.put("keymodel", Model);

            userData.put("keymodel", Year);

            userData.put("keyvehicalnumber", vehinum);


        } catch (JSONException e) {

            e.printStackTrace();

        }

        // Send POST request to API endpoint

        new vehicalAsyncTask().execute(userData.toString());

    }

    private class vehicalAsyncTask extends AsyncTask<String, Void, String> {

        @SuppressLint("RestrictedApi")
        @Override

        protected String doInBackground(String... params) {

            String jsonData = params[0];

            HttpURLConnection connection = null;

            StringBuilder response = new StringBuilder();

            try {

                URL url = new URL("https://demoproj-1.onrender.com/vehical");

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

                Toast.makeText(vehicaldetails.this, "Details added successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(vehicaldetails.this, claimfilling.class);

                startActivity(intent);

            } else {

                Toast.makeText(vehicaldetails.this, "Details failed to add", Toast.LENGTH_SHORT).show();

            }

            Intent intent = new Intent(vehicaldetails.this, claimfilling.class);

            startActivity(intent);

            finish();

        }

    }

}


