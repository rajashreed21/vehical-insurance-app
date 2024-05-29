package com.example.vehicalinsurance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class claimstatus extends AppCompatActivity {

    private static final String TAG = "claimstatus";
    private Button addTaskBtn;
    private String API_URL = "https://demoproj-1.onrender.com/statusdata";
    private TextView statusdetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_status);



        statusdetails = findViewById(R.id.viewdetails);

        addTaskBtn = findViewById(R.id.home_btn);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(claimstatus.this, registeractivity.class);

                startActivity(intent);
            }
        });

        // Fetch data from API endpoint
        new FetchDataAsyncTask().execute();

    }

    private class FetchDataAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(API_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
            } catch (IOException e) {
                Log.e(TAG, "Error fetching data: " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray jsonArray = new JSONArray(result);
                StringBuilder data = new StringBuilder();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("number");
                    String body = jsonObject.getString("date");
                    String time = jsonObject.getString("status");
                    data.append("Claim Number: ").append(title).append("\nDate: ").append(body).append("\nStatus").append(time).append("\n\n");
                }

                statusdetails.setText(data.toString());

            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON: " + e.getMessage());
            }
        }
    }
}
