package com.example.vehicalinsurance;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginactivity extends AppCompatActivity {

    private Button btn;
    private TextView email, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btn = findViewById(R.id.login_btn);
        email = findViewById(R.id.emailid);
        pass = findViewById(R.id.password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String emailBody = email.getText().toString();
        String passBody = pass.getText().toString();
        String apiEndpoint = "https://web-dev-1-v7lo.onrender.com/login";

        StringRequest sreq = new StringRequest(Request.Method.POST, apiEndpoint,
                res -> Toast.makeText(loginactivity.this, "Response: " + res, Toast.LENGTH_LONG).show(),


                err -> Toast.makeText(loginactivity.this, "Error: " + err, Toast.LENGTH_LONG).show())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("username", emailBody);
                params.put("pass", passBody);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(loginactivity.this);
        requestQueue.add(sreq);
    }
}







//    private void loginUser() {
//        String emailBody = email.getText().toString();
//        String passBody = pass.getText().toString();
//        String apiEndpoint = "https://web-dev-1-v7lo.onrender.com/login";
//        RequestQueue requestQueue = Volley.newRequestQueue(this);

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("email", emailBody);
//            jsonObject.put("password", passBody);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiEndpoint, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    boolean success = response.getBoolean("success");
//                    if (success) {
//                        Toast.makeText(LoginActivity.this, "login successfully!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "login failed ", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("LoginActivity", "Error: " + error.getMessage());
//                Toast.makeText(LoginActivity.this, "Error occurred " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });



//    private void sendDataToAPI(String email, String password){
//        try {
//        String apiEndpoint="https://web-dev-1-v7lo.onrender.com/login";
//            final String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
//
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, apiEndpoint, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Toast.makeText(LoginActivity.this, "Data stored successfully!", Toast.LENGTH_SHORT).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(LoginActivity.this, "Error storing data "+error.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }){
//                public byte[] getBody() throws AuthFailureError{
//                    try{
//                        return data.getBytes("utf-8");
//                    }catch (UnsupportedEncodingException e){
//                        e.printStackTrace();
//                        return null;
//                    }
//                }
//            };
//            requestQueue.add(stringRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }