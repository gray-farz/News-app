package com.example.finalstep2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalstep2.adapter.AdapterResponseB;
import com.example.finalstep2.api.ApiService;
import com.example.finalstep2.dialog.DialogLanguage;
import com.example.finalstep2.dialog.FilterDialogEn;
import com.example.finalstep2.dialog.FilterDialogFa;
import com.example.finalstep2.model.Response;
import com.example.finalstep2.utils.SharedPreferencesLanguage;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogLanguage.OnChangeLanguage,AdapterResponseB.OnClickResponse
,FilterDialogEn.OnClickFilterEn, FilterDialogFa.OnClickFilter
{
    private ApiService apiService;
    private SharedPreferencesLanguage preferencesLanguage;
    private MaterialToolbar toolbar;
    private RecyclerView recyclerNews;
    private ProgressBar progressBar;
    private String language = "";
    private AdapterResponseB adapterResponseB;
    private List<Response> responseList = new ArrayList<>();
    private int page = 1;
    private int loadIndex;
    private String filter = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new ApiService(this);
        preferencesLanguage = new SharedPreferencesLanguage(this);

        findViews();
        listener();
        getLanguage();
        setUp();
        getResponse();
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerNews = findViewById(R.id.recyclerNews);
        recyclerNews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        progressBar = findViewById(R.id.progressBar);
    }
    private void listener() {
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.language:
                    DialogLanguage dialogLanguage = new DialogLanguage();
                    dialogLanguage.show(getSupportFragmentManager(), null);
                    break;
                case R.id.filter:
                    if (language.equals("fa"))
                    {
                        Log.d("aaa", "listener: fa");
                        FilterDialogFa filterDialogFa = FilterDialogFa.newInstance(FilterDialogFa.CAT_SELECT);
                        filterDialogFa.show(getSupportFragmentManager(), null);
                    }
                    else if (language.equals("en")) {
                        FilterDialogEn filterDialogEn = FilterDialogEn.newInstance(FilterDialogEn.CAT_SELECT);
                        filterDialogEn.show(getSupportFragmentManager(), null);
                    }
                    Log.d("aaa", "listener: null");
                    break;
                case R.id.search:
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("ln", language);
                    startActivity(intent);
                    break;
            }
            return false;
        });
    }
    private void getLanguage() {
        if (preferencesLanguage.getLn().equals("en"))
            language = "en";
        else if (preferencesLanguage.getLn().equals("fa"))
            language = "fa";
    }
    private void setUp() {
        adapterResponseB = new AdapterResponseB(responseList, recyclerNews, this::onClick);
        recyclerNews.setAdapter(adapterResponseB);

        adapterResponseB.setOnLoadMoreListener(() -> {
            if (responseList.size() / 5 == page)
            {
                page++;
                responseList.add(null);
                loadIndex = responseList.size() - 1;
                adapterResponseB.notifyItemInserted(loadIndex);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getResponse();
                    }
                }, 500);

            }
        });
    }
    private void getResponse()
    {
        if (page == 1) {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService.getResponse(page,language,filter,
                response -> {
                    if (page != 1) {
                        responseList.remove(loadIndex);
                        adapterResponseB.notifyItemRemoved(loadIndex);
                    }
                    responseList.addAll(response);
                    adapterResponseB.notifyDataSetChanged();
                    adapterResponseB.setLoading(false);
                    progressBar.setVisibility(View.GONE);
                },error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }

        );
    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("ln", language);
        startActivity(intent);
    }

    @Override
    public void onChange(String language) {
        this.language = language;
        page = 1;
        responseList.clear();
        adapterResponseB.notifyDataSetChanged();
        getResponse();
    }


    @Override
    public void onCatEn(String filter) {
        this.filter = filter;
        page = 1;
        responseList.clear();
        adapterResponseB.notifyDataSetChanged();
        getResponse();
    }

    @Override
    public void onFilterDateEn(String filter) {
        this.filter = filter;
        page = 1;
        responseList.clear();
        adapterResponseB.notifyDataSetChanged();
        getResponse();
    }

    @Override
    public void onCancelFilterEn(String filter) {
        this.filter = filter;
        page = 1;
        responseList.clear();
        adapterResponseB.notifyDataSetChanged();
        getResponse();
    }

    @Override
    public void onCat(String filter) {
        this.filter = filter;
        page = 1;
        responseList.clear();
        adapterResponseB.notifyDataSetChanged();
        getResponse();
    }

    @Override
    public void onFilterDate(String filter) {
        this.filter = filter;
        page = 1;
        responseList.clear();
        adapterResponseB.notifyDataSetChanged();
        getResponse();
    }

    @Override
    public void onCancelFilter(String filter) {
        this.filter = filter;
        page = 1;
        responseList.clear();
        adapterResponseB.notifyDataSetChanged();
        getResponse();
    }
}