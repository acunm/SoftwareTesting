package com.toro.softwaretesting;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button isimGetir;
    private TextView dersIsmi;
    private EditText eklenecekDers;
    private Button dersEkle;
    private Button guncelle;
    private TextView alanogrenci;
    private FirebaseFirestore db;
    private Button dersisil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dersIsmi = findViewById(R.id.tvDersIsmi);
        isimGetir = findViewById(R.id.btnDers);
        eklenecekDers = findViewById(R.id.eklenecekders);
        dersEkle = findViewById(R.id.dersekle);
        guncelle = findViewById(R.id.updateButon);
        alanogrenci = findViewById(R.id.alanogrenci);
        dersisil = findViewById(R.id.dersisil);

        db = FirebaseFirestore.getInstance();


        isimGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("Dersler").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            dersIsmi.setText(task.getResult().getDocuments().get(0).getString("dersIsmi"));

                        }

                    }
                });
            }
        });

        dersEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dersIsmi = eklenecekDers.getText().toString();

                Map<String, Object> data = new HashMap<>();
                data.put("dersIsmi", dersIsmi);

                db.collection("Dersler").document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Eklendi.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String yeniisim = eklenecekDers.getText().toString();

                Map<String, Object> data = new HashMap<>();
                data.put("dersIsmi", yeniisim);

                db.collection("Dersler").document("KhNCoY9qnrmC7PiRk0Ln")
                        .update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Güncellendi.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        db.collection("Dersler").document("eKSWibLiG4LyZt6FDaDP")
                .collection("alanOgrenciler").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                alanogrenci.setText(task.getResult().getDocuments().get(0).getString("isim"));

            }
        });

        dersisil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("Dersler").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                for(int i=0; i < task.getResult().getDocuments().size();i++){

                                    DocumentSnapshot snapshot = task.getResult().getDocuments().get(i);

                                    if(snapshot.getString("dersIsmi").equals("fizik")){

                                        documentSil(snapshot.getId());

                                    }
                                }

                            }
                        });

            }
        });


    }

    private void documentSil(String id) {

        db.collection("Dersler").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Silindi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
