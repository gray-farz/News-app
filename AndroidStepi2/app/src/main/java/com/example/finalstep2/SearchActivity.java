package com.example.finalstep2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.finalstep2.adapter.AdapterResponseA;
import com.example.finalstep2.api.ApiService;
import com.example.finalstep2.model.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageView imgClear;
    private ImageView imgBack;
    private EditText edtSearch;
    private ProgressBar progressBar;
    private AdapterResponseA adapterResponseA;
    private List<Response> responseList = new ArrayList<>();
    private RecyclerView recyclerSearch;
    private ApiService apiService;
    private String language = "";
    private TextView txtEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        apiService = new ApiService(this);

        findViews();
        checkLanguage();
        search();
        clickListener();

    }

    private void findViews() {
        imgBack = findViewById(R.id.imgBack);
        edtSearch = findViewById(R.id.edtSearch);
        imgClear = findViewById(R.id.imgClear);
        recyclerSearch = findViewById(R.id.recyclerSearch);
        txtEmpty = findViewById(R.id.txtEmpty);
        progressBar = findViewById(R.id.progressBar);

        imgClear.setVisibility(View.GONE);
        txtEmpty.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        imgClear.setVisibility(View.INVISIBLE);
        language = getIntent().getStringExtra("ln");
    }

    private void search() {

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    recyclerSearch.setVisibility(View.VISIBLE);
                    getData(s.toString());
                    imgClear.setVisibility(View.VISIBLE);
                } else {
                    imgClear.setVisibility(View.INVISIBLE);
                    recyclerSearch.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void clickListener() {
        imgBack.setOnClickListener(view -> {
            finish();
        });
        imgClear.setOnClickListener(view -> {
            edtSearch.setText("");
        });
    }

    private void getData(String keyword) {

        progressBar.setVisibility(View.VISIBLE);
        apiService.search(language,keyword,
                response -> {
                    if (response.isEmpty()) {
                        txtEmpty.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        recyclerSearch.setVisibility(View.INVISIBLE);
                    } else {
                        txtEmpty.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.GONE);
                        adapterResponseA = new AdapterResponseA(response);
                        adapterResponseA.notifyDataSetChanged();
                        recyclerSearch.setAdapter(adapterResponseA);
                    }
                },error -> {
                    Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });

    }

    private void checkLanguage() {
        if (language.equals("en")) {
            txtEmpty.setText("isEmpty");
        } else if (language.equals("fa")) {
            txtEmpty.setText("محتوایی وجود ندارد");
        }
    }

}