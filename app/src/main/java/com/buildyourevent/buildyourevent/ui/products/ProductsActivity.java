package com.buildyourevent.buildyourevent.ui.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.hzn.lib.EasyTransition;
import com.hzn.lib.EasyTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsActivity extends AppCompatActivity  implements SubCategoryAdapter.onSubCategoryListener
{
    @BindView(R.id.subcategory_recyclerview) RecyclerView subCategoryRecyclerView;
    @BindView(R.id.subcategory_layout) RelativeLayout subCategoryLayout;
    @BindView(R.id.subcategory_control) ImageView subCategorycontrolBtn;
    @BindView(R.id.products_recyclerview) RecyclerView productsRecyclerView;
    @BindView(R.id.subcategory_progressBar) ProgressBar subCategoryprogressBar;
    @BindView(R.id.products_progressBar) ProgressBar productsProgressBar;

    private int categoryId;
    UserViewModel viewModel;

    Boolean isItemSelected = false;

    private ArrayList<SubCategoryData> subCategoryList;
    private ArrayList<ProductData> productsList = new ArrayList<>();

    private SubCategoryAdapter subCategoryAdapter;
    private ProductsAdapter productsAdapter;
    SharedPrefMethods prefMethods;

    List<Integer> selectedIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        categoryId = getIntent().getIntExtra(Codes.CATEGORY_ID, 0);
        prefMethods = new SharedPrefMethods(this);

        subCategoryprogressBar.setVisibility(View.VISIBLE);
        subCategoryRecyclerView.setVisibility(View.GONE);

        viewModel.getAllSubCategories(categoryId).observe(this, new Observer<List<SubCategoryData>>()
        {
            @Override
            public void onChanged(List<SubCategoryData> subCategoryData)
            {
                subCategoryList = (ArrayList<SubCategoryData>) subCategoryData;
                if (subCategoryList != null)
                {
                    setSubCategoryToRecycler();
                }
            }
        });

        viewModel.getAllProducts(0).observe(this, new Observer<List<ProductData>>()
        {
            @Override
            public void onChanged(List<ProductData> productData)
            {
                productsList = (ArrayList<ProductData>) productData;
                RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
                productsRecyclerView.setLayoutManager(manager);
                productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                productsAdapter = new ProductsAdapter(getApplicationContext(), productsList);
                productsRecyclerView.setAdapter(productsAdapter);
                productsProgressBar.setVisibility(View.GONE);
                productsRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setSubCategoryToRecycler()
    {
//        RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 3);
//        subCategoryRecyclerView.setLayoutManager(manager2);
        subCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());

        subCategoryAdapter = new SubCategoryAdapter(this, subCategoryList, this);
        subCategoryAdapter.mutableLiveData.observe(this, o->{
            SubCategoryData item = (SubCategoryData) o;
            if  (item.isSelect()){
                idsList.add(item.getSubCatId());
                Log.e("ss", idsList.toString());
            }else{
                idsList.remove(Integer.valueOf(item.getSubCatId()));
                Log.e("ss", idsList.toString());
            }
        });
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);
        subCategoryprogressBar.setVisibility(View.GONE);
        subCategoryRecyclerView.setVisibility(View.VISIBLE);

//        if (prefMethods.isSmall()) {
//            Log.e("s", "1st");
//            prefMethods.setSmall(false);
//            subCategoryLayout.getLayoutParams().height = 700;
////            RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 4);
////            subCategoryRecyclerView.setLayoutManager(manager2);
//
//        } else
//        {
//            Log.e("s", "2nd");
//            prefMethods.setSmall(true);
//            subCategoryLayout.getLayoutParams().height = 300;
////            subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
////
////            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
////            subCategoryRecyclerView.startAnimation(anim);
//        }
    }


    @OnClick(R.id.subcategory_control)
    void filterLayoutControl(View v)
    {

       /* final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (200 * scale + 0.5f);

        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, pixels);
        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);*/
        //scrollpopap.setLayoutParams(rel_btn);
       // scrollpopap.setScrollingEnabled(false);
        /*if (isLayoutVisible == false)
        {
            subCategoryLayout.setVisibility(View.VISIBLE);
            subCategorycontrolBtn.setImageResource(R.drawable.arrow_up);
            isLayoutVisible = true;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) subCategoryLayout.getLayoutParams();
            // Changes the height and width to the specified *pixels*
            params.height = 400;
            params.width = 100;
            subCategoryLayout.setLayoutParams(params);
        }
        else
        {
            subCategoryLayout.setVisibility(View.GONE);
            subCategorycontrolBtn.setImageResource(R.drawable.arrow_down);
            isLayoutVisible = false;
        }*/
        if (prefMethods.isSmall())
        {
            Log.e("s", "1st");
            prefMethods.setSmall(false);
//            RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 4);
//            subCategoryRecyclerView.setLayoutManager(manager2);
//
//
//            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//            subCategoryRecyclerView.startAnimation(anim);
            subCategoryLayout.getLayoutParams().height = (int)getResources().getDimension(R.dimen.normal_parent_size_height);

        } else
        {
            Log.e("s", "2nd");
            prefMethods.setSmall(true);
            subCategoryLayout.getLayoutParams().height = (int)getResources().getDimension(R.dimen.small_parent_size_height);

//            subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//
//            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
//            subCategoryRecyclerView.startAnimation(anim);
        }
        subCategoryAdapter.notifyDataSetChanged();
//        subCategoryLayout.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
//        refresh recycler

    }
    public ArrayList<Integer> idsList = new ArrayList<>();

    @Override
    public void onSubCategoryClick(int position, SubCategoryData data)
    {
//        Log.d(Codes.APP_TAGS, "onsubcategory clicked");
//
//        for (int i =0; i <subCategoryList.size(); i ++)
//        {
//            if (subCategoryList.get(i).isSelect())
//            {
//                selectedIds.add(subCategoryList.get(position).getSubCatId());
//                Log.d(Codes.APP_TAGS, "this id // " + subCategoryList.get(position).getSubCatId());
//            }
//        }
//
//        String allIds = removeSpaces();
//        Log.d(Codes.APP_TAGS, "selected Items / " + allIds);
//        removeSpaces();
//
//        productsProgressBar.setVisibility(View.VISIBLE);
//        productsRecyclerView.setVisibility(View.GONE);
//
//        viewModel.getAllProducts(subCategoryList.get(position).getSubCatId()).observe(this, new Observer<List<ProductData>>()
//        {
//            @Override
//            public void onChanged(List<ProductData> productData)
//            {
//                productsList = (ArrayList<ProductData>) productData;
//                RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
//                productsRecyclerView.setLayoutManager(manager);
//                productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
//                productsAdapter = new ProductsAdapter(getApplicationContext(), productsList);
//                productsRecyclerView.setAdapter(productsAdapter);
//                productsProgressBar.setVisibility(View.GONE);
//                productsRecyclerView.setVisibility(View.VISIBLE);
//            }
//        });
//
//        prefMethods.saveSubCategoryData(subCategoryList.get(position));
    }
    @Override
    public void onSelected(int itemId) {
        idsList.add(itemId);
        Log.e("ss", idsList.toString() );
    }

    @Override
    public void onUnSelected(int itemId) {

        idsList.remove(Integer.valueOf(itemId));
        Log.e("ss", idsList.toString());
    }


    public String removeSpaces()
    {
        String str = selectedIds.toString();
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("[\\[\\](){}]","");


        return str;
    }
}
