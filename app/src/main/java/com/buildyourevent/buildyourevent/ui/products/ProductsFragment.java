package com.buildyourevent.buildyourevent.ui.products;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsFragment extends Fragment implements SubCategoryAdapter.onSubCategoryListener
{
    @BindView(R.id.subcategory_recyclerview)
    RecyclerView subCategoryRecyclerView;
    @BindView(R.id.subcategory_layout)
    RelativeLayout subCategoryLayout;
    @BindView(R.id.subcategory_control)
    ImageView subCategorycontrolBtn;
    @BindView(R.id.products_recyclerview) RecyclerView productsRecyclerView;
    @BindView(R.id.subcategory_progressBar)
    ProgressBar subCategoryprogressBar;
    @BindView(R.id.products_progressBar) ProgressBar productsProgressBar;

    private int categoryId;
    UserViewModel viewModel;

    public ArrayList<Integer> idsList = new ArrayList<>();

    private ArrayList<SubCategoryData> subCategoryList;
    private ArrayList<ProductData> productsList = new ArrayList<>();

    private SubCategoryAdapter subCategoryAdapter;
    private ProductsAdapter productsAdapter;
    SharedPrefMethods prefMethods;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        prefMethods = new SharedPrefMethods(getActivity());
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

        View root = inflater.inflate(R.layout.activity_products, container, false);
        ButterKnife.bind(this, root);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        categoryId = prefMethods.getCategoryId();

        subCategoryprogressBar.setVisibility(View.VISIBLE);
        subCategoryRecyclerView.setVisibility(View.GONE);

        viewModel.getAllSubCategories(categoryId).observe(getActivity(), new Observer<List<SubCategoryData>>()
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

        viewModel.getAllProducts("0").observe(getActivity(), new Observer<List<ProductData>>()
        {
            @Override
            public void onChanged(List<ProductData> productData)
            {
                productsList = (ArrayList<ProductData>) productData;
                RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                productsRecyclerView.setLayoutManager(manager);
                productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                productsAdapter = new ProductsAdapter(getActivity(), productsList);
                productsRecyclerView.setAdapter(productsAdapter);
                productsProgressBar.setVisibility(View.GONE);
                productsRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }


    private void setSubCategoryToRecycler()
    {
//        RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 3);
//        subCategoryRecyclerView.setLayoutManager(manager2);
        //  subCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());

        subCategoryAdapter = new SubCategoryAdapter(getActivity().getApplicationContext(), subCategoryList, this);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getActivity(), 4);
        subCategoryRecyclerView.setLayoutManager(manager2);
       /* subCategoryLayout.getLayoutParams().height = 700;
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        subCategoryRecyclerView.startAnimation(anim);
*/
        /*subCategoryAdapter.mutableLiveData.observe(this, o->{
            SubCategoryData item = (SubCategoryData) o;
            if  (item.isSelect()){
                idsList.add(item.getSubCatId());
                Log.e("ss", idsList.toString());
            }else{
                idsList.remove(Integer.valueOf(item.getSubCatId()));
                Log.e("ss", idsList.toString());
            }
        });*/
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);
        subCategoryprogressBar.setVisibility(View.GONE);
        subCategoryRecyclerView.setVisibility(View.VISIBLE);

      /*  if (prefMethods.isSmall())
        {
            prefMethods.setSmall(false);
            RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 4);
            subCategoryRecyclerView.setLayoutManager(manager2);
            subCategoryLayout.getLayoutParams().height = 700;
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            subCategoryRecyclerView.startAnimation(anim);
            //subCategoryLayout.getLayoutParams().height = (int)getResources().getDimension(R.dimen.normal_parent_size_height);

        } else
        {
            Log.e("s", "2nd");
            prefMethods.setSmall(true);
            subCategoryLayout.getLayoutParams().height = 320;
            subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
            subCategoryRecyclerView.startAnimation(anim);
        }*/
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
        /*if (prefMethods.isSmall())
        {
            Log.e("s", "1st");
            prefMethods.setSmall(false);
           RecyclerView.LayoutManager manager2 = new GridLayoutManager(this, 4);
           subCategoryRecyclerView.setLayoutManager(manager2);

           subCategoryLayout.getLayoutParams().height = 700;
           Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
           subCategoryRecyclerView.startAnimation(anim);
           //subCategoryLayout.getLayoutParams().height = (int)getResources().getDimension(R.dimen.normal_parent_size_height);

        }
        else
        {
            Log.e("s", "2nd");
            prefMethods.setSmall(true);
           // subCategoryLayout.getLayoutParams().height = (int)getResources().getDimension(R.dimen.small_parent_size_height);

           subCategoryLayout.getLayoutParams().height = 220;
           subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
           Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
           subCategoryRecyclerView.startAnimation(anim);
        }*/
        //  subCategoryAdapter.notifyDataSetChanged();
//        subCategoryLayout.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
//        refresh recycler


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

        //subCategoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSubCategoryClick(int position, SubCategoryData data)
    {
        Toast.makeText(getActivity(), "filter work" , Toast.LENGTH_SHORT).show();

        Log.d(Codes.APP_TAGS, "onsubcategory clicked");

        prefMethods.saveSubCategoryData(subCategoryList.get(position));
        productsProgressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setVisibility(View.GONE);

        viewModel.getAllProducts( "" + subCategoryList.get(position).getSubCatId()).observe(this, new Observer<List<ProductData>>()
        {
            @Override
            public void onChanged(List<ProductData> productData)
            {
                productsList = (ArrayList<ProductData>) productData;
                RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                productsRecyclerView.setLayoutManager(manager);
                productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                productsAdapter = new ProductsAdapter(getActivity(), productsList);
                productsRecyclerView.setAdapter(productsAdapter);
                productsProgressBar.setVisibility(View.GONE);
                productsRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onSelected(int itemId)
    {
        Log.e("ff", itemId+"" );
        idsList.add(itemId);
        getProducts();
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
            allIds = "0" ;
        }

        Log.d(Codes.APP_TAGS, "ids list // " + allIds);
        Log.d(Codes.APP_TAGS, "subCategories is // " + subCategoryList.toString());

        viewModel.getAllProducts(allIds).observe(this, new Observer<List<ProductData>>() {
            @Override
            public void onChanged(List<ProductData> productData) {

                productsList = (ArrayList<ProductData>) productData;
                RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                productsRecyclerView.setLayoutManager(manager);
                productsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                productsAdapter = new ProductsAdapter(getActivity(), productsList);
                productsRecyclerView.setAdapter(productsAdapter);
                productsProgressBar.setVisibility(View.GONE);
                productsRecyclerView.setVisibility(View.VISIBLE);
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
