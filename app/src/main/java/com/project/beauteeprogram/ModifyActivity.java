package com.project.beauteeprogram;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class ModifyActivity extends AppCompatActivity {
    private EditText edtNamaLengkap;
    private EditText edtNickname;
    private EditText edtEmail;
    private EditText edtDomisili;
    private RadioGroup rgSocmed;
    private RadioButton rbIg;
    private RadioButton rbFb;
    private RadioButton rbPinterest;
    private RadioButton rbLinked;
    private RadioButton rbTwitter;
    private RadioButton rbYoutube;
    private CheckBox cbNormal;
    private CheckBox cbOily;
    private CheckBox cbDry;
    private CheckBox cbSensitive;
    private CheckBox cbCombination;
    private CheckBox cbLainnya;
    private SeekBar seekBar;
    private TextView txtRating;
    private int id;
    private String namaLengkap;
    private String nickname;
    private String email;
    private String domisili;
    private String socmed;
    private String skintype;
    private String rating;
    private Button btnUbah;

    private DatabaseHelper db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        edtNamaLengkap = findViewById(R.id.input_namalengkap);
        edtNickname = findViewById(R.id.input_nickname);
        edtEmail = findViewById(R.id.input_email);
        edtDomisili = findViewById(R.id.input_domisili);
        rgSocmed = findViewById(R.id.rgsocmed);
        rbIg = findViewById(R.id.ig);
        rbFb = findViewById(R.id.fb);
        rbPinterest = findViewById(R.id.pinterest);
        rbLinked = findViewById(R.id.linked);
        rbTwitter = findViewById(R.id.twitter);
        rbYoutube = findViewById(R.id.youtube);
        cbNormal = findViewById(R.id.normal);
        cbOily = findViewById(R.id.oily);
        cbDry = findViewById(R.id.dry);
        cbSensitive = findViewById(R.id.sensitive);
        cbCombination = findViewById(R.id.combination);
        cbLainnya = findViewById(R.id.lainnya);
        seekBar = findViewById(R.id.seekbar);
        txtRating = findViewById(R.id.rating);
        btnUbah = findViewById(R.id.ubah);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");
        namaLengkap = intent.getExtras().getString("namaLengkap");
        nickname = intent.getExtras().getString("nickname");
        email = intent.getExtras().getString("email");
        domisili = intent.getExtras().getString("domisili");
        socmed = intent.getExtras().getString("socmed");
        skintype = intent.getExtras().getString("skintype");
        rating = intent.getExtras().getString("rating");

        edtNamaLengkap.setText(namaLengkap);
        edtNickname.setText(nickname);
        edtEmail.setText(email);
        edtDomisili.setText(domisili);
        txtRating.setText("Ambiance to Join Us: " + rating);
        setSocmedSelected();
        setSkintypeSelected();
        seekBar.setProgress(Integer.parseInt(rating));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                rating = String.valueOf(i);
                txtRating.setText("Ambiance to Join Us: " + rating);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaLengkap = edtNamaLengkap.getText().toString().trim();
                nickname = edtNickname.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                domisili = edtDomisili.getText().toString().trim();
                socmed = getSocmedSelected();
                skintype = getSkintypeSelected();

                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("REGISTER");

                builder.setMessage(
                        "Verify carefully that all the data submitted are correct!\n\n" +
                                "Nama Lengkap: \n" + namaLengkap + "\n\n" +
                                "Username: \n" + nickname + "\n\n" +
                                "Email: \n" + email + "\n\n" +
                                "Domisili: \n" + domisili + "\n\n" +
                                "Social Media: \n" + socmed + "\n\n" +
                                "Skin Type: \n" + skintype + "\n" +
                                "Ambiance to Join Us: \n" + rating + ""
                );

                builder.setPositiveButton("TRUE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent layoutMenu = new Intent(ModifyActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        layoutMenu.putExtra("status", "edit");

                        user = new User();
                        user.setId(id);
                        user.setNamaLengkap(namaLengkap);
                        user.setNickname(nickname);
                        user.setEmail(email);
                        user.setDomisili(domisili);
                        user.setSocmed(socmed);
                        user.setSkintype(skintype);
                        user.setRating(rating);

                        db.update(user);

                        startActivity(layoutMenu);
                        finish();
                    }
                });

                builder.setNegativeButton("FALSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void setSkintypeSelected() {
        if (skintype.contains("Normal Skin")) {
            cbNormal.setChecked(true);
        }
        if (skintype.contains("Oily Skin")) {
            cbOily.setChecked(true);
        }
        if (skintype.contains("Dry Skin")) {
            cbDry.setChecked(true);
        }
        if (skintype.contains("Sensitive Skin")) {
            cbSensitive.setChecked(true);
        }
        if (skintype.contains("Combination Skin")) {
            cbCombination.setChecked(true);
        }
        if (skintype.contains("Others")) {
            cbLainnya.setChecked(true);
        }
    }

    private String getSkintypeSelected() {
        String skintype = "";

        if (cbNormal.isChecked()) {
            skintype += "- Normal Skin\n";
        }
        if (cbOily.isChecked()) {
            skintype += "- Oily Skin\n";
        }
        if (cbDry.isChecked()) {
            skintype += "- Dry Skin\n";
        }
        if (cbSensitive.isChecked()) {
            skintype += "- Sensitive Skin\n";
        }
        if (cbCombination.isChecked()) {
            skintype += "- Combination Skin\n";
        }
        if (cbLainnya.isChecked()) {
            skintype += "- Others\n";
        }

        return skintype;
    }

    private void setSocmedSelected() {
        if (socmed.equals("Instagram")) {
            rbIg.setChecked(true);
        } else if (socmed.equals("Facebook")) {
            rbFb.setChecked(true);
        } else if (socmed.equals("Pinterest")) {
            rbPinterest.setChecked(true);
        } else if (socmed.equals("LinkedIn")) {
            rbLinked.setChecked(true);
        } else if (socmed.equals("Twitter")) {
            rbTwitter.setChecked(true);
        } else if (socmed.equals("Youtube")) {
            rbYoutube.setChecked(true);
        }
    }

    private String getSocmedSelected() {
        String socmed = "";

        int selectedId = rgSocmed.getCheckedRadioButtonId();

        if (selectedId == rbIg.getId()) {
            socmed = "Instagram";
        } else if (selectedId == rbFb.getId()) {
            socmed = "Facebook";
        } else if (selectedId == rbPinterest.getId()) {
            socmed = "Pinterest";
        } else if (selectedId == rbLinked.getId()) {
            socmed = "Linked";
        } else if (selectedId == rbTwitter.getId()) {
            socmed = "Twitter";
        } else if (selectedId == rbYoutube.getId()) {
            socmed = "Youtube";
        }

        return socmed;
    }
}