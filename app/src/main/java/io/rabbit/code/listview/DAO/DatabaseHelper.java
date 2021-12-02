package io.rabbit.code.listview.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import io.rabbit.code.listview.entities.Product;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mabase";
    public static int DATABASE_VERSION = 1;
    public static DatabaseHelper databaseHelper;
    private final String TAG = "DATABASE_HELPER";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null , DATABASE_VERSION);
    }

    public static DatabaseHelper getDatabaseInstence(Context context) {

        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Debut de la creation de la table produit");
        db.execSQL(Product.CREATE_TABLE);
        Log.i(TAG, "Fin de la creation de la table produit");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
