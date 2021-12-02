package io.rabbit.code.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import io.rabbit.code.listview.databinding.ActivityCreateProductBinding;
import io.rabbit.code.listview.entities.Product;

public class CreateProduct extends AppCompatActivity {

    Product produit = new Product();
    ActivityCreateProductBinding binding;
    //List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct(binding);
            }
        });

    }

    public void saveProduct(ActivityCreateProductBinding binding) {

        produit.setName(attestField(binding.name , "Veillez saisir le nom du produit"));
        produit.setDescription(attestField(binding.description , "entrez la description du produit"));
        produit.setPrice((float) Double.parseDouble(attestField(binding.price , "veillez saisir le price")));
        produit.setQuantityInStock(Integer.parseInt(attestField(binding.quantite , "veillez saisir la quantite de produit")));
        produit.setAlertQuantity(Integer.parseInt(attestField(binding.limite , "donnez la limite")));

        final String i1 = binding.name.getText().toString();
        final String i2 = binding.description.getText().toString();
        final String i3 = binding.price.getText().toString();
        final String i4 = binding.quantite.getText().toString();
        final String i5 = binding.limite.getText().toString();

        if (!(i1.isEmpty() || i2.isEmpty() || i3.isEmpty() || i4.isEmpty() || i5.isEmpty())) {

            Intent intent = new Intent(CreateProduct.this , MainActivity.class);

            intent.putExtra("ff" , produit);
            startActivity(intent);

        } else {
            Snackbar snackbar = Snackbar
                    .make(binding.getRoot(), "tous les champs doivent etre remplis", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    public static String attestField (EditText v , String err) {
        if (v.getText().toString().isEmpty()) {
            v.setError(err);
            return "0";
        } else return v.getText().toString();
    }

    public static void attestFieldS (EditText v , String err) {
        if (v.getText().toString().isEmpty()) {
            v.setError(err);
        }
    }
}