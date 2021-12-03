package io.rabbit.code.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import io.rabbit.code.listview.DAO.DataBaseRoom;
import io.rabbit.code.listview.DAO.ProduitRoomDAO;
import io.rabbit.code.listview.databinding.ActivitySetProduitBinding;
import io.rabbit.code.listview.entities.Product;
import io.rabbit.code.listview.websevices.ProductWebService;

public class SetProduit extends AppCompatActivity {

    ActivitySetProduitBinding binding;
    ProduitRoomDAO produitRoomDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetProduitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        produitRoomDAO = DataBaseRoom.getInstance(getApplicationContext()).produitRoomDAO();

        Product produit = (Product) getIntent().getSerializableExtra("setprod");

        binding.setProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateProduct.attestFieldS(binding.name , "saisissez le nom du produit");
                CreateProduct.attestFieldS(binding.description , "saisissez le champ");
                CreateProduct.attestFieldS(binding.price , "saisissez le champ");
                CreateProduct.attestFieldS(binding.quantite , "saisissez le champ");
                CreateProduct.attestFieldS(binding.limite , "saisissez le champ");

                final String i1 = binding.name.getText().toString();
                final String i2 = binding.description.getText().toString();
                final String i3 = binding.price.getText().toString();
                final String i4 = binding.quantite.getText().toString();
                final String i5 = binding.limite.getText().toString();

                if (!(i1.isEmpty() || i2.isEmpty() || i3.isEmpty() || i4.isEmpty() || i5.isEmpty())) {

                    produit.setName(i1);
                    produit.setDescription(i2);
                    produit.setPrice(Float.parseFloat(i3));
                    produit.setQuantityInStock(Integer.parseInt(i4));
                    produit.setQuantityInStock(Integer.parseInt(i5));

                    Thread thread = new Thread(new Runnable() {
                        ProductWebService productWebService = new ProductWebService();
                        String msg = "";
                        @Override
                        public void run() {

                            if (!productWebService.getProducts().isEmpty()) {
                                productWebService.updateProduct(produit);
                                msg ="Requette de modification éffectué";
                            } else {
                                produitRoomDAO.update(produit);
                                msg = "produit modifié";
                            }

                            produitRoomDAO.update(produit);
                            runOnUiThread(()->{
                                Toast.makeText(SetProduit.this , msg , Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                    thread.start();
                    Intent intent = new Intent(SetProduit.this , MainActivity.class);
                    startActivity(intent);

                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.getRoot(), "tous les champs doivent etre rempli pour modifier le chant", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
}