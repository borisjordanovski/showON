package com.example.boris.showon.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.activity.showONActivity;
import com.example.boris.showon.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Boris on 06-Sep-17.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private EditText etProfileName;
    private EditText etProfileLastName;
    private EditText etProfileCountry;
    private EditText etProfileEmail;
    private User user;
    private ImageView ivProfilePicture;

    private Button btnProfileEdit;
    private Button btnProfileCancel;
    private Button btnProfileSave;
    private Button btnProfileCamera;
    private Button btnProfileGallery;
    private LinearLayout llProfileButtons;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;

    public static ProfileFragment newInstance() { return new ProfileFragment();}

    private void initUI()   {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        etProfileName = (EditText) rootView.findViewById(R.id.etProfileName);
        etProfileLastName = (EditText) rootView.findViewById(R.id.etProfileLastName);
        etProfileCountry = (EditText) rootView.findViewById(R.id.etProfileCountry);
        etProfileEmail = (EditText) rootView.findViewById(R.id.etProfileEmail);
        llProfileButtons = (LinearLayout) rootView.findViewById(R.id.llProfileEditButtons);
        btnProfileEdit = (Button) rootView.findViewById(R.id.btnProfileEdit);
        btnProfileEdit.setOnClickListener(this);
        btnProfileCancel = (Button) rootView.findViewById(R.id.btnProfileCancel);
        btnProfileCancel.setOnClickListener(this);
        btnProfileSave = (Button) rootView.findViewById(R.id.btnProfileSave);
        btnProfileSave.setOnClickListener(this);
        btnProfileCamera = (Button) rootView.findViewById(R.id.btnProfileCamera);
        btnProfileCamera.setOnClickListener(this);
        btnProfileGallery = (Button) rootView.findViewById(R.id.btnProfileGallery);
        btnProfileGallery.setOnClickListener(this);
        user = new User();
        ivProfilePicture = (ImageView) rootView.findViewById(R.id.ivProfilePicture);

    }
    @Override
    public int getNavigationId() {
        return 3;
    }

    @Override
    public String getTitle() {
        return "Profile";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_profile,container, false);
        initUI();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String imageBase64 = sharedPref.getString("profile", "");
        if(!imageBase64.equals("")) {
            byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivProfilePicture.setImageBitmap(decodedByte);
        }
        myRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                for (int i = 0; i < dataSnapshot.getChildrenCount() ; i++) {
                    user.setName(dataSnapshot.getValue(User.class).getName());
                    user.setLastName(dataSnapshot.getValue(User.class).getLastName());
                    user.setEmail(dataSnapshot.getValue(User.class).getEmail());
                    user.setCountry(dataSnapshot.getValue(User.class).getCountry());
                    user.setGenres(dataSnapshot.getValue(User.class).getGenres());
                }
                etProfileName.setText(user.getName());
                etProfileLastName.setText(user.getLastName());
                etProfileCountry.setText(user.getCountry());
                etProfileEmail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }

    }

    private void startGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select"), REQUEST_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivProfilePicture.setImageBitmap(imageBitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            final byte[] imageInByte = baos.toByteArray();
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profile", Base64.encodeToString(imageInByte, Base64.DEFAULT));
            editor.commit();
            ((showONActivity)getActivity()).updateNavDrawerInfo(firebaseAuth.getCurrentUser().getUid());
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                bitmap = Bitmap.createBitmap(bitmap);
                ivProfilePicture.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] imageInByte = baos.toByteArray();
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile", Base64.encodeToString(imageInByte, Base64.DEFAULT));
                editor.commit();
                ((showONActivity)getActivity()).updateNavDrawerInfo(firebaseAuth.getCurrentUser().getUid());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.btnProfileEdit:
                btnProfileEdit.setVisibility(View.GONE);
                llProfileButtons.setVisibility(View.VISIBLE);
                etProfileName.setEnabled(true);
                etProfileLastName.setEnabled(true);
                etProfileCountry.setEnabled(true);
                user.setName(etProfileName.getText().toString());
                user.setLastName(etProfileLastName.getText().toString());
                user.setCountry(etProfileCountry.getText().toString());
                break;
            case R.id.btnProfileCancel:
                llProfileButtons.setVisibility(View.GONE);
                btnProfileEdit.setVisibility(View.VISIBLE);
                etProfileName.setEnabled(false);
                etProfileLastName.setEnabled(false);
                etProfileCountry.setEnabled(false);
                etProfileName.setText(user.getName());
                etProfileLastName.setText(user.getLastName());
                etProfileCountry.setText(user.getCountry());
                break;
            case R.id.btnProfileSave:
                if(etProfileName.getText().toString().length() == 0
                        || etProfileLastName.getText().toString().length() == 0
                        || etProfileCountry.getText().toString().length() == 0) {
                    Toast.makeText(context, "Please fill all the fields before saving", Toast.LENGTH_SHORT).show();
                } else  {
                    llProfileButtons.setVisibility(View.GONE);
                    btnProfileEdit.setVisibility(View.VISIBLE);
                    etProfileName.setEnabled(false);
                    etProfileLastName.setEnabled(false);
                    etProfileCountry.setEnabled(false);
                    user.setName(etProfileName.getText().toString());
                    user.setLastName(etProfileLastName.getText().toString());
                    user.setCountry(etProfileCountry.getText().toString());
                    myRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("name").setValue(user.getName());
                    myRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("lastName").setValue(user.getLastName());
                    myRef.child("user").child(firebaseAuth.getCurrentUser().getUid()).child("country").setValue(user.getCountry());
                }
                break;
            case R.id.btnProfileGallery:
                startGallery();
                break;
            case R.id.btnProfileCamera:
                startCamera();
                break;

        }
    }


}
