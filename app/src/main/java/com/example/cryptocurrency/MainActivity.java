package com.example.cryptocurrency;

import android.app.VoiceInteractor;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity  {

    JSONArray arr;
    String[] key;
    String[] name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        key=new String[60];
        name=new String[60];
        ListView listview=(ListView) findViewById(R.id.listing);
        final RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,"https://www.buyucoin.com/api/v1/crypto/",(String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    arr=response.getJSONArray("BuyUcoin_data");
                    for(int j=0;j<arr.length();j++) {
                        JSONObject prices = arr.getJSONObject(j);
                        Iterator<String> it= prices.keys();
                        int i=0;
                        while (it.hasNext()){
                            key[i]=it.next();
                            name[i]=prices.getString(key[i]);
                            Log.d(valueOf(MainActivity.this),"key:-"+key[i]+"Value"+name[i]);
                            System.out.println("keyleololololo");
                            i++;
                        }
                        Log.d(valueOf(MainActivity.this), valueOf(prices));
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


        customAdapter customadapter=new customAdapter(MainActivity.this,key,name);
        listview.setAdapter(customadapter);
    }
    class customAdapter extends BaseAdapter {
        Context c;
        String[] key;
        String[] name;
        customAdapter(Context c,String[] key,String[] name){
            this.name=name;
            this.key=key;
            this.c=c;
        }


        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int i) {
            return name[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        class ViewHolder{
            TextView tname;
            TextView tprice;
            ViewHolder(View v){
                tname=(TextView)v.findViewById(R.id.textname);
                tprice=(TextView)v.findViewById(R.id.textprice);
            }

        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View row=view;
            ViewHolder viewHolder=null;
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.customlayout, null);
                viewHolder=new ViewHolder(row);
                row.setTag(viewHolder);
            }
            else{
                viewHolder=(ViewHolder) row.getTag();
            }
            viewHolder.tname.setText(key[i]);
            viewHolder.tprice.setText(name[i]);
            return row;
        }
    }
}
