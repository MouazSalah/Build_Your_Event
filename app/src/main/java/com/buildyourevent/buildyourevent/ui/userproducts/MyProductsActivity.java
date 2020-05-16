package com.buildyourevent.buildyourevent.ui.userproducts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.userproduct.request.RemoveProductRequest;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.RemoveProductResponse;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.UserOwnProductData;
import com.buildyourevent.buildyourevent.model.data.userproduct.response.UserOwnProductResponse;
import com.buildyourevent.buildyourevent.ui.auth.LoginActivity;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProductsActivity extends AppCompatActivity implements UserProductAdapter.onCartItemListener
{
    @BindView(R.id.myproducts_recyclerview) RecyclerView productsRecyclerView;
    @BindView(R.id.emptymyproducts_layout) LinearLayout emptyProductsLayout;
    @BindView(R.id.notlogin_layout) LinearLayout notLoginLayout;
    @BindView(R.id.myproducts_progressBar) ProgressBar progressBar;

    SharedPrefMethods prefMethods;
    UserData userData;
    UserViewModel viewModel;

    UserProductAdapter adapter;
    List<UserOwnProductData> productsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        ButterKnife.bind(this);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userData = prefMethods.getUserData();

        if (userData == null)
        {
            notLoginLayout.setVisibility(View.VISIBLE);
            emptyProductsLayout.setVisibility(View.GONE);
            productsRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
           // cartsCounTextView.setText("0");
        }
        else
        {
            // Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show();
            getUserProducts();
        }
    }

    private void getUserProducts()
    {
        viewModel.showAllOwnProducts(userData.getId(), userData.getToken()).observe(this, new Observer<UserOwnProductResponse>()
        {
            @Override
            public void onChanged(UserOwnProductResponse productResponse)
            {
                if (productResponse.getStatus() == 200)
                {
                    productsList = productResponse.getData();
                    if (productResponse.getData().size() == 0)
                    {
                        progressBar.setVisibility(View.GONE);
                        emptyProductsLayout.setVisibility(View.VISIBLE);
                        productsRecyclerView.setVisibility(View.GONE);
                    }
                    else
                    {
                        buildRecyclerView();
                        progressBar.setVisibility(View.GONE);
                        emptyProductsLayout.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
   }

    private void buildRecyclerView()
    {
        productsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 2);
        productsRecyclerView.setLayoutManager(manager2);

        Log.d(Codes.APP_TAGS, "cards size: " + productsList.size());
        adapter = new UserProductAdapter(this, productsList, this);
        adapter.notifyDataSetChanged();
        productsRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.addnewproduct_button)
    void addNewProduct(View v)
    {
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.addproduct_button)
    void addProduct(View v)
    {
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.loginnow_button)
    void loginNowTask(View v)
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemCartClick(UserOwnProductData userOwnProductData)
    {
        String [] items = {"Delete" , "Edit"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (which == 0)
                {
                    removeProduct(userOwnProductData);
                }
                if (which == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), UpdateProductActivity.class);
                    prefMethods.saveProductId(userOwnProductData.getProductId());
                    startActivity(intent);
                }
            }
        });

        builder.show();
    }

    private void removeProduct(UserOwnProductData userOwnProductData)
    {
        progressBar.setVisibility(View.VISIBLE);
        RemoveProductRequest removeProductRequest = new RemoveProductRequest(userOwnProductData.getProductId(), userData.getId(), userData.getToken());
        viewModel.removeProduct(removeProductRequest).observe(this, new Observer<RemoveProductResponse>() {
            @Override
            public void onChanged(RemoveProductResponse removeProductResponse)
            {
                if (removeProductResponse.getStatus() == 200)
                {
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent(getApplicationContext(), MyProductsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MyProductsActivity.this, "Try Later...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
