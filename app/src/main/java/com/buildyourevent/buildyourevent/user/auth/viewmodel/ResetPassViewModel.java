package com.buildyourevent.buildyourevent.user.auth.viewmodel;


import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.buildyourevent.buildyourevent.model.auth.resetpassword.ResetPasswordResponse;
import com.buildyourevent.buildyourevent.user.auth.repository.VerifyRepo;
import com.buildyourevent.buildyourevent.utils.MyApp;

import es.dmoral.toasty.Toasty;

public class ResetPassViewModel extends ViewModel
{
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> confirmPass = new ObservableField<>();
    public MutableLiveData<Integer> mutableLiveData;

    String email;

    VerifyRepo verifyRepo = new VerifyRepo();

    public MutableLiveData<Integer> getMutableLiveData()
    {
        if (mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();
        }

        return mutableLiveData;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void onSaveClicked()
    {
        if (password.get().isEmpty())
        {
            Toasty.error(MyApp.getInstance(), "enter password").show();
        }
        else if (password.get().length() < 6)
        {
            Toasty.error(MyApp.getInstance(), "short password").show();
        }
        else if (confirmPass.get().isEmpty())
        {
            Toasty.error(MyApp.getInstance(), "enter confirm password").show();
        }
        else if (!confirmPass.get().equals(password.get()))
        {
            Toasty.error(MyApp.getInstance(), "enter password").show();
        }
        else
        {
            changePassword();
        }
    }

    private void changePassword()
    {
        verifyRepo.resetPassword(email, password.get()).observeForever(new Observer<ResetPasswordResponse>()
        {
            @Override
            public void onChanged(ResetPasswordResponse resetPasswordResponse)
            {
                if (resetPasswordResponse.getStatus() == 200)
                {
                    mutableLiveData.setValue(1);
                }
                else
                {
                    mutableLiveData.setValue(0);
                }
            }
        });
    }
}
