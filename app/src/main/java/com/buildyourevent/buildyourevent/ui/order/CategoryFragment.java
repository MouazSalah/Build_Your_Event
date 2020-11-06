package com.buildyourevent.buildyourevent.ui.order;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.data.banner.BannerData;
import com.buildyourevent.buildyourevent.model.data.category.CategoryData;
import com.buildyourevent.buildyourevent.model.data.category.CategoryResponse;
import com.buildyourevent.buildyourevent.ui.Adapter.CategoryAdapter;
import com.buildyourevent.buildyourevent.ui.Adapter.SliderImageAdapter;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment
{
    private UserViewModel viewModel;
    private ArrayList<CategoryData> categoriesList;
    private ArrayList<BannerData> bannersList;

    @BindView(R.id.categories_progressBar) ProgressBar progressBar;
    @BindView(R.id.categories_recyclerview) RecyclerView categoryRecyclerView;
    @BindView(R.id.imageSlider) SliderView sliderView;

    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        progressBar.setVisibility(View.VISIBLE);
        categoryRecyclerView.setVisibility(View.GONE);

        viewModel.getAllBanners().observe(getActivity(), new Observer<List<BannerData>>()
        {
            @Override
            public void onChanged(List<BannerData> bannerItems)
            {
                bannersList = (ArrayList<BannerData>) bannerItems;
                if (bannersList != null)
                {
                    setSlider();
                }
            }
        });

        viewModel.getAllCategories().observe(getActivity(), categoryResponse -> {
            categoriesList = (ArrayList<CategoryData>) categoryResponse.getData();
            if (categoriesList.size() != 0)
            {
                setRecyclerView();
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "لا يوجد اقسام", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void setSlider()
    {
        final SliderImageAdapter adapter = new SliderImageAdapter(bannersList, getActivity());
        adapter.setCount(bannersList.size());

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener()
        {
            @Override
            public void onIndicatorClicked(int position)
            {
                sliderView.setCurrentPagePosition(position);
            }
        });
    }


    private void setRecyclerView()
    {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 4);
        categoryRecyclerView.setLayoutManager(manager);

        categoryAdapter = new CategoryAdapter(getContext(), categoriesList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);
        categoryRecyclerView.setVisibility(View.VISIBLE);

    }
}