package com.buildyourevent.buildyourevent.user.auth.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.model.auth.code.VerifyCodeResponse;
import com.buildyourevent.buildyourevent.user.auth.repository.VerifyRepo;

public class VerifyRegisterViewModel extends ViewModel
{
    ObservableField<String> email = new ObservableField<>();
    public ObservableField<Integer> code = new ObservableField<>();

    MutableLiveData<Integer> mutableLiveData;
    VerifyRepo verifyRepo = new VerifyRepo();

    public void setEmail(String email)
    {

    }

    public MutableLiveData<Integer> getMutableLiveData()
    {
        if (mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void confirmCode()
    {
        if (code.get().toString().isEmpty())
        {
            mutableLiveData.setValue(3);
        }
        else
        {
            verifyCode();
        }
    }

    public void verifyCode()
    {
        verifyRepo.verifyCode(email.get(), code.get()).observeForever(new Observer<VerifyCodeResponse>()
        {
            @Override
            public void onChanged(VerifyCodeResponse verifyCodeResponse)
            {
                if (verifyCodeResponse.getStatus() == 200)
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

    public void resendCode()
    {
        verifyRepo.sendCode(email.get()).observeForever(new Observer<SendCodeResponse>()
        {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse)
            {
                if (sendCodeResponse.getStatus() == 200)
                {
                    mutableLiveData.setValue(3);
                }
            }
        });
    }
}

