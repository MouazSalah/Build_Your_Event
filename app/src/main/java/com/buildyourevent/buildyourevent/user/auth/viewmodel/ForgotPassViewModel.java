package com.buildyourevent.buildyourevent.user.auth.viewmodel;

import android.util.Log;
import android.util.Patterns;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.buildyourevent.buildyourevent.model.auth.code.SendCodeResponse;
import com.buildyourevent.buildyourevent.user.auth.repository.VerifyRepo;

import java.util.Objects;

public class ForgotPassViewModel extends ViewModel
{
    public ObservableField<String> email = new ObservableField<>();
    private MutableLiveData<Integer> mutableLiveData;

    VerifyRepo verifyRepo = new VerifyRepo();

    public MutableLiveData<Integer> getMutableLiveData()
    {
        if (mutableLiveData == null)
        {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void sendCode() {
        verifyRepo.sendCode(email.get()).observeForever(new Observer<SendCodeResponse>() {
            @Override
            public void onChanged(SendCodeResponse sendCodeResponse) {
                if (sendCodeResponse.getStatus() == 200) {
                    mutableLiveData.setValue(1);
                } else {
                    mutableLiveData.setValue(0);
                }
            }
        });
    }

    public void onSendClicked()
    {
        Log.d("mouaaaaz", " clicked");

        if (Objects.requireNonNull(email.get()).isEmpty())
        {
           mutableLiveData.setValue(3);
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(email.get())).matches())
        {
            mutableLiveData.setValue(4);
        }
        else
        {
            sendCode();
        }
    }

}
