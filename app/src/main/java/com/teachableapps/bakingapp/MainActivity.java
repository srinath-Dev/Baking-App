package com.teachableapps.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.teachableapps.bakingapp.models.Ingredient;
import com.teachableapps.bakingapp.models.Recipe;
import com.teachableapps.bakingapp.utilities.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ListItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String RECIPE_DETAIL_KEY = "recipedetail";
    public static final String INGREDIENTS_STRINGS_KEY = "ingredientsstringskey";

    private List<Recipe> mRecipeList = new ArrayList<>();
    private RecyclerView mRecipeListRecyclerView;
    private RecipeListAdapter mRecipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Clear recipeList
        mRecipeList.clear();

        // RecyclerView
        mRecipeListRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        if(findViewById(R.id.v_tablet_pixel)!=null) {
//            mRecipeListRecyclerView = (RecyclerView) findViewById(R.id.rv_main_tablet);
            mRecipeListRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }else{
//            mRecipeListRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
            mRecipeListRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
        mRecipeListRecyclerView.setHasFixedSize(false);
        mRecipeListAdapter = new RecipeListAdapter(mRecipeList, this, this);
        mRecipeListRecyclerView.setAdapter(mRecipeListAdapter);

        // Query Recipe API
        loadRecipes();
    }

    @Override
    public void OnListItemClick(Recipe recipe) {

        // Update Widget
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // compile ingredient list
        List<Ingredient> ingredientList = recipe.getIngredients();
        ArrayList<String> ingredientsStrings = new ArrayList<String>();
        for (int i=0; i<ingredientList.size(); i++) {
            ingredientsStrings.add( //" • " +
                    ingredientList.get(i).getQuantity() + " " +
                    ingredientList.get(i).getMeasure() + " " +
                    ingredientList.get(i).getIngredient() + "\n");
        }

        // send broadcast intent to update ingredient list in widget
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.teachableapps.bakingapp.broadcast.INGREDIENTS_STRINGS");
        broadcastIntent.putStringArrayListExtra(INGREDIENTS_STRINGS_KEY,ingredientsStrings);
        sendBroadcast(broadcastIntent);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_listview);

        //Now update all widgets
        BakingWidgetProvider.updateRecipeWidgets(this, appWidgetManager, recipe.getName(), appWidgetIds);

        // Start Recipe Detail Activity
        Intent recipeDetailIntent = new Intent(MainActivity.this, RecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_DETAIL_KEY, recipe);
        recipeDetailIntent.putExtras(bundle);
        startActivity(recipeDetailIntent);

    }

    public void loadRecipes() {

        // Create a call object
        Call<ArrayList<Recipe>> call = ApiUtils.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                // Success
                mRecipeList = response.body();
                mRecipeListAdapter.setRecipeListData(mRecipeList);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

                // Failed
                Log.d(TAG,"Failed");
                t.printStackTrace();
                showEmptyView();
            }
        });
    }

    // Show this view when list is empty
    private void showEmptyView() {
        Toast.makeText(this, "EMPTY List", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"EMPTY List");
    }

}
