package com.example.boris.showon.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.activity.showONActivity;
import com.example.boris.showon.model.User;
import com.example.boris.showon.util.UIHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Boris on 18-Jul-17.
 */

public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private ImageView ivRegisterLogo;
    private EditText etRegisterName;
    private EditText etRegisterLastName;
    private EditText etRegisterCountry;
    private EditText etRegisterEmail;
    private EditText etRegisterPassword;
    private Button btnRegisterSignUp;
    private TextView tvRegisterSignIn;
    private Button btnVisiblePassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String userID;
    //Firebase Database
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    private void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(getActivity());
        ivRegisterLogo = (ImageView) rootView.findViewById(R.id.ivRegisterLogo);
        etRegisterName = (EditText) rootView.findViewById(R.id.etRegisterName);
        etRegisterLastName = (EditText) rootView.findViewById(R.id.etRegisterLastName);
        etRegisterCountry = (EditText) rootView.findViewById(R.id.etRegisterCountry);
        etRegisterEmail = (EditText) rootView.findViewById(R.id.etRegisterEmail);
        etRegisterPassword = (EditText) rootView.findViewById(R.id.etRegisterPassword);
        etRegisterPassword.setTypeface(etRegisterEmail.getTypeface());
        btnRegisterSignUp = (Button) rootView.findViewById(R.id.btnRegisterSignUp);
        btnRegisterSignUp.setOnClickListener(this);
        tvRegisterSignIn = (TextView) rootView.findViewById(R.id.tvRegisterSignIn);
        tvRegisterSignIn.setOnClickListener(this);
        btnVisiblePassword = (Button) rootView.findViewById(R.id.btnVisiblePassword);
        userID = new String();
    }

    @Override
    public int getNavigationId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return "Sign Up";
    }

    private void signUpUser() {
        String name = etRegisterName.getText().toString();
        String lastName = etRegisterLastName.getText().toString();
        String country = etRegisterCountry.getText().toString();
        String email = etRegisterEmail.getText().toString();
        String password = etRegisterPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(lastName)) {
            Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {


//            SharedPreferences sp = (getActivity().getSharedPreferences("key", 0));
//            sp.edit().putString("NAME", name).apply();
//            sp.edit().putString("LAST_NAME", lastName).apply();
//            sp.edit().putString("COUNTRY", country).apply();
//            sp.edit().putString("EMAIL", email).apply();
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseUser = firebaseAuth.getCurrentUser();
                        userID = firebaseUser.getUid();
                        Bundle args = new Bundle();
                        args.putString("USER", etRegisterName.getText().toString()+ "," + etRegisterLastName.getText().toString() +"," + etRegisterCountry.getText().toString()+ "," + etRegisterEmail.getText().toString());
                        PostRegisterFragment postRegisterFragment = PostRegisterFragment.newInstance();
                        postRegisterFragment.setArguments(args);
                        UIHelper.replaceFragment(supportFragmentManager, R.id.fragmentContainer, postRegisterFragment, false, 0, 0);
                    } else {
                        Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ((showONActivity) getActivity()).hideActionBar(true);

        initUI();


        btnVisiblePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etRegisterPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        btnVisiblePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                        break;
                    case MotionEvent.ACTION_UP:
                        etRegisterPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        btnVisiblePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_black, 0);
                        etRegisterPassword.setTypeface(etRegisterEmail.getTypeface());
                        break;
                }
                return true;
            }
        });


        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRegisterSignIn:
                UIHelper.replaceFragment(supportFragmentManager, R.id.fragmentContainer, LoginFragment.newInstance(), false, 0, 0);
                break;
            case R.id.btnRegisterSignUp:
                signUpUser();
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if (firebaseAuth.getCurrentUser() != null)
                    ((showONActivity) getActivity()).updateNavDrawerInfo(firebaseAuth.getCurrentUser().getUid());
                break;
        }

    }


}
