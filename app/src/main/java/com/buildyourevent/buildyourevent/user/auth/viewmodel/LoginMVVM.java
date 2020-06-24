package com.buildyourevent.buildyourevent.user.auth.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.buildyourevent.buildyourevent.database.AuthRepository;
import com.buildyourevent.buildyourevent.model.auth.login.LoginRequest;
import com.buildyourevent.buildyourevent.model.auth.login.LoginResponse;

public class LoginMVVM extends ViewModel
{
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private MutableLiveData<LoginRequest> userMutableLiveData;
    private MutableLiveData<Integer> clickLiveData;

    AuthRepository authRepository = new AuthRepository();

    public MutableLiveData<LoginRequest> getUser()
    {
        if (userMutableLiveData == null)
        {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getSkipValue()
    {
        if (clickLiveData == null)
        {
            clickLiveData = new MutableLiveData<>();
        }
        return clickLiveData;
    }

    public LiveData<LoginResponse> loginCurrentUser(LoginRequest loginRequest)
    {
        return authRepository.loginUser(loginRequest);
    }

    public void onLoginClicked()
    {
        LoginRequest loginUser = new LoginRequest(EmailAddress.getValue(), Password.getValue());

        userMutableLiveData.setValue(loginUser);
    }

    public void onSkipClicked()
    {
        clickLiveData.setValue(1);
    }

    public void onForgotPasswordClicked()
    {
        clickLiveData.setValue(2);
    }

    public void onRegisterClicked()
    {
        clickLiveData.setValue(3);
    }
}