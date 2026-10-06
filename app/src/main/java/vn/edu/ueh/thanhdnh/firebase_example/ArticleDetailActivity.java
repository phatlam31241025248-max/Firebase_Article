package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class ArticleDetailActivity
        extends AppCompatActivity {

    FirebaseFirestore db;

    ImageView imgDetail;

    TextView txtTitle;
    TextView txtContent;

    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_article_detail
        );

        FirebaseApp.initializeApp(this);

        db =
                FirebaseFirestore.getInstance();

        imgDetail =
                findViewById(
                        R.id.img_detail
                );

        txtTitle =
                findViewById(
                        R.id.txt_detail_title
                );

        txtContent =
                findViewById(
                        R.id.txt_detail_content
                );

        String articleId =
                getIntent()
                        .getStringExtra(
                                "articleId"
                        );

        int imageResId =
                getIntent()
                        .getIntExtra(
                                "imageResId",
                                R.drawable.animal_1_cat
                        );

        // Hiện ảnh
        imgDetail.setImageResource(
                imageResId
        );

        if (articleId == null ||
                articleId.isEmpty()) {

            Toast.makeText(
                    this,
                    "Không tìm thấy Article",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadArticleRealtime(articleId);
    }

    private void loadArticleRealtime(
            String articleId) {

        listenerRegistration =
                db.collection("articles")
                        .document(articleId)
                        .addSnapshotListener(
                                (document, error) -> {

                                    if (error != null) {

                                        Toast.makeText(
                                                ArticleDetailActivity.this,
                                                "Lỗi: "
                                                        + error.getMessage(),
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        return;
                                    }

                                    if (document == null ||
                                            !document.exists()) {

                                        Toast.makeText(
                                                ArticleDetailActivity.this,
                                                "Article không tồn tại",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        finish();

                                        return;
                                    }

                                    String title =
                                            document.getString(
                                                    "title"
                                            );

                                    String content =
                                            document.getString(
                                                    "content"
                                            );

                                    if (title == null) {
                                        title = "";
                                    }

                                    if (content == null) {
                                        content = "";
                                    }

                                    // Cập nhật Title ngay
                                    txtTitle.setText(
                                            title
                                    );

                                    // Cập nhật Content ngay
                                    txtContent.setText(
                                            content
                                    );
                                }
                        );
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (listenerRegistration != null) {

            listenerRegistration.remove();

            listenerRegistration = null;
        }
    }
}