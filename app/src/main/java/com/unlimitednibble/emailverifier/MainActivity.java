package com.unlimitednibble.emailverifier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    /*

        App developed by Rengarajan R
        Mail me renga.anbil@gmail.com

     */
    EditText editText;
    TextView textView;
    Button button;
    RequestQueue requestQueue;

    final String url = "http://api.openweathermap.org/data/2.5/weather?q=";
    final  String apikey = "Your Own API Key";
    /*
            API key fetched from

            http://api.openweathermap.org/

            Create an Account in openweather and generate a API key and paste it inside the double quotes

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.city);
        textView = findViewById(R.id.we);
        button= findViewById(R.id.fetch);
        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().trim() == null){
                    editText.setError("Enter a City Nmae");
                }
                else {
                    String orginalUrl = url + editText.getText().toString().trim() + apikey;
                    objectrqst(orginalUrl);
                }
            }
        });
    }

    private void objectrqst(String orginalUrl) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, orginalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // fetching from API
                  JSONObject JSON = response.getJSONObject("main");
                  JSONArray JSAR = response.getJSONArray("weather");
                  JSONObject JSOB = JSAR.getJSONObject(0);

                    // climate
                  String climate = JSOB.getString("main");

                    //temperature
                  float temperature = JSON.getInt("temp");

                  //converting from  kelvin to celsius
                  temperature-=272;
                    Log.d("TAG", String.valueOf(temperature));
                    textView.setText("");
                  textView.append(climate +"\n" +String.valueOf(temperature)+"  Degree Celsius");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }


}
