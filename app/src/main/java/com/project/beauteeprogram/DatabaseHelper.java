package com.project.beauteeprogram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "BeauteeProgram";
    private static final String TABLE_NAME = "tbl_user";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAMALENGKAP = "nama_lengkap";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DOMISILI = "domisili";
    private static final String KEY_SOCMED = "socmed";
    private static final String KEY_SKINTYPE = "skintype";
    private static final String KEY_RATING = "rating";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAMALENGKAP + " TEXT, " +
                KEY_NICKNAME + " TEXT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_DOMISILI + " TEXT, " +
                KEY_SOCMED + " TEXT, " +
                KEY_SKINTYPE + " TEXT, " +
                KEY_RATING + " TEXT " + ")";
        sqLiteDatabase.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;

        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert (User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAMALENGKAP, user.getNamaLengkap());
        values.put(KEY_NICKNAME, user.getNickname());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_DOMISILI, user.getDomisili());
        values.put(KEY_SOCMED, user.getSocmed());
        values.put(KEY_SKINTYPE, user.getSkintype());
        values.put(KEY_RATING, user.getRating());

        db.insert(TABLE_NAME, null, values);
    }

    public List<User> selectUserData() {
        ArrayList<User> users = new ArrayList<User>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_ID, KEY_NAMALENGKAP, KEY_NICKNAME, KEY_EMAIL, KEY_DOMISILI, KEY_SOCMED, KEY_SKINTYPE, KEY_RATING};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String namaLengkap = cursor.getString(1);
            String nickname = cursor.getString(2);
            String email = cursor.getString(3);
            String domisili = cursor.getString(4);
            String socmed = cursor.getString(5);
            String skintype = cursor.getString(6);
            String rating = cursor.getString(7);

            User user = new User();
            user.setId(id);
            user.setNamaLengkap(namaLengkap);
            user.setNickname(nickname);
            user.setEmail(email);
            user.setDomisili(domisili);
            user.setSocmed(socmed);
            user.setSkintype(skintype);
            user.setRating(rating);

            users.add(user);
        }

        return users;
    }

    public void update(User user) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAMALENGKAP, user.getNamaLengkap());
        values.put(KEY_NICKNAME, user.getNickname());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_DOMISILI, user.getDomisili());
        values.put(KEY_SOCMED, user.getSocmed());
        values.put(KEY_SKINTYPE, user.getSkintype());
        values.put(KEY_RATING, user.getRating());

        String whereClause = KEY_ID + " = '" + user.getId() + "'";

        db.update(TABLE_NAME, values, whereClause, null);
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = KEY_ID + " = '" + id + "'";

        db.delete(TABLE_NAME, whereClause, null);
    }
}
