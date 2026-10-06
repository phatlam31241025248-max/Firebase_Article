package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class ShowArticleActivity
        extends AppCompatActivity {

    FirebaseFirestore db;

    RecyclerView recyclerView;

    List<Article> articles =
            new ArrayList<>();

    ArticleViewAdapter adapter;

    // Listener theo dõi Firebase
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_show_data
        );

        FirebaseApp.initializeApp(this);

        db =
                FirebaseFirestore.getInstance();

        recyclerView =
                findViewById(
                        R.id.reclyclerview
                );

        adapter =
                new ArticleViewAdapter(
                        this,
                        articles
                );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerView.setAdapter(
                adapter
        );

        loadArticlesRealtime();
    }

    private void loadArticlesRealtime() {

        listenerRegistration =
                db.collection("articles")
                        .addSnapshotListener(
                                (value, error) -> {

                                    if (error != null) {

                                        Toast.makeText(
                                                ShowArticleActivity.this,
                                                "Lỗi: "
                                                        + error.getMessage(),
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        return;
                                    }

                                    if (value == null) {
                                        return;
                                    }

                                    // Xóa danh sách cũ
                                    articles.clear();

                                    // Lấy dữ liệu mới từ Firebase
                                    for (
                                            QueryDocumentSnapshot document
                                            : value
                                    ) {

                                        String title =
                                                document.getString(
                                                        "title"
                                                );

                                        String content =
                                                document.getString(
                                                        "content"
                                                );

                                        Article article =
                                                new Article(
                                                        document.getId(),
                                                        title,
                                                        content
                                                );

                                        articles.add(
                                                article
                                        );
                                    }

                                    // Cập nhật Adapter
                                    adapter.update(
                                            articles
                                    );

                                    adapter.notifyDataSetChanged();
                                }
                        );
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        // Hủy listener khi Activity bị đóng
        if (listenerRegistration != null) {

            listenerRegistration.remove();

            listenerRegistration = null;
        }
    }
}