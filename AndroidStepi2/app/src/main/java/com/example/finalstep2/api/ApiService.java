package com.example.finalstep2.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.finalstep2.model.Response;
import com.example.finalstep2.model.ResponseDetails;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiService {

    private RequestQueue requestQueue;

    public ApiService(Context context)
    {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getResponse(int page, String language, String filter,
                            com.android.volley.Response.Listener<List<Response>> listener,
                            com.android.volley.Response.ErrorListener errorListener) {
        GsonRequest<List<Response>> request = new GsonRequest<List<Response>>
                (Request.Method.GET,
                "https://rezaeianengineer.com/finaljava/shownews.php?page=" + page + "&per_page=5"+"&filter="+filter,
                new TypeToken<List<Response>>() {
                }.getType(), listener, errorListener);
        Map<String, String> map = new HashMap<>();
        map.put("Accept-Language", language);
        request.setMap(map);
        requestQueue.add(request);

    }

    public void getDetails(int id, String language ,
                           com.android.volley.Response.Listener<ResponseDetails> listener,
                           com.android.volley.Response.ErrorListener errorListener)
    {
        GsonRequest<ResponseDetails> request = new GsonRequest<ResponseDetails>(Request.Method.POST,
                "https://rezaeianengineer.com/finaljava/getcontent.php",new TypeToken<ResponseDetails>(){}.getType()
        ,listener,errorListener);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",id);
        request.setJsonObject(jsonObject);
        Map<String, String> map = new HashMap<>();
        map.put("Accept-Language", language);
        request.setMap(map);
        requestQueue.add(request);
    }

    public void search(String language, String keyword,
                       com.android.volley.Response.Listener<List<Response>> listener,
                       com.android.volley.Response.ErrorListener errorListener) {
        GsonRequest<List<Response>> request = new GsonRequest<List<Response>>(Request.Method.GET,
                "https://rezaeianengineer.com/finaljava/search.php?search="+keyword,
                new TypeToken<List<Response>>() {
                }.getType(), listener, errorListener);
        Map<String, String> map = new HashMap<>();
        map.put("Accept-Language", language);
        request.setMap(map);
        requestQueue.add(request);
    }

}
