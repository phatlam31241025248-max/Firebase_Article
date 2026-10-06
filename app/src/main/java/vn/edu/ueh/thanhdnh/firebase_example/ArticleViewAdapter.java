package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleViewAdapter
        extends RecyclerView.Adapter<ArticleViewHolder> {

    private LayoutInflater inflater;

    private List<Article> articles;

    private Context context;

    // 6 ảnh tương ứng với 6 bài viết
    private final int[] articleImages = {
            R.drawable.animal_1_cat,
            R.drawable.animal_2_dog,
            R.drawable.animal_3_lion,
            R.drawable.animal_4_panda,
            R.drawable.animal_5_tiger,
            R.drawable.animal_6_fox
    };

    public ArticleViewAdapter(
            Context context,
            List<Article> articles) {

        this.context = context;

        this.inflater =
                LayoutInflater.from(context);

        this.articles = articles;
    }

    public void update(
            List<Article> articles) {

        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                inflater.inflate(
                        R.layout.contact_list,
                        parent,
                        false
                );

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ArticleViewHolder holder,
            int position) {

        Article article =
                articles.get(position);

        // Hiện Title
        holder.getTxtTitle()
                .setText(
                        article.getTitle()
                );

        // Chọn ảnh theo vị trí bài viết
        int imageIndex =
                position % articleImages.length;

        int imageResId =
                articleImages[imageIndex];

        // Hiện ảnh
        holder.getImgArticle()
                .setImageResource(
                        imageResId
                );

        // Bấm vào ảnh
        holder.getImgArticle()
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    context,
                                    ArticleDetailActivity.class
                            );

                    // Gửi ID của Article
                    intent.putExtra(
                            "articleId",
                            article.getDocumentId()
                    );

                    // Gửi ảnh tương ứng
                    intent.putExtra(
                            "imageResId",
                            imageResId
                    );

                    context.startActivity(intent);
                });
    }

    @Override
    public int getItemCount() {

        return articles.size();
    }
}