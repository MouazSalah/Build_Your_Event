package com.buildyourevent.buildyourevent.ui.user.subcategory;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.Toast;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.FragmentSubDataBinding;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.product.ProductData;
import com.buildyourevent.buildyourevent.model.data.product.ProductResponse;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryData;
import com.buildyourevent.buildyourevent.model.data.subcategory.SubCategoryResponse;
import com.buildyourevent.buildyourevent.ui.cardactivity.CartsActivity;
import com.buildyourevent.buildyourevent.ui.products.ProductsAdapter;
import com.buildyourevent.buildyourevent.ui.products.SubCategoryActivity;
import com.buildyourevent.buildyourevent.ui.products.SubCategoryAdapter;
import com.buildyourevent.buildyourevent.utils.PrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import java.util.ArrayList;
import butterknife.OnClick;

public class SubDataFragment extends Fragment implements SubCategoryAdapter.onSubCategoryListener
{
    FragmentSubDataBinding binding;
    UserViewModel viewModel;

    private SubDataViewModel mViewModel;

    public ArrayList<Integer> idsList = new ArrayList<>();
    private ArrayList<SubCategoryData> subCategoryList;
    private ArrayList<ProductData> productsList = new ArrayList<>();

    private SubCategoryAdapter subCategoryAdapter;
    private ProductsAdapter productsAdapter;
    
    ProgressBar subProgressBar = binding.subProgressBar;
    ProgressBar productsProgressBar = binding.productProgressBar;

    RecyclerView rvSubCategory = binding.rvSubcategory;
    RecyclerView rvProducts = binding.rvProducts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentSubDataBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class); //here is the trick

        viewModel.getAllSubCategories(PrefMethods.getInstance().getCategoryId()).observe(requireActivity(), new Observer<SubCategoryResponse>()
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
                    Toast.makeText(getActivity(), R.string.no_subcategory, Toast.LENGTH_SHORT).show();
                }
            }
        });

        subProgressBar.setVisibility(View.VISIBLE);
        binding.rvSubcategory.setVisibility(View.GONE);

        getAllProducts();

        return binding.getRoot();
    }

    private void setSubCategoryToRecycler()
    {
        SubCategoryData item = new SubCategoryData(1, "", "0" , "ALL" , "null", "null", 1);
        subCategoryList.add(0, item);
        subCategoryAdapter = new SubCategoryAdapter(getActivity(), subCategoryList, this);
        RecyclerView.LayoutManager manager2 = new GridLayoutManager(getActivity(), 4);
        binding.rvSubcategory.setLayoutManager(manager2);

        rvSubCategory.setAdapter(subCategoryAdapter);
        subProgressBar.setVisibility(View.GONE);
        rvSubCategory.setVisibility(View.VISIBLE);
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

        Log.d(Codes.APP_TAGS, "ids list // " + allIds);
        Log.d(Codes.APP_TAGS, "subCategories is // " + subCategoryList.toString());
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
                        rvProducts.setLayoutManager(manager);
                        rvProducts.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getActivity(), productsList);
                        rvProducts.setAdapter(productsAdapter);
                        productsProgressBar.setVisibility(View.GONE);
                        rvProducts.setVisibility(View.VISIBLE);
                        binding.emptyProducts.setVisibility(View.GONE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
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
                        rvProducts.setLayoutManager(manager);
                        rvProducts.setItemAnimator(new DefaultItemAnimator());
                        productsAdapter = new ProductsAdapter(getActivity(), productsList);
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



    @OnClick(R.id.sub_control)
    void filterLayoutControl(View v)
    {
        if (PrefMethods.getInstance().isSmall())
        {
            Log.e("s", "1st");
            PrefMethods.getInstance().setSmall(false);
            binding.subCategoryLayout.setVisibility(View.GONE);
            binding.subControl.setImageResource(R.drawable.arrow_down);
        }
        else
        {
            Log.e("s", "2nd");
            PrefMethods.getInstance().setSmall(true);
            binding.subControl.setImageResource(R.drawable.arrow_up);
            binding.subCategoryLayout.setVisibility(View.VISIBLE);
        }

        subCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSubCategoryClick(int position, SubCategoryData data)
    {
        Toast.makeText(getActivity(), "filter work" , Toast.LENGTH_SHORT).show();
        Log.d(Codes.APP_TAGS, "onsubcategory clicked");

        PrefMethods.getInstance().saveSubCategoryData(subCategoryList.get(position));
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
            getProducts();
        }

        productsProgressBar.setVisibility(View.VISIBLE);
        rvProducts.setVisibility(View.GONE);
    }

    @Override
    public void onUnSelected(int itemId)
    {
        idsList.remove(Integer.valueOf(itemId));
        getProducts();
        productsProgressBar.setVisibility(View.VISIBLE);
        rvProducts.setVisibility(View.GONE);
    }


    @OnClick(R.id.subcategory_cartlayout)
    void openCarts()
    {
        startActivity(new Intent(getActivity(), CartsActivity.class));
    }


    public String removeSpaces()
    {
        String str = idsList.toString();
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("[\\[\\](){}]","");

        return str;
    }
}
