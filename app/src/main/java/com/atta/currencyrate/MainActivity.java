package com.atta.currencyrate;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String RATE_URL =
            "http://apilayer.net/api/live?access_key=1680584d6c2c64b8a76306249a4714b2&currencies=USD,EUR,RUB,JPY,GBP,CAD,TRY,KRW,CHF,CNY,EGP&format=1";


    /** Tag for the log messages */
    public final String LOG_TAG = MainActivity.class.getSimpleName();


    public List<Currency> curr = new ArrayList<>();

    RecyclerView recyclerView;

    CurrencyAdapter currencyAdapter;

    String[] currencyNames;

    int[] currencyImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        currencyNames = new String[]{"USD", "EUR", "RUB", "JPY",
                "GBP", "CAD", "TRY", "KRW", "CHF", "CNY"};

        currencyImages = new int[]{R.drawable.us, R.drawable.europ, R.drawable.russia, R.drawable.japan
                , R.drawable.uk, R.drawable.canada, R.drawable.turkey, R.drawable.southkorea, R.drawable.switzerland
                , R.drawable.china};

        getData();

        final SwipeRefreshLayout mySwipeRefreshLayout = findViewById(R.id.swip);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getData();

                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

    }

    public void getData(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonResponse) {
                        Log.d(LOG_TAG, jsonResponse);
                        curr.clear();


                        try {
                            // Create a JSONObject from the JSON response string
                            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

                            JSONObject featureArray = baseJsonResponse.getJSONObject("quotes");

                            double cur1 = featureArray.getDouble("USDEGP");
                            Currency newCurrency = new Currency(currencyImages[0], currencyNames[0], cur1);

                            curr.add(newCurrency);

                            curr.add(new Currency(currencyImages[1], currencyNames[1], cur1/featureArray.getDouble("USDEUR")));
                            curr.add(new Currency(currencyImages[2], currencyNames[2], cur1/featureArray.getDouble("USDRUB")));
                            curr.add(new Currency(currencyImages[3], currencyNames[3], cur1/featureArray.getDouble("USDJPY")));
                            curr.add(new Currency(currencyImages[4], currencyNames[4], cur1/featureArray.getDouble("USDGBP")));
                            curr.add(new Currency(currencyImages[5], currencyNames[5], cur1/featureArray.getDouble("USDCAD")));
                            curr.add(new Currency(currencyImages[6], currencyNames[6], cur1/featureArray.getDouble("USDTRY")));
                            curr.add(new Currency(currencyImages[7], currencyNames[7], cur1/featureArray.getDouble("USDKRW")));
                            curr.add(new Currency(currencyImages[8], currencyNames[8], cur1/featureArray.getDouble("USDCHF")));
                            curr.add(new Currency(currencyImages[9], currencyNames[9], cur1/featureArray.getDouble("USDCNY")));

                            currencyAdapter = new CurrencyAdapter(MainActivity.this, curr);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.setAdapter(currencyAdapter);

                        } catch (JSONException e) {
                            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
                        }
                        Log.d(LOG_TAG, "done");

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, error.toString());
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}
