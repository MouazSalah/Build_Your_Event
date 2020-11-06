package com.buildyourevent.buildyourevent.ui.order;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.FragmentSubcategoryBinding;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.product.ProductsData;
import com.buildyourevent.buildyourevent.model.data.product.ProductsResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.ui.Adapter.ProductsAdapter;
import com.buildyourevent.buildyourevent.ui.Adapter.SubCategoryAdapter;
import com.buildyourevent.buildyourevent.utils.PrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SubDataFragment extends Fragment implements SubCategoryAdapter.onSubCategoryListener
{
    FragmentSubcategoryBinding binding;
    UserViewModel viewModel;
    public ArrayList<Integer> idsList = new ArrayList<>();
    private ArrayList<SubCategoryData> subCategoryList;
    private ArrayList<ProductsData> productsList = new ArrayList<>();
    private SubCategoryAdapter subCategoryAdapter;
    private ProductsAdapter productsAdapter;
    ProgressBar subProgressBar;
    ProgressBar productsProgressBar;
    RecyclerView rvSubCategory;
    RecyclerView rvProducts;
    PrefMethods prefMethods;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentSubcategoryBinding.inflate(inflater, container, false);

        prefMethods = new PrefMethods();

         subProgressBar = binding.subProgressBar;
         productsProgressBar = binding.subproductProgressBar;
         rvSubCategory = binding.subRecyclerview;
         rvProducts = binding.subproductRecyclerview;

        LinearLayout navView = getActivity().findViewById(R.id.toolbar);
        navView.setVisibility(View.GONE);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class); //here is the trick

        viewModel.getAllSubCategories(Codes.categoryId).observe(requireActivity(), new Observer<SubCategoryResponse>()
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
                    subProgressBar.setVisibility(View.GONE);
                    rvSubCategory.setVisibility(View.GONE);
                    binding.emptySubCategory.setVisibility(View.VISIBLE);
                }
            }
        });

        subProgressBar.setVisibility(View.VISIBLE);
        rvSubCategory.setVisibility(View.GONE);

        getAllProducts();

        binding.subControl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (prefMethods.isSmall())
                {
                    prefMethods.setSmall(false);
                    binding.subLayout.setVisibility(View.GONE);
                    binding.subControl.setImageResource(R.drawable.arrow_down);
                }
                else
                {
                    prefMethods.setSmall(true);
                    binding.subControl.setImageResource(R.drawable.arrow_up);
                    binding.subLayout.setVisibility(View.VISIBLE);
                }
                subCategoryAdapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }

    private void setSubCategoryToRecycler()
    {
        SubCategoryData item = new SubCategoryData(1, "", "0" , "ALL" , "null", "null", 1);
        subCategoryList.add(0, item);
        subCategoryAdapter = new SubCategoryAdapter(getActivity(), subCategoryList, this);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getActivity(), 4);
        rvSubCategory.setLayoutManager(manager2);

        rvSubCategory.setAdapter(subCategoryAdapter);
        subProgressBar.setVisibility(View.GONE);
        rvSubCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSubCategoryClick(int position, SubCategoryData data)
    {
        Log.d(Codes.APP_TAGS, "onsubcategory clicked");

        PrefMethods.saveSubCategoryData(subCategoryList.get(position));
        productsProgressBar.setVisibility(View.VISIBLE);
        rvProducts.setVisibility(View.GONE);

        Log.d(Codes.APP_TAGS, "size of list // " + subCategoryList.size());

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
            getProductsIds();
        }

        productsProgressBar.setVisibility(View.VISIBLE);
        rvProducts.setVisibility(View.GONE);
    }

    @Override
    public void onUnSelected(int itemId)
    {
        idsList.remove(Integer.valueOf(itemId));
        getProductsIds();
        productsProgressBar.setVisibility(View.VISIBLE);
        rvProducts.setVisibility(View.GONE);

//        productsAdapter.getFilter().filter("name");
    }

    private void getProductsIds()
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

        Log.d(Codes.APP_TAGS, "ids list // " + allIds);
        Log.d(Codes.APP_TAGS, "subCategories is // " + subCategoryList.toString());
    }

    private void getSelectedProducts(String allIds)
    {
        viewModel.getSelectedProducts(allIds).observe(this, new Observer<ProductsResponse>()
        {
            @Override
            public void onChanged(ProductsResponse response)
            {
                if (response.getStatus().equals("200"))
                {
                    if (response.getData().size() != 0)
                    {
                        productsList = (ArrayList<ProductsData>) response.getData();
                        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        rvProducts.setLayoutManager(manager);
                        rvProducts.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getActivity());
                        productsAdapter.setOldListData(productsList);
                        productsAdapter.updateDataList(productsList);
                        rvProducts.setAdapter(productsAdapter);
                        productsProgressBar.setVisibility(View.GONE);
                        rvProducts.setVisibility(View.VISIBLE);
                        binding.emptyProducts.setVisibility(View.GONE);
                    }
                    else
                    {
                        binding.emptyProducts.setVisibility(View.VISIBLE);
                        productsProgressBar.setVisibility(View.GONE);
                        rvProducts.setVisibility(View.GONE);
                    }
                }
                else
                {
                    binding.emptyProducts.setVisibility(View.VISIBLE);
                    productsProgressBar.setVisibility(View.GONE);
                    rvProducts.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getAllProducts()
    {
        viewModel.getAllProducts().observe(getActivity(), new Observer<ProductsResponse>()
        {
            @Override
            public void onChanged(ProductsResponse productResponse)
            {
                if (productResponse.getStatus().equals("200"))
                {
                    if (productResponse.getData().size() != 0)
                    {
                        productsList = (ArrayList<ProductsData>) productResponse.getData();
                        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        rvProducts.setLayoutManager(manager);
                        rvProducts.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getActivity());
                        productsAdapter.updateDataList(productsList);
                        productsAdapter.setOldListData(productsList);
                        rvProducts.setAdapter(productsAdapter);
                        productsProgressBar.setVisibility(View.GONE);
                        rvProducts.setVisibility(View.VISIBLE);
                        binding.emptyProducts.setVisibility(View.GONE);
                    }
                    else
                    {
                        binding.emptyProducts.setVisibility(View.VISIBLE);
                        productsProgressBar.setVisibility(View.GONE);
                        rvProducts.setVisibility(View.GONE);
                    }
                }
                else
                {
                    binding.emptyProducts.setVisibility(View.VISIBLE);
                    productsProgressBar.setVisibility(View.GONE);
                    rvProducts.setVisibility(View.GONE);
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
