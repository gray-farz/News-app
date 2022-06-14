package com.example.finalstep2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalstep2.R;
import com.example.finalstep2.model.Response;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterResponseB extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Response> responseList = new ArrayList<>();
    private int sizeIndex;
    private int lastVisible;
    private OnLoadMoreListener onLoadMoreListener;
    private OnClickResponse onClickResponse;
    private boolean isLoading = false;
    private int countLoadMore = 2;

    public AdapterResponseB(List<Response> responseList, RecyclerView recyclerView, OnClickResponse onClickResponse) {
        this.responseList = responseList;
        this.onClickResponse = onClickResponse;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                sizeIndex = layoutManager.getItemCount();
                lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                if (isLoading == false && sizeIndex <= (lastVisible + countLoadMore)) {
                    if (onLoadMoreListener != null)
                        onLoadMoreListener.loadMore();
                    isLoading = true;
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response, parent, false);
            return new ResponseViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResponseViewHolder) {
            ((ResponseViewHolder) holder).bindResponse(responseList.get(position));
        } else if (holder instanceof LoadingViewHolder) {
            ((LoadingViewHolder) holder).bindProgress();
        }
    }

    @Override
    public int getItemCount() {
        return responseList == null ? 0 : responseList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public int getItemViewType(int position) {
        if (responseList.get(position) != null)
            return 1;
        else return 2;
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

        public void bindResponse(Response response) {
            Glide.with(itemView.getContext()).load(response.getCatImage()).into(imgCatResponse);
            Glide.with(itemView.getContext()).load(response.getImage()).into(imgItemResponse);
            txtDateResponse.setText(response.getDate());
            txtTitleResponse.setText(response.getTitle());
            txtCatResponse.setText(response.getCat());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickResponse.onClick(response.getId());
                }
            });
        }

    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
        public void bindProgress() {
            progressBar.setIndeterminate(true);
        }
    }

    public interface OnClickResponse {
        void onClick(int id);
    }

}
