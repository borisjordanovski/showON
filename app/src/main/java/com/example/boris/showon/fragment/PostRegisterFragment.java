package com.example.boris.showon.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.model.Genre;
import com.example.boris.showon.model.User;
import com.example.boris.showon.util.UIHelper;
import com.example.boris.showon.view.ShowONCheckButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 20-Jul-17.
 */

public class PostRegisterFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private ShowONCheckButton btnPostRegisterAdventure;
    private ShowONCheckButton btnPostRegisterAction;
    private ShowONCheckButton btnPostRegisterAnimation;
    private ShowONCheckButton btnPostRegisterComedy;
    private ShowONCheckButton btnPostRegisterCrime;
    private ShowONCheckButton btnPostRegisterHorror;
    private ShowONCheckButton btnPostRegisterDocumentary;
    private ShowONCheckButton btnPostRegisterMusic;
    private Button btnPostRegisterContinue;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;

    private void initUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        btnPostRegisterAdventure = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterAdventure);
        btnPostRegisterAdventure.setOnClickListener(this);
        btnPostRegisterAction = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterAction);
        btnPostRegisterAction.setOnClickListener(this);
        btnPostRegisterAnimation = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterAnimation);
        btnPostRegisterAnimation.setOnClickListener(this);
        btnPostRegisterComedy = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterComedy);
        btnPostRegisterComedy.setOnClickListener(this);
        btnPostRegisterCrime = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterCrime);
        btnPostRegisterCrime.setOnClickListener(this);
        btnPostRegisterHorror = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterHorror);
        btnPostRegisterHorror.setOnClickListener(this);
        btnPostRegisterDocumentary = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterDocumentary);
        btnPostRegisterDocumentary.setOnClickListener(this);
        btnPostRegisterMusic = (ShowONCheckButton) rootView.findViewById(R.id.btnPostRegisterMusic);
        btnPostRegisterMusic.setOnClickListener(this);
        btnPostRegisterContinue = (Button) rootView.findViewById(R.id.btnPostRegisterContinue);
        btnPostRegisterContinue.setOnClickListener(this);
    }

    public static PostRegisterFragment newInstance() {
        return new PostRegisterFragment();
    }

    @Override
    public int getNavigationId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_post_register, container, false);
        initUI();


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPostRegisterAdventure:
                if (btnPostRegisterAdventure.isChecked()) {
                    btnPostRegisterAdventure.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterAdventure.setChecked(false);
                    btnPostRegisterAdventure.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterAdventure.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterAdventure.setChecked(true);
                    btnPostRegisterAdventure.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;
            case R.id.btnPostRegisterAction:
                if (btnPostRegisterAction.isChecked()) {
                    btnPostRegisterAction.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterAction.setChecked(false);
                    btnPostRegisterAction.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterAction.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterAction.setChecked(true);
                    btnPostRegisterAction.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;
            case R.id.btnPostRegisterAnimation:
                if (btnPostRegisterAnimation.isChecked()) {
                    btnPostRegisterAnimation.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterAnimation.setChecked(false);
                    btnPostRegisterAnimation.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterAnimation.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterAnimation.setChecked(true);
                    btnPostRegisterAnimation.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;
            case R.id.btnPostRegisterComedy:
                if (btnPostRegisterComedy.isChecked()) {
                    btnPostRegisterComedy.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterComedy.setChecked(false);
                    btnPostRegisterComedy.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterComedy.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterComedy.setChecked(true);
                    btnPostRegisterComedy.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;
            case R.id.btnPostRegisterCrime:
                if (btnPostRegisterCrime.isChecked()) {
                    btnPostRegisterCrime.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterCrime.setChecked(false);
                    btnPostRegisterCrime.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterCrime.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterCrime.setChecked(true);
                    btnPostRegisterCrime.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;
            case R.id.btnPostRegisterHorror:
                if (btnPostRegisterHorror.isChecked()) {
                    btnPostRegisterHorror.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterHorror.setChecked(false);
                    btnPostRegisterHorror.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterHorror.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterHorror.setChecked(true);
                    btnPostRegisterHorror.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;
            case R.id.btnPostRegisterDocumentary:
                if (btnPostRegisterDocumentary.isChecked()) {
                    btnPostRegisterDocumentary.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterDocumentary.setChecked(false);
                    btnPostRegisterDocumentary.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterDocumentary.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterDocumentary.setChecked(true);
                    btnPostRegisterDocumentary.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;

            case R.id.btnPostRegisterMusic:
                if (btnPostRegisterMusic.isChecked()) {
                    btnPostRegisterMusic.setBackgroundResource(R.drawable.check_border_unchecked);
                    btnPostRegisterMusic.setChecked(false);
                    btnPostRegisterMusic.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    btnPostRegisterMusic.setBackgroundResource(R.drawable.check_border_checked);
                    btnPostRegisterMusic.setChecked(true);
                    btnPostRegisterMusic.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                break;

            case R.id.btnPostRegisterContinue:
                if(!btnPostRegisterAdventure.isChecked() && !btnPostRegisterAction.isChecked() && !btnPostRegisterAnimation.isChecked() &&
                        !btnPostRegisterComedy.isChecked() && !btnPostRegisterCrime.isChecked()
                        && !btnPostRegisterHorror.isChecked() && !btnPostRegisterDocumentary.isChecked() && !btnPostRegisterMusic.isChecked())
                    Toast.makeText(context, "Please select at least one genre" , Toast.LENGTH_SHORT).show();
                else {
                    String genres = new String();

                    if(btnPostRegisterAdventure.isChecked())
                        genres += "adventure,";
                    if(btnPostRegisterAction.isChecked())
                        genres += "action,";
                    if(btnPostRegisterAnimation.isChecked())
                        genres += "animation,";
                    if(btnPostRegisterComedy.isChecked())
                        genres += "comedy,";
                    if(btnPostRegisterCrime.isChecked() )
                        genres += "crime,";
                   if( btnPostRegisterHorror.isChecked())
                       genres += "horror,";
                    if(btnPostRegisterDocumentary.isChecked())
                        genres += "documentary,";
                    if(btnPostRegisterMusic.isChecked())
                        genres += "music,";

                    genres = new String(genres.substring(0, genres.length() - 1));
                    List<String> listGenres = new ArrayList<>();
                    String[] temp = genres.split(",");
                    for (int i = 0; i < temp.length ; i++) {
                        listGenres.add(temp[i]);
                    }
                    Bundle args = getArguments();
                    temp = args.getString("USER", "").split(",");
                    User user = new User();
                    user.setName(temp[0]);
                    user.setLastName(temp[1]);
                    user.setCountry(temp[2]);
                    user.setEmail(temp[3]);
                    user.setGenres(listGenres);
                    myRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);

                    UIHelper.replaceFragment(supportFragmentManager,R.id.fragmentContainer, MainFragment.newInstance(), false, 0, 0);

                }

        }
    }
}
