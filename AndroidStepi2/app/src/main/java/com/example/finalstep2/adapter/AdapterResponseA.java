package com.example.finalstep2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.finalstep2.R;
import com.example.finalstep2.model.Response;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterResponseA extends RecyclerView.Adapter<AdapterResponseA.ResponseViewHolder> {

    private List<Response> responseList = new ArrayList<>();
    public AdapterResponseA(List<Response> responseList) {
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response,parent,false);
        return new ResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseViewHolder holder, int position) {
        holder.bindResponse(responseList.get(position));
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public void setNewsList(List<Response> newsList) {
        this.responseList = newsList;
        notifyDataSetChanged();
    }

    public class ResponseViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgCatResponse;
        private ImageView imgItemResponse;
        private TextView txtTitleResponse;
        private TextView txtDateResponse;
        private TextView txtCatResponse;

        public ResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCatResponse = itemView.findViewById(R.id.imgCatResponse);
            imgItemResponse = itemView.findViewById(R.id.imgItemResponse);
            txtTitleResponse = itemView.findViewById(R.id.txtTitleResponse);
            txtDateResponse = itemView.findViewById(R.id.txtDateResponse);
            txtCatResponse = itemView.findViewById(R.id.txtCatResponse);
        }

        public void bindResponse(Response response)
        {
            Glide.with(itemView.getContext()).load(response.getCatImage()).into(imgCatResponse);
            Glide.with(itemView.getContext()).load(response.getImage()).into(imgItemResponse);
            txtDateResponse.setText(response.getDate());
            txtTitleResponse.setText(response.getTitle());
            txtCatResponse.setText(response.getCat());
        }

    }

}
