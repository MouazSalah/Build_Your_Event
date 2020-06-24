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
import com.buildyourevent.buildyourevent.databinding.AuthFragmentResetPasswordBinding;
import com.buildyourevent.buildyourevent.user.auth.viewmodel.ResetPassViewModel;

import es.dmoral.toasty.Toasty;

public class ResetPasswordFragment extends Fragment
{
    AuthFragmentResetPasswordBinding binding;
    ResetPassViewModel viewModel;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentResetPasswordBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ResetPassViewModel.class);
        binding.setModel(viewModel);

        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);


        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if (integer == 1)
                {
                    navController.navigate(R.id.forgot_to_verify);
                    Toasty.success(requireActivity(), "code Sent Successfully").show();
                }
                else
                {
                    Toasty.error(requireActivity(),"try again" ).show();
                }
            }
        });

        return binding.getRoot();
    }
}


