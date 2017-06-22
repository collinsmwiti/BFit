//package
package com.example.collins.bfit.ui;

//imports

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.models.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
//class MealDetailFragment
public class MealDetailFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.mealImageView) ImageView mImageLabel;
    @Bind(R.id.mealNameTextView) TextView mNameLabel;
    @Bind(R.id.brandTextView) TextView mBrandLabel;
    @Bind(R.id.caloriesTextView) TextView mCaloriesLabel;
    @Bind(R.id.servingUnitTextView) TextView mServingUnitLabel;
    @Bind(R.id.servingQtyTextView) TextView mServingQtyLabel;
    @Bind(R.id.saveMealButton) TextView mSaveMealButton;

    private Meal mMeal;
    private ArrayList<Meal> mMeals;
    private int mPosition;
    private String mSource;
    private static final int REQUEST_IMAGE_CAPTURE = 111;


    //constructor MealDetailFragment
    public static MealDetailFragment newInstance(ArrayList<Meal> meals, Integer position, String source) {
        MealDetailFragment mealDetailFragment = new MealDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_MEALS, Parcels.wrap(meals));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        mealDetailFragment.setArguments(args);
        return mealDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeals = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_MEALS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mMeal = mMeals.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);
        ButterKnife.bind(this, view);

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveMealButton.setVisibility(View.GONE);
        }
//        else {
        if (!mMeal.getImageUrl().contains("http")) {
            try {
                Bitmap image = decodeFromFirebaseBase64(mMeal.getImageUrl());
                mImageLabel.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(view.getContext()).load(mMeal.getImageUrl()).fit().centerCrop().into(mImageLabel);
            mNameLabel.setText(mMeal.getFoodName());
            mCaloriesLabel.setText("Calories: " + mMeal.getMealCalories());
            mBrandLabel.setText("Brand: " + mMeal.getBrandName());
            mServingUnitLabel.setText("Serving unit: " + mMeal.getServingUnit());
            mServingQtyLabel.setText("Serving Quantity: " + mMeal.getServingQty());
            mSaveMealButton.setOnClickListener(this);
        }

        return view;
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onClick(View v) {

        if (v == mSaveMealButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            DatabaseReference mealRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_MEALS)
                    .child(uid);

            DatabaseReference pushRef = mealRef.push();
            String pushId = pushRef.getKey();
            mMeal.setPushId(pushId);
            pushRef.setValue(mMeal);
//            mealRef.push().setValue(mMeal);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_MEALS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mMeal.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }

}
