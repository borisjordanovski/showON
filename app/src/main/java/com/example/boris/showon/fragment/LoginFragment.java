package com.example.boris.showon.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.activity.showONActivity;
import com.example.boris.showon.util.UIHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Boris on 18-Jul-17.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private EditText etLoginEmail;
    private EditText etLoginPassword;
    private Button btnLoginLogin;
    private TextView tvLoginRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DrawerLayout drawer;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(context);
        etLoginEmail = (EditText) rootView.findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) rootView.findViewById(R.id.etLoginPassword);
        etLoginPassword.setTypeface(etLoginEmail.getTypeface());
        btnLoginLogin = (Button) rootView.findViewById(R.id.btnLoginLogin);
        btnLoginLogin.setOnClickListener(this);
        tvLoginRegister = (TextView) rootView.findViewById(R.id.tvLoginRegister);
        tvLoginRegister.setOnClickListener(this);


    }

    @Override
    public int getNavigationId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return "showON Login";
    }

    private void login() {
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            Toast.makeText(context, "Please enter email and password...", Toast.LENGTH_SHORT).show();
        else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        UIHelper.replaceFragment(supportFragmentManager, R.id.fragmentContainer, MainFragment.newInstance(), false, 0, 0);
                    } else {
                        Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();

                }
            });

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ((showONActivity) getActivity()).hideActionBar(true);
        initUI();
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        etLoginEmail.clearFocus();
        etLoginPassword.clearFocus();

        return rootView;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginLogin:
                login();
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if(firebaseAuth.getCurrentUser() != null)
                    ((showONActivity)getActivity()).updateNavDrawerInfo(firebaseAuth.getCurrentUser().getUid());
                break;
            case R.id.tvLoginRegister:
                UIHelper.replaceFragment(supportFragmentManager, R.id.fragmentContainer, RegisterFragment.newInstance(), false, 0, 0);
        }

    }


}
