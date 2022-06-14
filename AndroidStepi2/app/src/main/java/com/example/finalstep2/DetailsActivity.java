package com.example.finalstep2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.finalstep2.api.ApiService;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView txtTitleDetails;
    private ImageView imgResponseDetails;
    private TextView txtDateDetails;
    private TextView txtCatDetails;
    private TextView txtContentDetails;
    private CircleImageView imgCatDetails;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        apiService = new ApiService(this);
        findViews();
        getContent();

    }

    private void findViews() {
        imgBack = findViewById(R.id.imgBack);
        txtTitleDetails = findViewById(R.id.txtTitleDetails);
        imgResponseDetails = findViewById(R.id.imgResponseDetails);
        txtDateDetails = findViewById(R.id.txtDateDetails);
        txtCatDetails = findViewById(R.id.txtCatDetails);
        txtContentDetails = findViewById(R.id.txtContentDetails);
        imgCatDetails = findViewById(R.id.imgCatDetails);
        imgBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void getContent() {

        apiService.getDetails(getIntent().getIntExtra("id", 0), getIntent().getStringExtra("ln"),
                response -> {
                    txtCatDetails.setText(response.getCat());
                    txtContentDetails.setText(response.getContent());
                    txtDateDetails.setText(response.getDate());
                    txtTitleDetails.setText(response.getTitle());
                    Glide.with(DetailsActivity.this).load(response.getCatImage()).into(imgCatDetails);
                    Glide.with(DetailsActivity.this).load(response.getImage()).into(imgResponseDetails);
                },error -> {
                    Toast.makeText(DetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                });

    }

}