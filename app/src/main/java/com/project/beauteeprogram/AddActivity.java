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
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private EditText txtNamaLengkap;
    private String namaLengkap;
    private EditText txtNickname;
    private String nickname;
    private EditText txtEmail;
    private String email;
    private EditText txtDomisili;
    private String domisili;
    private RadioGroup rgSocmed;
    private RadioButton rbIg;
    private RadioButton rbFb;
    private RadioButton rbPinterest;
    private RadioButton rbLinked;
    private RadioButton rbTwitter;
    private RadioButton rbYoutube;
    private String socmed;
    private CheckBox cbNormal;
    private CheckBox cbOily;
    private CheckBox cbDry;
    private CheckBox cbSensitive;
    private CheckBox cbCombination;
    private CheckBox cbLainnya;
    private String skintype;
    private SeekBar seekBar;
    private TextView txtRating;
    private String nilaiRating;
    private Button btnDaftar;

    private DatabaseHelper db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        txtNamaLengkap = findViewById(R.id.input_namalengkap);
        txtNickname = findViewById(R.id.input_nickname);
        txtEmail = findViewById(R.id.input_email);
        txtDomisili = findViewById(R.id.input_domisili);
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
        btnDaftar = findViewById(R.id.daftar);

        db = new DatabaseHelper(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                nilaiRating = String.valueOf(i);
                txtRating.setText("Ambiance to Join Us: " + nilaiRating);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaLengkap = txtNamaLengkap.getText().toString().trim();
                nickname = txtNickname.getText().toString().trim();
                email = txtEmail.getText().toString().trim();
                domisili = txtDomisili.getText().toString().trim();

                socmed = getSocmedSelected();
                skintype = getSkintypeSelected();

                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("REGISTER");
                builder.setMessage(
                        "Verify carefully that all the data submitted are correct!\n\n" +
                                "Full Name: \n" + namaLengkap + "\n\n" +
                                "Username: \n" + nickname + "\n\n" +
                                "Email: \n" + email + "\n\n" +
                                "Domicile: \n" + domisili + "\n\n" +
                                "Social Media: \n" + socmed + "\n\n" +
                                "Skin Type: \n" + skintype + "\n" +
                                "Ambiance to Join Us: \n" + nilaiRating + ""
                );

                builder.setPositiveButton("TRUE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "You have successfully registered!", Toast.LENGTH_SHORT).show();
                        Intent layoutMenu = new Intent(AddActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        layoutMenu.putExtra("status", "add");

                        user = new User();
                        user.setNamaLengkap(namaLengkap);
                        user.setNickname(nickname);
                        user.setEmail(email);
                        user.setDomisili(domisili);
                        user.setSocmed(socmed);
                        user.setSkintype(skintype);
                        user.setRating(nilaiRating);

                        db.insert(user);

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
            socmed = "LinkedIn";
        } else if (selectedId == rbTwitter.getId()) {
            socmed = "Twitter";
        } else if (selectedId == rbYoutube.getId()) {
            socmed = "Youtube";
        }

        return socmed;
    }

}