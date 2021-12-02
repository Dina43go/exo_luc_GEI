package io.rabbit.code.listview.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.rabbit.code.listview.entities.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class DataBaseRoom extends RoomDatabase {

    private static DataBaseRoom dataBaseRoom;

    public static DataBaseRoom getInstance(Context context) {
        if(dataBaseRoom==null) {
            dataBaseRoom = Room.databaseBuilder(context,
                    DataBaseRoom.class, "mabase2:db").build();
        }
        return dataBaseRoom;
    }
    public abstract ProduitRoomDAO produitRoomDAO();
}
