package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    FirebaseFirestore db;

    Button btAdd;
    Button btShow;

    EditText etName;
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();

        btAdd = findViewById(R.id.btAdd);
        btShow = findViewById(R.id.btShow);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);

        btAdd.setOnClickListener(this);
        btShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // =========================
        // ADD ARTICLE
        // =========================

        if (view.getId() == R.id.btAdd) {

            String title =
                    etName.getText()
                            .toString()
                            .trim();

            String content =
                    etPhone.getText()
                            .toString()
                            .trim();

            // Kiểm tra Title
            if (title.isEmpty()) {

                Toast.makeText(
                        this,
                        "Vui lòng nhập Title",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Kiểm tra Content
            if (content.isEmpty()) {

                Toast.makeText(
                        this,
                        "Vui lòng nhập Content",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Tạo Article
            Article article =
                    new Article(
                            title,
                            content
                    );

            // Lưu Firebase
            db.collection("articles")
                    .add(article)
                    .addOnSuccessListener(
                            documentReference -> {

                                Toast.makeText(
                                        this,
                                        "Add Article thành công",
                                        Toast.LENGTH_SHORT
                                ).show();

                                // XÓA TITLE
                                etName.setText("");

                                // XÓA CONTENT
                                etPhone.setText("");
                            }
                    )
                    .addOnFailureListener(
                            e -> {

                                Toast.makeText(
                                        this,
                                        "Lỗi: "
                                                + e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                    );
        }

        // =========================
        // SHOW ARTICLES
        // =========================

        else if (view.getId() == R.id.btShow) {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            ShowArticleActivity.class
                    );

            startActivity(intent);
        }
    }
}