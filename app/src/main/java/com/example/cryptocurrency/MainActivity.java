package com.example.cryptocurrency;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity {

    JSONArray arr;
    String[] name={"BitCoin"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView listview=(TextView) findViewById(R.id.d);
        final RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,"https://www.buyucoin.com/api/v1/btc/",(String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    arr=response.getJSONArray("BuyUcoin_data");
                    for(int j=0;j<arr.length();j++) {
                        JSONObject prices = arr.getJSONObject(j);
                        listview.setText(prices.getString("btc_buy_price"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestQueue.stop();
            }
        });
        requestQueue.add(jsonObjectRequest);
//        customAdapter customadapter=new customAdapter();
//        listview.setAdapter(customadapter);
    }
//    class customAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return name.length;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            view=getLayoutInflater().inflate(R.layout.customlayout,null);
//            TextView tname=(TextView)view.findViewById(R.id.textname);
//            TextView tprice=(TextView)view.findViewById(R.id.textprice);
//
//            return view;
//        }
//    }
}
