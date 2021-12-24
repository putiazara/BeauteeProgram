package com.project.beauteeprogram;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    private TextView txtNamaLengkap;
    private TextView txtNickname;
    private TextView txtEmail;
    private TextView txtDomisili;
    private TextView txtSocmed;
    private TextView txtSkintype;
    private TextView txtRating;
    private String namaLengkap;
    private String nickname;
    private String email;
    private String domisili;
    private String socmed;
    private String skintype;
    private String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        txtNamaLengkap = findViewById(R.id.isi_namalengkap);
        txtNickname = findViewById(R.id.isi_nickname);
        txtEmail = findViewById(R.id.isi_email);
        txtDomisili = findViewById(R.id.isi_domisili);
        txtSocmed = findViewById(R.id.isi_socmed);
        txtSkintype = findViewById(R.id.isi_skintype);
        txtRating = findViewById(R.id.isi_rating);

        Intent intent = getIntent();
        namaLengkap = intent.getExtras().getString("namaLengkap");
        nickname = intent.getExtras().getString("nickname");
        email = intent.getExtras().getString("email");
        domisili = intent.getExtras().getString("domisili");
        socmed = intent.getExtras().getString("socmed");
        skintype = intent.getExtras().getString("skintype");
        rating = intent.getExtras().getString("rating");

        txtNamaLengkap.setText(namaLengkap);
        txtNickname.setText(nickname);
        txtEmail.setText(email);
        txtDomisili.setText(domisili);
        txtSocmed.setText(socmed);
        txtSkintype.setText(skintype);
        txtRating.setText(rating);
    }
}