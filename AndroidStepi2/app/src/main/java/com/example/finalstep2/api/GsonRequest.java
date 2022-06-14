package com.example.finalstep2.api;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {

    private Type type;
    private Response.Listener<T> listener;
    private JsonObject jsonObject;
    private Map<String,String> map;
    public GsonRequest(int method, String url, Type type, Response.Listener listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data);
            Gson gson = new Gson();
            T result = gson.fromJson(json, type);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new VolleyError());
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (jsonObject != null) {
            return jsonObject.toString().getBytes();
        }
        return super.getBody();
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (map!=null)?map :super.getHeaders();
    }

}
