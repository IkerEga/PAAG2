package eus.ikeregana.invesnews_multimedia.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eus.ikeregana.invesnews_multimedia.R;
import eus.ikeregana.invesnews_multimedia.data.model.ArticleDto;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ArticleDto article);
    }
    private OnItemClickListener listener;

    private List<ArticleDto> articles = new ArrayList<>();

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        ArticleDto article = articles.get(position);

        holder.tvTitle.setText(article.getTitle());
        holder.tvDescription.setText(article.getDescription());
        holder.tvSourceDate.setText(article.getSource());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(article);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<ArticleDto> newArticles) {
        if (newArticles == null) newArticles = new ArrayList<>();
        articles = newArticles;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvSourceDate;
        TextView tvDescription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSourceDate = itemView.findViewById(R.id.tvSourceDate);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
