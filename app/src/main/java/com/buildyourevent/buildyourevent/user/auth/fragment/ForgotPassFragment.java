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
import com.buildyourevent.buildyourevent.databinding.AuthFragmentForgotPasswordBinding;
import com.buildyourevent.buildyourevent.user.auth.viewmodel.ForgotPassViewModel;

import es.dmoral.toasty.Toasty;

public class ForgotPassFragment extends Fragment
{
    AuthFragmentForgotPasswordBinding binding;
    ForgotPassViewModel viewModel;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentForgotPasswordBinding.inflate(inflater, container, false);

        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);

        viewModel = new ViewModelProvider(this).get(ForgotPassViewModel.class);
        binding.setModel(viewModel);

        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if (integer == 1)
                {
                   // ForgotPassFragmentDirections.ForgotToVerify action = new ForgotPassFragmentDirections.ForgotToVerify();
                   // action.setEmail(binding.etEmail.getText().toString());
                   // navController.navigate(action);
                }
                if (integer == 3)
                {
                    Toasty.error(requireActivity(), "Enter email").show();
                }
                if (integer == 4)
                {
                    Toasty.error(requireActivity(), "invalid email").show();
                }
                else
                {
                    Toasty.error(requireActivity(), "wrong code").show();
                }
            }
        });

        return binding.getRoot();
    }
}
