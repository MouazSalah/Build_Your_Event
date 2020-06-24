package com.buildyourevent.buildyourevent.user.home.subcategory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.HomeFragmentSubcategoryBinding;
import com.buildyourevent.buildyourevent.databinding.RawSubcategoryBinding;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.buildyourevent.buildyourevent.model.data.product.ProductResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.ui.products.ProductsAdapter;
import com.buildyourevent.buildyourevent.utils.PrefMethods;

import java.util.ArrayList;

public class SubCategoryFragment extends Fragment
{
    HomeFragmentSubcategoryBinding binding;
    SubCategoryViewModel viewModel;
    NavController navController;

    public ArrayList<Integer> idsList = new ArrayList<>();

    private ArrayList<SubCategoryData> subCategoryList;
    private ArrayList<ProductData> productsList = new ArrayList<>();

    private SubCatAdapter subCategoryAdapter;
    private ProductsAdapter productsAdapter;

    ProgressBar subProgress = binding.subProgressBar;
    RecyclerView subRecycler = binding.subRecyclerview;
    ProgressBar productsProgress = binding.productProgressBar;
    RecyclerView productsRecycler = binding.productRecyclerview;

    PrefMethods prefMethods;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = HomeFragmentSubcategoryBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SubCategoryViewModel.class);

        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);


        prefMethods = new PrefMethods();

        binding.subProgressBar.setVisibility(View.VISIBLE);
        binding.subRecyclerview.setVisibility(View.GONE);

        int categoryId = 10;

        RawSubcategoryBinding rawBinding = RawSubcategoryBinding.inflate(inflater, container, false);

        viewModel.getMutableLiveData().observe(getActivity(), new Observer<Object>()
        {
            @Override
            public void onChanged(Object o)
            {
                if (o instanceof SubCategoryData)
                {
                    SubCategoryData subCategoryData = (SubCategoryData) o;
                    if (subCategoryData.isSelect() == true)
                    {
                        idsList.add(subCategoryData.getId());

                        if (subCategoryData.getId() == 0)
                        {
                            getAllProducts();
                        }
                        else
                        {
                            getProductsIds();
                        }

                        productsProgress.setVisibility(View.VISIBLE);
                        productsRecycler.setVisibility(View.GONE);

                        rawBinding.itemfilterSelectedImageview.setImageResource(R.drawable.filter_background);
                    }
                    else
                    {
                        rawBinding.itemfilterSelectedImageview.setImageResource(R.drawable.icon_selected_foreground);
                    }
                }
            }
        });


        viewModel.getAllSubCategories(categoryId).observe(getActivity(), new Observer<SubCategoryResponse>()
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
                    subProgress.setVisibility(View.GONE);
                    subRecycler.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "لا يوجد فئات لها القسم", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getAllProducts();


        binding.subControl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (prefMethods.isSmall())
                {
                    Log.e("s", "1st");
                    prefMethods.setSmall(false);
                    binding.subLayout.setVisibility(View.GONE);
                    binding.subControl.setImageResource(R.drawable.arrow_down);
                }
                else
                {
                    Log.e("s", "2nd");
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
        subCategoryAdapter = new SubCatAdapter();
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getActivity(), 4);
        subRecycler.setLayoutManager(manager2);

        subRecycler.setAdapter(subCategoryAdapter);
        subProgress.setVisibility(View.GONE);
        subRecycler.setVisibility(View.VISIBLE);
    }


  /*  @OnClick(R.id.sub_control)
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
    }*/

/*

    @Override
    public void onSubCategoryClick(int position, SubCategoryData data)
    {
        Toast.makeText(getActivity(), "filter work" , Toast.LENGTH_SHORT).show();
        Log.d(Codes.APP_TAGS, "onsubcategory clicked");

        prefMethods.saveSubCategoryData(subCategoryList.get(position));
        productsProgressBar.setVisibility(View.VISIBLE);
        productsRecyclerView.setVisibility(View.GONE);

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
*/

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
    }

    private void getSelectedProducts(String allIds)
    {
        viewModel.getAllProducts(allIds).observe(this, new Observer<ProductResponse>()
        {
            @Override
            public void onChanged(ProductResponse response)
            {
                if (response.getStatus() == 200)
                {
                    if (response.getData().size() != 0)
                    {
                        productsList = (ArrayList<ProductData>) response.getData();
                        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        productsRecycler.setLayoutManager(manager);
                        productsRecycler.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getActivity(), productsList);
                        productsRecycler.setAdapter(productsAdapter);
                        productsProgress.setVisibility(View.GONE);
                        productsRecycler.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        productsProgress.setVisibility(View.GONE);
                        productsRecycler.setVisibility(View.GONE);
                    }
                }
                else
                {
                    productsProgress.setVisibility(View.GONE);
                    productsRecycler.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getAllProducts()
    {
        viewModel.getProducts().observe(getActivity(), new Observer<ProductResponse>()
        {
            @Override
            public void onChanged(ProductResponse productResponse)
            {
                if (productResponse.getStatus() == 200)
                {
                    if (productResponse.getData().size() != 0)
                    {
                        productsList = (ArrayList<ProductData>) productResponse.getData();
                        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                        productsRecycler.setLayoutManager(manager);
                        productsRecycler.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getActivity(), productsList);
                        productsRecycler.setAdapter(productsAdapter);
                        productsProgress.setVisibility(View.GONE);
                        productsRecycler.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        productsProgress.setVisibility(View.GONE);
                        productsRecycler.setVisibility(View.GONE);
                    }
                }
                else
                {
                    productsProgress.setVisibility(View.GONE);
                    productsRecycler.setVisibility(View.GONE);
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
