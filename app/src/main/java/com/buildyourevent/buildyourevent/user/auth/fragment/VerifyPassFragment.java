package com.buildyourevent.buildyourevent.user.auth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.AuthFragmentVerifyPasswordBinding;
import com.buildyourevent.buildyourevent.user.auth.viewmodel.VerifyPassViewModel;

import es.dmoral.toasty.Toasty;

public class VerifyPassFragment extends Fragment
{
    AuthFragmentVerifyPasswordBinding binding;
    VerifyPassViewModel viewModel;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentVerifyPasswordBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(VerifyPassViewModel.class);
        binding.setModel(viewModel);

        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);

        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if (integer == 1)
                {
                    navController.navigate(R.id.verify_to_reset);
                }
                if (integer == 3)
                {
                    Toasty.success(getActivity(), "Code sent again").show();
                }
                if (integer == 0)
                {
                    Toasty.error(getActivity(), "Code is un correct").show();
                }
            }
        });

        return binding.getRoot();
    }
}
