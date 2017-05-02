package com.vb.offerbrite.screens.login.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vb.offerbrite.R;
import com.vb.offerbrite.baseclasses.BaseFragment;
import com.vb.offerbrite.data.models.SignUpModel;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.Constants;

import retrofit2.Call;
import retrofit2.Response;

public class SignUpFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private AppCompatEditText etUserName;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etCountry;
    private Button btnSignUp;
    private ProgressBar pbSignUp;
    private TextInputLayout tillUserName;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private TextInputLayout tilCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_signup, container, false);
            init();
        }
        return rootView;
    }

    private void init() {
        pbSignUp = (ProgressBar) rootView.findViewById(R.id.pb_sign_up);
        etUserName = (AppCompatEditText) rootView.findViewById(R.id.et_user_name);
        tillUserName = (TextInputLayout) rootView.findViewById(R.id.til_username);

        etEmail = (AppCompatEditText) rootView.findViewById(R.id.et_email);
        tilEmail = (TextInputLayout) rootView.findViewById(R.id.til_email);

        etPassword = (AppCompatEditText) rootView.findViewById(R.id.et_password);
        tilPassword = (TextInputLayout) rootView.findViewById(R.id.til_password);

        etCountry = (AppCompatEditText) rootView.findViewById(R.id.et_country);
        tilCountry = (TextInputLayout) rootView.findViewById(R.id.til_country);

        btnSignUp = (Button) rootView.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                signUpService();
                AppUtils.hideKeyBoard(baseActivity, rootView);
                break;
        }
    }

    private void signUpService() {
        String userName = etUserName.getText().toString();
        String email = etEmail.getText().toString().replaceAll(" ", "");
        String password = etPassword.getText().toString();
        String country = etCountry.getText().toString();

        if (isSignUpValid(userName, email, password, country)) {
            SignUpModel info = new SignUpModel(userName, email, password, country);
            final Call<Object> request = application.getClient().signUp(info);
            pbSignUp.setVisibility(View.VISIBLE);
            if (AppUtils.isNetworkAvailable(baseActivity)) {
                request.enqueue(new retrofit2.Callback<Object>() {

                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        pbSignUp.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
//                            AppUtils.makeText(baseActivity, response.body().toString());
                            Log.i("sign up", response.body().toString());
                            AppUtils.makeText(baseActivity, "Now you are our official member, Please Log-in", Toast.LENGTH_LONG);
                            baseActivity.onBackPressed();
                        } else {
                            AppUtils.makeText(baseActivity, response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        pbSignUp.setVisibility(View.GONE);
                        if (!request.isCanceled())
                            AppUtils.makeText(baseActivity, Constants.RETRY, Toast.LENGTH_LONG);
                    }
                });
            } else {
                pbSignUp.setVisibility(View.GONE);
                AppUtils.makeText(baseActivity, Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
            }
        } else {
            pbSignUp.setVisibility(View.GONE);
        }
    }

    private boolean isSignUpValid(String userName, String email, String password, String country) {
        if (AppUtils.isEmpty(userName)) {
            tillUserName.setError(Constants.VALID_NAME);
            tillUserName.requestFocus();
            return false;
        } else {
            tillUserName.setError("");
        }

        if (AppUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(Constants.VALID_EMAIL_ADDRESS);
            tilEmail.requestFocus();
            return false;
        } else {
            tilEmail.setError("");
            tilEmail.setErrorEnabled(false);
        }

        if (AppUtils.isEmpty(password)) {
            tilPassword.setError(Constants.VALID_PASSWORD);
            tilPassword.requestFocus();
            return false;
        } else {
            tilPassword.setError("");
        }

        if (AppUtils.isEmpty(country)) {
            tilCountry.setError(Constants.VALID_COUNTRY);
            tilCountry.requestFocus();
            return false;
        } else {
            tilCountry.setError("");
            tilCountry.setErrorEnabled(false);
        }
        return true;
    }
}
