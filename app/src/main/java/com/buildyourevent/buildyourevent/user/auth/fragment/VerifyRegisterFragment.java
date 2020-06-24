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
import com.buildyourevent.buildyourevent.databinding.AuthFragmentVerifyRegisterBinding;
import com.buildyourevent.buildyourevent.user.auth.viewmodel.VerifyRegisterViewModel;

import es.dmoral.toasty.Toasty;

public class VerifyRegisterFragment extends Fragment
{
    AuthFragmentVerifyRegisterBinding binding;
    VerifyRegisterViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentVerifyRegisterBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(VerifyRegisterViewModel.class);
        binding.setModel(viewModel);

        viewModel.getMutableLiveData().observe(requireActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if (integer == 1)
                {
                    Toasty.success(requireActivity(), "Password Changed Successfully").show();
                   // getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
                }
                else if (integer == 3)
                {
                    Toasty.error(requireActivity(),"wrong code" ).show();
                }
                else
                {
                    Toasty.error(getActivity(), "Try again").show();
                }
            }
        });

        return binding.getRoot();
    }
}


