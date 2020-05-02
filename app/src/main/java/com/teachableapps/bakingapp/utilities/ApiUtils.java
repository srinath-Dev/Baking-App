package com.teachableapps.bakingapp.utilities;

import android.util.Log;

import com.teachableapps.bakingapp.models.Recipe;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ApiUtils {
    private static final String TAG = ApiUtils.class.getSimpleName();

    private static Retrofit retrofit = null;

    // Base URL of API endpoint
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    // Interface
    public interface RecipeApiUtils {

        // Create a call with method GET with the given endpoint
        @GET("baking.json")
        Call<ArrayList<Recipe>> fetchRecipes();

    }

    /*
     * Instantiate Retrofit.Builder and fetch the list of recipes
     * @return Call<ArrayList<Recipe>> list of Recipe objects
     */
    public static Call<ArrayList<Recipe>> getRecipes() {

        //enable logging
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Create a basic REST adapter which points to the BASE_URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeApiUtils apiUtils = retrofit.create(RecipeApiUtils.class);

        Call<ArrayList<Recipe>> call = apiUtils.fetchRecipes();

//        Log.d(TAG,"Fetch attempt completed.");
        return call; //apiUtils.fetchRecipes();

    }

}