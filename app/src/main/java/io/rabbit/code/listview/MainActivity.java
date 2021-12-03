package io.rabbit.code.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.rabbit.code.listview.DAO.DataBaseRoom;
import io.rabbit.code.listview.DAO.ProduitDAO;
import io.rabbit.code.listview.DAO.ProduitRoomDAO;
import io.rabbit.code.listview.databinding.ActivityMainBinding;
import io.rabbit.code.listview.entities.Product;
import io.rabbit.code.listview.websevices.ProductWebService;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ProductAdapter adapter;
    Product produit = new Product();

    ProduitRoomDAO produitRoomDAO;

    List<Product> produitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        produitRoomDAO = DataBaseRoom.getInstance(getApplicationContext()).produitRoomDAO();

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , CreateProduct.class);
                intent.putExtra("ss", (Serializable) produitList);
                startActivity(intent);
            }
        });

        binding.listP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product productl = (Product) binding.listP.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this , DetailProduit.class);
                intent.putExtra("detail" , productl);
                startActivity(intent);
            }
        });

        produit = (Product) getIntent().getSerializableExtra("ff");


        if (produit != null) {
            addProduct(produit);
        } else {
            listProduct();
        }

    }

    public void listProduct() {

        Thread thread = new Thread(new Runnable() {
            List<Product> liste = new ArrayList<>();
            ProductWebService productWebService = new ProductWebService();
            @Override
            public void run() {

                Log.i("SERVER____SIDE____CREAT", String.valueOf(productWebService.getProducts()));
                liste.addAll(productWebService.getProducts());
                produitList.addAll(produitRoomDAO.findAll());
                runOnUiThread(()->{

                    if (!liste.isEmpty()) {
                        makeProductListAdapter(liste);
                    } else {
                        makeProductListAdapter(produitList);
                        Toast.makeText(MainActivity.this , "Nous navons pas pu joindre le serveur distant" , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        thread.start();
    }

    public void  addProduct(Product product){
        Thread thread = new Thread(new Runnable() {
            List<Product> liste = new ArrayList<>();
            ProductWebService productWebService = new ProductWebService();
            @Override
            public void run() {

                produitRoomDAO.insert(produit);
                produitList.addAll(produitRoomDAO.findAll());
                Log.i("PRODUIT__ROMM", String.valueOf(productWebService.createProduct(product)));

                Product produit_ =  productWebService.createProduct(product);
                liste.addAll(productWebService.getProducts());
                runOnUiThread(()->{
                    if (produit_ != null) {
                        makeProductListAdapter(liste);
                    } else {
                        makeProductListAdapter(produitList);
                        Toast.makeText(MainActivity.this , "Nous navons pas pu Ajouter un produit au serveur distant" , Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
        thread.start();
    }

    public void makeProductListAdapter (List<Product> produits) {
        adapter = new ProductAdapter(this , produits);
        binding.listP.setAdapter(adapter);
    }

}