package com.buildyourevent.buildyourevent.user.auth.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.databinding.AuthFragmentLanguageBinding;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;

import java.util.Locale;
import java.util.Objects;

public class LanguageFragment extends Fragment
{
    AuthFragmentLanguageBinding binding;
    NavController navController ;
    SharedPrefMethods prefMethods;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = AuthFragmentLanguageBinding.inflate(inflater, container, false);

        prefMethods = new SharedPrefMethods(requireActivity());
        navController = Navigation.findNavController(requireActivity(), R.id.auth_navigation);
        binding.btnArabic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeLanguage("ar");
            }
        });

        binding.btnEnglish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeLanguage("en");
            }
        });

        return binding.getRoot();
    }

    public void changeLanguage(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Objects.requireNonNull(requireActivity()).getResources().updateConfiguration(config, requireActivity().getResources().getDisplayMetrics());

        navController.navigate(R.id.language_to_login);
    }
}
