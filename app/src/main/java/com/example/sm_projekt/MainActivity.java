package com.example.sm_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvPrzypadki, tvWyzdrowiali, tvKrytyczni, tvChorzy, tvDzisiejszePrzypadki,  tvZgony,tvDzisiejszeZgony, tvDotknietePanstwa;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPrzypadki = findViewById((R.id.tvPrzypadki));
        tvWyzdrowiali = findViewById((R.id.tvWyzdrowiali));
        tvKrytyczni = findViewById((R.id.tvKrytyczni));
        tvChorzy = findViewById((R.id.tvChorzy));
        tvDzisiejszePrzypadki = findViewById((R.id.tvDzisiejszePrzypadki));
        tvZgony = findViewById((R.id.tvZgony));
        tvDzisiejszeZgony = findViewById((R.id.tvDzisiejszeZgony));
        tvDotknietePanstwa = findViewById((R.id.tvDotknietePanstwa));

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);

        pobierzDane();

    }

    private void pobierzDane() {

        String url = "https://corona.lmao.ninja/v2/all";

        simpleArcLoader.start();

        StringRequest request = new StringRequest((Request.Method.GET), url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject((response.toString()));


                            tvPrzypadki.setText(jsonObject.getString("cases"));
                            tvWyzdrowiali.setText(jsonObject.getString("recovered"));
                            tvKrytyczni.setText(jsonObject.getString("critical"));
                            tvChorzy.setText(jsonObject.getString("active"));
                            tvDzisiejszePrzypadki.setText(jsonObject.getString("todayCases"));
                            tvZgony.setText(jsonObject.getString("deaths"));
                            tvDzisiejszeZgony.setText(jsonObject.getString("todayDeaths"));
                            tvDotknietePanstwa.setText(jsonObject.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("Przypadki",Integer.parseInt(tvPrzypadki.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Wyzdrowiali",Integer.parseInt(tvWyzdrowiali.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Zgony",Integer.parseInt(tvZgony.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Chorzy",Integer.parseInt(tvChorzy.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility((View.GONE));
                            scrollView.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility((View.GONE));
                            scrollView.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility((View.GONE));
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void wybierzKraj(View view) {

    }
}