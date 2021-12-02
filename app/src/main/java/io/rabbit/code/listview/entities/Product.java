package io.rabbit.code.listview.entities;

import java.io.Serializable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "price")
    private float price;
    @ColumnInfo(name = "quantityInStock")
    private int quantityInStock;
    @ColumnInfo(name = "alertQuantity")
    private int alertQuantity;

    // dataBase variable for products

    public static String TABLE_NAME = "product";

    public static String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
                                                "(" +
                                                    "id INTEGER PRIMARY KEY, "+
                                                    "name VARCHAR(100), "+
                                                    "description  TEXT, "+
                                                    "price REAl default 0, "+
                                                    "quantityInStock INTEGER default 0, "+
                                                    "alertQuantity INTEGER default 0"+
                                                ")";


    public Product(String name, String description, float price, int quantityInStock, int alertQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.alertQuantity = alertQuantity;
    }

    public Product() {
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setAlertQuantity(int alertQuantity) {
        this.alertQuantity = alertQuantity;
    }






    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public int getAlertQuantity() {
        return alertQuantity;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                ", alertQuantity=" + alertQuantity +
                '}';
    }
}
