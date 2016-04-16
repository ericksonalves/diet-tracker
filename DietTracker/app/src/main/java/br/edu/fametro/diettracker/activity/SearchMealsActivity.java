package br.edu.fametro.diettracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.adapter.MealsAdapter;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.model.Meal;

public class SearchMealsActivity extends AppCompatActivity {

    private EditText mSearchMealEditText;
    private ListView mSearchMealsListView;
    private MealsAdapter mAdapter;
    private List<Meal> mMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_meal);
        mSearchMealEditText = (EditText) findViewById(R.id.edit_text_search_meal);
        mSearchMealsListView = (ListView) findViewById(R.id.list_view_search_meals);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMeals = Controller.getInstance().getMeals(this);
        List<Meal> meals = new ArrayList<>();
        meals.addAll(mMeals);
        mAdapter = new MealsAdapter(this, R.layout.meals_list_item, meals);
        mSearchMealsListView.setAdapter(mAdapter);
        mSearchMealEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Meal> items = new ArrayList<>();
                for (Meal meal : mMeals) {
                    if (meal.getName().contains(s)) {
                        items.add(meal);
                    }
                }
                mAdapter.clear();
                mAdapter.addAll(items);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
