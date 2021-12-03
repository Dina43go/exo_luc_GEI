package io.rabbit.code.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import io.rabbit.code.listview.DAO.DataBaseRoom;
import io.rabbit.code.listview.DAO.ProduitDAO;
import io.rabbit.code.listview.DAO.ProduitRoomDAO;
import io.rabbit.code.listview.databinding.ActivityDetailProduitBinding;
import io.rabbit.code.listview.entities.Product;
import io.rabbit.code.listview.websevices.ProductWebService;

public class DetailProduit extends AppCompatActivity {

     ActivityDetailProduitBinding binding;
     Product produit = new Product();
     ProduitDAO produitDAO;

     ProduitRoomDAO produitRoomDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProduitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        produitRoomDAO = DataBaseRoom.getInstance(getApplicationContext()).produitRoomDAO();

        produit = (Product) getIntent().getSerializableExtra("detail");

        Log.i("ID ####", String.valueOf(produit.getId()));

        binding.prodDesignation.setText(" : "+produit.getName());
        binding.prodDescription.setText(" : "+produit.getDescription());
        binding.prodPrix.setText(" : "+ String.valueOf(produit.getPrice()) + "CFA");
        binding.prodQuantite.setText(" : "+String.valueOf(produit.getQuantityInStock()));
        binding.limite.setText(" : "+String.valueOf(produit.getAlertQuantity()));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);

        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuModifier:
                 modifierProduit();
                return true;

            case R.id.menuSuprimer:
                 suprimerProduit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // methode

    public void modifierProduit () {

        Toast.makeText(DetailProduit.this , "modifier produit" ,Toast.LENGTH_LONG)
                .show();
        Intent intent = new Intent(this , SetProduit.class);
        intent.putExtra("setprod",produit);
        startActivity(intent);

    }

    public void suprimerProduit () {

        Thread thread = new Thread(new Runnable() {
            ProductWebService productWebService = new ProductWebService();
            String msg = "";
            @Override
            public void run() {

                if (!productWebService.getProducts().isEmpty()) {
                    productWebService.deleteProduct(produit);
                    msg ="Requette de supression éffectué";
                } else {
                    produitRoomDAO.delete(produit);
                    msg = "produit suprimé";
                }

                runOnUiThread(()->{
                    //Log.i("xxxxxxxx", "Produit a été suprimé");
                    Toast.makeText(DetailProduit.this , msg , Toast.LENGTH_SHORT).show();
                });
            }
        });
        thread.start();

        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }
}