package com.vb.offerbrite.screens.login.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vb.offerbrite.R;
import com.vb.offerbrite.baseclasses.BaseFragment;
import com.vb.offerbrite.data.models.LoginModel;
import com.vb.offerbrite.resservice.ResLogin;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.Constants;
import com.vb.offerbrite.utils.ServerConstants;

import retrofit2.Call;
import retrofit2.Response;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private TextView tvSignUp;
    private TextView tvContinue;
    private Button btnLogin;
    private Button btnFacebook;
    private Button btnGoogle;
    private ProgressBar pbLogin;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            init();
        }
        return rootView;
    }

    private void init() {

        etEmail = (AppCompatEditText) rootView.findViewById(R.id.et_email);
        tilEmail = (TextInputLayout) rootView.findViewById(R.id.til_email);

        etPassword = (AppCompatEditText) rootView.findViewById(R.id.et_password);
        tilPassword = (TextInputLayout) rootView.findViewById(R.id.til_password);

        tvSignUp = (TextView) rootView.findViewById(R.id.tv_sign_up);
        tvSignUp.setOnClickListener(this);

        tvContinue = (TextView) rootView.findViewById(R.id.tv_continue);
        tvContinue.setOnClickListener(this);

        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        btnFacebook = (Button) rootView.findViewById(R.id.btn_facebook);
        btnFacebook.setOnClickListener(this);

        btnGoogle = (Button) rootView.findViewById(R.id.btn_google);
        btnGoogle.setOnClickListener(this);

        pbLogin = (ProgressBar) rootView.findViewById(R.id.pb_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:
                baseActivity.changeFragment(new SignUpFragment(), true);
                break;

            case R.id.tv_continue:
                baseActivity.changeToHomeScreen();
                baseActivity.finish();
                break;
            case R.id.btn_facebook:
            case R.id.btn_google:
                break;
            case R.id.btn_login:
                loginService();
                break;
        }
    }

    private void loginService() {
        AppUtils.hideKeyBoard(baseActivity, rootView);
        pbLogin.setVisibility(View.VISIBLE);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (isValidCredential(email, password))
            if (AppUtils.isNetworkAvailable(baseActivity)) {

                LoginModel info = new LoginModel(email, password);
                final Call<ResLogin> request = application.getClient().userLogin(info);
                request.enqueue(new retrofit2.Callback<ResLogin>() {

                    @Override
                    public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {
                        pbLogin.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().response &&
                                    response.body().responseCode.equalsIgnoreCase(ServerConstants.SUCCESS_CODE)) {
                                if (response.body().data != null) {
                                    baseActivity.updateUserInfo(response.body().data);
                                    baseActivity.changeToHomeScreen();
                                } else if (!AppUtils.isEmpty(response.body().responseMessage)) {
                                    AppUtils.makeText(baseActivity, response.body().responseMessage, Toast.LENGTH_LONG);
                                }
                            } else {
                                if (!AppUtils.isEmpty(response.body().responseMessage))
                                    AppUtils.makeText(baseActivity, response.body().responseMessage, Toast.LENGTH_LONG);
                            }
                        } else {
                            AppUtils.makeText(baseActivity, response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResLogin> call, Throwable t) {
                        pbLogin.setVisibility(View.GONE);
                        if (!request.isCanceled())
                            AppUtils.makeText(baseActivity, t.getMessage(), Toast.LENGTH_LONG);
                    }
                });
            } else {
                pbLogin.setVisibility(View.GONE);
                AppUtils.makeText(baseActivity, Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
            }
    }

    private boolean isValidCredential(String email, String password) {
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
        return true;
    }
}
