package io.rabbit.code.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.MessageFormat;
import java.util.List;

import io.rabbit.code.listview.entities.Product;

public class ProductAdapter extends BaseAdapter {
    List<Product> produit;
    private Context context;

    public ProductAdapter(Context context , List<Product> produit) {
        this.context = context;
        this.produit = produit;
    }

    @Override
    public int getCount() {
        return produit.size();
    }

    @Override
    public Object getItem(int position) {
        return produit.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(this.context).inflate(R.layout.list_productx , parent ,false);

        // find textview by id

        TextView designation = myView.findViewById(R.id.listProductsDesignation);
        TextView quantite = myView.findViewById(R.id.listProductsQuantite);
        TextView prix = myView.findViewById(R.id.listProductsPrix);

        // set textView

        designation.setText(produit.get(index).getName());
        quantite.setText(MessageFormat.format(String.valueOf(produit.get(index).getQuantityInStock()),"produit"));
        prix.setText(MessageFormat.format(String.valueOf(produit.get(index).getPrice()),"XOF"));
        return myView;
    }
}
