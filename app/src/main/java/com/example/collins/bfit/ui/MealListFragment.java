package com.example.collins.bfit.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.R;
import com.example.collins.bfit.adapters.MealListAdapter;
import com.example.collins.bfit.models.Meal;
import com.example.collins.bfit.services.NutritionixService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealListFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MealListAdapter mAdapter;
    public ArrayList<Meal> mMeals = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentMeals;

    public MealListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        // Instructs fragment to include menu options:
        setHasOptionsMenu(true);
    }

     //receiving a response from NutritionixService class
    private void getMeals(String meal) {
        final NutritionixService nutritionixService = new NutritionixService();
        nutritionixService.findMeals(meal, new Callback() {
            //callback methods
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMeals = nutritionixService.processResults(response);
                //to enhance threading and enable us to display data held by the API
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //using custom adapters with recycler view
                        mAdapter = new MealListAdapter(getActivity(), mMeals);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
//                        String[] mealNames = new String[mMeals.size()];
//                        for (int i = 0; i< mealNames.length; i ++) {
//                            mealNames[i] = mMeals.get(i).getFoodName();
//                        }
//                        ArrayAdapter adapter = new ArrayAdapter(MealListActivity.this, android.R.layout.simple_list_item_1, mealNames);
//                        mListView.setAdapter(adapter);
//
//                        for (Meal meal : mMeals) {
//                            Log.d(TAG, "Image url: " + meal.getImageUrl());
//                            Log.d(TAG, "Food name: " + meal.getFoodName());
//                            Log.d(TAG, "Serving unit: " + meal.getServingUnit());
//                            Log.d(TAG, "Brand name: " + meal.getBrandName());
//                            Log.d(TAG, "Serving qty: " + meal.getServingQty());
//                            Log.d(TAG, "Meal categories: " + meal.getMealCalories());
//                        }
                    }
                });


//                try {
//                    String jsonData = response.body().string();
//                    if (response.isSuccessful()) {
//                        Log.v(TAG, jsonData);
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_list, container, false);
        ButterKnife.bind(this, view);

        mRecentMeals = mSharedPreferences.getString(Constants.PREFERENCES_MEAL_KEY, null);

        if (mRecentMeals != null) {
            getMeals(mRecentMeals);
        }

        return view;
    }

    //adding search view
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        //adding searchview listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getMeals(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void addToSharedPreferences(String meal) {
        mEditor.putString(Constants.PREFERENCES_MEAL_KEY, meal).apply();
    }
}

