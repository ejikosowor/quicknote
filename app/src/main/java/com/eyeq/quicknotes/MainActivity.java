package com.eyeq.quicknotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String url = "http://192.168.43.175/android/api/notes";

    private List<Note> list = new ArrayList<>();
    private Adapter adapter;

    ListView listView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });

        listView = (ListView) findViewById(R.id.notes_list_view);
        adapter = new Adapter(this, list);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest arrReq = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        JSONArray obj = response.getJSONArray("data");

                        if(obj.length() > 0) {
                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject jsonObj = obj.getJSONObject(i);

                                Note noteList = new Note();
                                noteList.setTitle(jsonObj.get("title").toString());
                                noteList.setDate(jsonObj.get("updated_at").toString());

                                list.add(noteList);
                            }
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "No Notes found",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter.notifyDataSetChanged();
                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 401) {
                        Toast.makeText(MainActivity.this, "Unauthorized", Toast.LENGTH_LONG).show();
                    }
                    Log.e("Volley", String.valueOf(error));
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjNiZDEwOTAyYmNmOTcxZGUzYTYwMGQ5OTlhYjgwOWQzNTc2YzMzMjg1NjJiYjUwNzgyMTdmMTE1Zjc0NTY2NDZhN2JmZTNkYmRmYmUyOGVjIn0.eyJhdWQiOiIyIiwianRpIjoiM2JkMTA5MDJiY2Y5NzFkZTNhNjAwZDk5OWFiODA5ZDM1NzZjMzMyODU2MmJiNTA3ODIxN2YxMTVmNzQ1NjY0NmE3YmZlM2RiZGZiZTI4ZWMiLCJpYXQiOjE1MDQyODM4MTcsIm5iZiI6MTUwNDI4MzgxNywiZXhwIjoxNTA1NTc5ODE3LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.vSP8SeDI77Ib4bx4ezMhAfVbi1WRnldx3TVVDVXd1AdWUOplvCho9dxMoafSAI7TKpWKPviAlw7FdWi_X2uJbbsmOaaVHYe5OGufOvpXWsXj8OUEDxTQ4Zsi2SHCoNfNrFkNDJnJkS685ec40eJznHQkxJodpCgNr8EhWj3-pP_hxwB7B_93bICUvSXYhAq2JqScvnQO_-R0EqLtst26-Yrr9gIX590r40zujjNk1tn257tLZjH3v2xnULexiF3rHSQhZ4whjEd0sBw8HpLtvS9gBKZlTVe_pqF43PUH5WglIJ8JWUNq4dRuMXcJteP2IiPk6PXnTLkR59txmGJwoYCxpOc7ZFXpggqQ03K_jOugfFidIbKJgDHk48BeyxoWybJTvGIwP8xEXYoZ4bvBlt7A4BCICJIhavUD2Gkk-4KNxB3wZyePseC3T_i1DTcf4OlzpkxpgF9vQpvvST_I4LGwg4kpjMFETKn13K5xPgXBz59-a4ZDzOmP42c3AwCroJNJBr97YVjjycrvdlhzTKZHWOF8iTXPBQ_pyGOdyGXF_KlVQHAH5HKNbG-f27lzrlZAGsU_WeY7PGjsTfXF0j0u95lTfgPeSFYkFRKMr4nSLAK_l-JO6hQ3ndDbTmU8x0eYZXz3trtXA8e4TmP8zb-YmcN34rEHhbOcTrPUC-o";
                    String auth = "Bearer "+ token;
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

        requestQueue.add(arrReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}