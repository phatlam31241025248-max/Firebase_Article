package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder
        extends RecyclerView.ViewHolder {

  private ImageView imgArticle;
  private TextView txtTitle;

  public ArticleViewHolder(
          @NonNull View itemView) {

    super(itemView);

    imgArticle =
            itemView.findViewById(
                    R.id.img_article
            );

    txtTitle =
            itemView.findViewById(
                    R.id.txt_title
            );
  }

  public ImageView getImgArticle() {
    return imgArticle;
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }
}