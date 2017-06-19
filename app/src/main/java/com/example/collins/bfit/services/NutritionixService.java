package com.example.collins.bfit.services;

import com.example.collins.bfit.Constants;
import com.example.collins.bfit.models.Meal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by collins on 6/19/17.
 */

//class NutritionixService
public class NutritionixService {
    //to enhance connection to the API
    private static OkHttpClient client = new OkHttpClient();
    public static void findMeals(String meal, Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.NUTRITIONIX_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.NUTRITIONIX_MEAL_QUERY_PARAMETER, meal);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("x-app-id", Constants.Nutritionix_Application_ID)
                .header("x-app-key", Constants.Nutritionix_Application_Key)
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    //to enhance data to be displayed inform of an arraylist
    public ArrayList<Meal> processResults(Response response) {
        ArrayList<Meal> meals = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject mealJSON = new JSONObject(jsonData);
                JSONArray brandJSON = mealJSON.getJSONArray("branded");

                for (int i = 0; i < brandJSON.length(); i++) {
                    JSONObject myBrand = brandJSON.getJSONObject(i);

                     //String imageurl
                    String imageUrl = myBrand.getJSONObject("photo").getString("thumb");
                    //String foodname
                    String foodName = myBrand.getString("food_name");
                    //String servingUnit
                    String servingUnit = myBrand.getString("serving_unit");
                    //String brandName
                    String brandName = myBrand.getString("brand_name");
                    //String serving Qty
                    String servingQty = myBrand.getString("serving_qty");
                    //String mealCalories
                    String mealCalories = myBrand.getString("nf_calories");

                    //to clarify with our model Meal.java
                  Meal meal = new Meal(imageUrl, foodName, servingUnit, brandName, servingQty, mealCalories);
                    meals.add(meal);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return meals;
    }
}
