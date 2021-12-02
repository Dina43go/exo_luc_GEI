package io.rabbit.code.listview.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.rabbit.code.listview.entities.Product;

public class ProduitDAO {

    private DatabaseHelper databaseHelper;

    public ProduitDAO (Context context) {
        databaseHelper = DatabaseHelper.getDatabaseInstence(context);
    }

    public Product insert (Product product) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name" , product.getName());
        contentValues.put("description" , product.getDescription());
        contentValues.put("price" , product.getPrice());
        contentValues.put("quantityInStock", product.getQuantityInStock());
        contentValues.put("alertQuantity", product.getAlertQuantity());

        int id = (int) db.insert(Product.TABLE_NAME , null , contentValues);

        product.setId(id);

        return product;
    }


    public Product getOne (int index) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] column = new String[]{"id", "name", "description", "price", "quantityInStock", "alertQuantity"};

        String where = "id=?";
        String[] whereArgs = new String[]{index+""};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(Product.TABLE_NAME, column, where,
                whereArgs, "", "", "");

        if (cursor != null && cursor.moveToNext()) {
            Product product = new Product();
            product.setId(cursor.getInt(Math.max(cursor.getColumnIndex("id") ,0)));
            product.setName(cursor.getString(Math.max(cursor.getColumnIndex("name") ,0)));
            product.setDescription(cursor.getString(Math.max(cursor.getColumnIndex("description"), 0)));
            product.setPrice(cursor.getFloat(Math.max(cursor.getColumnIndex("price"), 0)));
            product.setQuantityInStock(cursor.getInt(Math.max(cursor.getColumnIndex("quantityInStock"), 0)));
            product.setAlertQuantity(cursor.getInt(Math.max(cursor.getColumnIndex("alertQuantity"), 0)));

            cursor.close();

            return product;
        }
        return null;
    }

    public List<Product> AllProduct() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] column = new String[]{"id", "name", "description", "price", "quantityInStock", "alertQuantity"};


        @SuppressLint("Recycle")
        Cursor cursor = db.query(Product.TABLE_NAME, column, "",
                null, "", "", "");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Product product = new Product();
                product.setId(cursor.getInt(Math.max(cursor.getColumnIndex("id") ,0)));
                product.setName(cursor.getString(Math.max(cursor.getColumnIndex("name") ,0)));
                product.setDescription(cursor.getString(Math.max(cursor.getColumnIndex("description"), 0)));
                product.setPrice(cursor.getFloat(Math.max(cursor.getColumnIndex("price"), 0)));
                product.setQuantityInStock(cursor.getInt(Math.max(cursor.getColumnIndex("quantityInStock"), 0)));
                product.setAlertQuantity(cursor.getInt(Math.max(cursor.getColumnIndex("alertQuantity"), 0)));

                productList.add(product);
            }
            cursor.close();
        }
        return productList;
    }

    public Product update(int id, Product product){

        return null;
    }

    public boolean delete(int id){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowDelete = db.delete(Product.TABLE_NAME, "id=?", new String[]{""+id});
        return rowDelete>0;
    }


}
