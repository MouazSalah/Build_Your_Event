package com.buildyourevent.buildyourevent.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.buildyourevent.buildyourevent.model.data.product.ProductResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.ui.Adapter.ProductsAdapter;
import com.buildyourevent.buildyourevent.ui.Adapter.SubCategoryAdapter;
import com.buildyourevent.buildyourevent.ui.userproducts.CartsActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubCategoryActivity extends AppCompatActivity implements SubCategoryAdapter.onSubCategoryListener
{
    @BindView(R.id.sub_recyclerview) RecyclerView subCategoryRecyclerView;
    @BindView(R.id.sub_control) ImageView subCategorycontrolBtn;
    @BindView(R.id.products_recyclerview) RecyclerView productsRecyclerView;
    @BindView(R.id.sub_progressBar) ProgressBar subCategoryprogressBar;
    @BindView(R.id.products_progressBar) ProgressBar productsProgressBar;
    @BindView(R.id.sub_layout)
    RelativeLayout subCategoryLayout;
    @BindView(R.id.empty_subCategory)
    LinearLayout emptySubCategoryLayout;
    @BindView(R.id.empty_products)
    LinearLayout emptyProductsLayout;
    @BindView(R.id.subcategory_cartscount) TextView cartsCount;

    private int categoryId;
    UserViewModel viewModel;

    public ArrayList<Integer> idsList = new ArrayList<>();

    private ArrayList<SubCategoryData> subCategoryList;
    private ArrayList<ProductData> productsList = new ArrayList<>();

    private SubCategoryAdapter subCategoryAdapter;
    private ProductsAdapter productsAdapter;
    SharedPrefMethods prefMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getBaseContext().getResources().updateConfiguration(config, this.getBaseContext().getResources().getDisplayMetrics());

        ButterKnife.bind(this);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        categoryId = (from bundle)

        subCategoryprogressBar.setVisibility(View.VISIBLE);
        subCategoryRecyclerView.setVisibility(View.GONE);

        categoryId = Codes.categoryId;

        viewModel.getAllSubCategories(categoryId).observe(this, new Observer<SubCategoryResponse>()
        {
            @Override
            public void onChanged(SubCategoryResponse response)
            {
                subCategoryList = (ArrayList<SubCategoryData>) response.getData();
                if (subCategoryList != null)
                {
                    setSubCategoryToRecycler();
                }
                else
                {
                    subCategoryprogressBar.setVisibility(View.GONE);
                    subCategoryRecyclerView.setVisibility(View.GONE);
                    emptySubCategoryLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(SubCategoryActivity.this, "لا يوجد فئات لها القسم", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getAllProducts();

        UserData userData = prefMethods.getUserData();
        if (userData != null)
        {
            cartsCount.setText("" + prefMethods.getCartsCount());
        }
        else
        {
            cartsCount.setText("0");
        }
    }

    @OnClick(R.id.subcategory_cartlayout)
    void openCarts()
    {
        startActivity(new Intent(getApplicationContext(), CartsActivity.class));
    }


    private void setSubCategoryToRecycler()
    {
        SubCategoryData item = new SubCategoryData(1, "", "0" , "ALL" , "null", "null", 1);
        subCategoryList.add(0, item);
        subCategoryAdapter = new SubCategoryAdapter(this.getApplicationContext(), subCategoryList, this);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 4);
        subCategoryRecyclerView.setLayoutManager(manager2);
      
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);
        subCategoryprogressBar.setVisibility(View.GONE);
        subCategoryRecyclerView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.sub_control)
    void filterLayoutControl(View v)
    {
        if (prefMethods.isSmall())
        {
            Log.e("s", "1st");
            prefMethods.setSmall(false);
            subCategoryLayout.setVisibility(View.GONE);
            subCategorycontrolBtn.setImageResource(R.drawable.arrow_down);
        }
        else
        {
            Log.e("s", "2nd");
            prefMethods.setSmall(true);
            subCategorycontrolBtn.setImageResource(R.drawable.arrow_up);
            subCategoryLayout.setVisibility(View.VISIBLE);
        }

        subCategoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSubCategoryClick(int position, SubCategoryData data)
    {
        Toast.makeText(this, "filter work" , Toast.LENGTH_SHORT).show();

        prefMethods.saveSubCategoryData(subCategoryList.get(position));
        productsProgressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setVisibility(View.GONE);
        getAllProducts();
    }

    @Override
    public void onSelected(int itemId)
    {
        Log.d(Codes.APP_TAGS, "item clicked // " + itemId);
        Log.e("ff", itemId+"" );
        idsList.add(itemId);

        if (itemId == 0)
        {
            getAllProducts();
        }
        else
        {
            getProducts();
        }

        productsProgressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onUnSelected(int itemId)
    {
        idsList.remove(Integer.valueOf(itemId));
        getProducts();
        productsProgressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setVisibility(View.GONE);
    }

    private void getProducts()
    {
        String allIds = removeSpaces();

        if (idsList.size() == 0 )
        {
            getAllProducts();
            allIds = "0" ;
        }
        else
        {
            getSelectedProducts(allIds);
        }

    }

    private void getSelectedProducts(String allIds)
    {
        viewModel.getAllProducts(allIds).observe(this, new Observer<ProductResponse>()
        {
            @Override
            public void onChanged(ProductResponse response)
            {
                Log.d(Codes.APP_TAGS , "selected products / " + response.getStatus() + "size :  " + response.getData().size());
                productsProgressBar.setVisibility(View.GONE);

                if (response.getStatus() == 200)
                {
                    if (response.getData().size() != 0)
                    {
                        productsList = (ArrayList<ProductData>) response.getData();
                        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
                        productsRecyclerView.setLayoutManager(manager);
                        productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getApplicationContext(), productsList);
                        productsRecyclerView.setAdapter(productsAdapter);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        emptyProductsLayout.setVisibility(View.GONE);
                    }
                    else
                    {
                        Toast.makeText(SubCategoryActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        emptyProductsLayout.setVisibility(View.VISIBLE);
                        productsRecyclerView.setVisibility(View.GONE);
                    }
                }
                else
                {
                    emptyProductsLayout.setVisibility(View.VISIBLE);
                    productsRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getAllProducts()
    {
        viewModel.getProducts().observe(this, new Observer<ProductResponse>()
        {
            @Override
            public void onChanged(ProductResponse productResponse)
            {
                Log.d(Codes.APP_TAGS , "all products / " + productResponse.getStatus() + "size :  " + productResponse.getData().size());
                if (productResponse.getStatus() == 200)
                {
                    if (productResponse.getData().size() != 0)
                    {
                        productsList = (ArrayList<ProductData>) productResponse.getData();
                        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
                        productsRecyclerView.setLayoutManager(manager);
                        productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getApplicationContext(), productsList);
                        productsRecyclerView.setAdapter(productsAdapter);
                        productsProgressBar.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        emptyProductsLayout.setVisibility(View.GONE);
                    }
                    else
                    {
                        emptyProductsLayout.setVisibility(View.VISIBLE);
                        productsProgressBar.setVisibility(View.GONE);
                        productsRecyclerView.setVisibility(View.GONE);
                    }
                }
                else
                {
                    emptyProductsLayout.setVisibility(View.VISIBLE);
                    productsProgressBar.setVisibility(View.GONE);
                    productsRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    public String removeSpaces()
    {
        String str = idsList.toString();
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("[\\[\\](){}]","");

        return str;
    }
}
