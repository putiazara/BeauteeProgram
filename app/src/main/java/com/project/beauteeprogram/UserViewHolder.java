package com.project.beauteeprogram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserViewHolder extends RecyclerView.ViewHolder {
    TextView txtNickname;
    TextView txtNamaLengkap;
    TextView txtSocmed;
    private CardView listUser;
    private Context context;
    private List<User> userList;
    private int id;
    private String namaLengkap;
    private String nickname;
    private String email;
    private String domisili;
    private String socmed;
    private String skintype;
    private String rating;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();

        txtNickname = itemView.findViewById(R.id.txtNicknameList);
        txtNamaLengkap = itemView.findViewById(R.id.txtNamaLengkapList);
        txtSocmed = itemView.findViewById(R.id.txtSocmedList);
        listUser = itemView.findViewById(R.id.row_item);

        listUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertAction(context, getAdapterPosition());
            }
        });
    }

    private void alertAction(Context context, int position) {
        String[] option = {"Detail", "Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DatabaseHelper db = new DatabaseHelper(context);
        userList = db.selectUserData();

        id = userList.get(position).getId();
        namaLengkap = userList.get(position).getNamaLengkap();
        nickname = userList.get(position).getNickname();
        email = userList.get(position).getEmail();
        domisili = userList.get(position).getDomisili();
        socmed = userList.get(position).getSocmed();
        skintype = userList.get(position).getSkintype();
        rating = userList.get(position).getRating();

        builder.setTitle("What do you want?");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Intent viewActivity = new Intent(context, ViewActivity.class);
                        viewActivity.putExtra("id", id);
                        viewActivity.putExtra("namaLengkap", namaLengkap);
                        viewActivity.putExtra("nickname", nickname);
                        viewActivity.putExtra("email", email);
                        viewActivity.putExtra("domisili", domisili);
                        viewActivity.putExtra("socmed", socmed);
                        viewActivity.putExtra("skintype", skintype);
                        viewActivity.putExtra("rating", rating);

                        context.startActivity(viewActivity);
                        break;

                    case 1:
                        Intent modifyActivity = new Intent(context, ModifyActivity.class);
                        modifyActivity.putExtra("id", id);
                        modifyActivity.putExtra("namaLengkap", namaLengkap);
                        modifyActivity.putExtra("nickname", nickname);
                        modifyActivity.putExtra("email", email);
                        modifyActivity.putExtra("domisili", domisili);
                        modifyActivity.putExtra("socmed", socmed);
                        modifyActivity.putExtra("skintype", skintype);
                        modifyActivity.putExtra("rating", rating);

                        context.startActivity(modifyActivity);
                        break;

                    case 2:
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.delete(userList.get(position).getId());

                        userList = db.selectUserData();
                        MainActivity.setupRecyclerView(context, userList, MainActivity.recyclerView);

                        Toast.makeText(context, "Data has been deleted successfully", Toast.LENGTH_SHORT);
                        break;
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
