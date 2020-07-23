package com.buildyourevent.buildyourevent.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.buildyourevent.buildyourevent.databinding.FragmentProductDetailsBinding;
import com.buildyourevent.buildyourevent.viewmodel.ProductDetailsViewModel;

public class ProductDataFragment extends Fragment
{
    FragmentProductDetailsBinding binding;
    ProductDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProductDetailsViewModel.class);

        binding.setModel(viewModel);

        binding.buyproductButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), ProductInfoActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}
