package com.buildyourevent.buildyourevent.user.auth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.AuthFragmentLoginBinding;
import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.user.auth.viewmodel.LoginMVVM;
import com.buildyourevent.buildyourevent.user.home.UserActivity;
import com.buildyourevent.buildyourevent.utils.PrefMethods;
import com.muddzdev.styleabletoast.StyleableToast;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class LoginFragment extends Fragment
{
    AuthFragmentLoginBinding binding;
    NavController navController ;
    LoginMVVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentLoginBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(LoginMVVM.class);
        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);

        binding.setLifecycleOwner(this);

        binding.setModel(viewModel);

        viewModel.getUser().observe(requireActivity(), new Observer<LoginRequest>()
        {
            @Override
            public void onChanged(@Nullable LoginRequest loginUser)
            {
                validateInputs(loginUser);
            }
        });

        viewModel.getSkipValue().observe(requireActivity(), new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                if (integer == 1)
                {
                    navController.navigate(R.id.login_to_skip);
                }
                if (integer == 2)
                {
                    navController.navigate(R.id.login_to_password);
                }
                if (integer == 3)
                {
                    navController.navigate(R.id.login_to_register);
                }
            }
        });

        return binding.getRoot();
    }

    private void validateInputs(LoginRequest loginUser)
    {
        if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getEmail()))
        {
            StyleableToast.makeText(requireActivity(), "Enter an E-Mail Address!", R.style.mytoast).show();

            binding.etLoginEmail.setError("Enter an E-Mail Address");
            binding.etLoginEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(loginUser.getEmail()).matches())
        {
            binding.etLoginEmail.setError("Enter a Valid E-mail Address");
            binding.etLoginEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPassword()))
        {
            binding.etLoginPassword.setError("Enter a Password");
            binding.etLoginPassword.requestFocus();
        }
        else if (loginUser.getPassword().length() < 6)
        {
            binding.etLoginPassword.setError("Enter at least 6 Digit password");
            binding.etLoginPassword.requestFocus();
        }
        else
        {
            binding.etLoginEmail.setText(loginUser.getEmail());
            binding.etLoginPassword.setText(loginUser.getPassword());
            signin(loginUser);
        }
    }

    private void signin(LoginRequest loginUser)
    {
        viewModel.loginCurrentUser(new LoginRequest(loginUser.getEmail(), loginUser.getPassword()))
                                   .observe(requireActivity(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse)
            {
                if (loginResponse.getStatus() == 200)
                {
                    Toasty.success(getActivity(), "success").show();
                    new PrefMethods().SaveUserData(loginResponse.getUserData());
                    startActivity(new Intent(getActivity(), UserActivity.class));
                    requireActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}