package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowArticleActivity extends AppCompatActivity {

    FirebaseFirestore db;

    RecyclerView recyclerView;

    List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_show_data);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        FirebaseApp.initializeApp(this);

        recyclerView =
                findViewById(R.id.reclyclerview);

        ArticleViewAdapter adapter =
                new ArticleViewAdapter(
                        getBaseContext(),
                        articles
                );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getBaseContext())
        );

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("articles")
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {

                            @Override
                            public void onComplete(
                                    @NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {

                                    articles.clear();

                                    for (
                                            QueryDocumentSnapshot q
                                            : task.getResult()
                                    ) {

                                        Map<String, Object> data =
                                                q.getData();

                                        String title =
                                                (String) data.get("title");

                                        String content =
                                                (String) data.get("content");

                                        Article article =
                                                new Article(
                                                        title,
                                                        content
                                                );

                                        articles.add(article);
                                    }

                                    adapter.update(articles);

                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                );
    }
}