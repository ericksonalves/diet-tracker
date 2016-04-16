package br.edu.fametro.diettracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.adapter.MealsAdapter;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.model.Meal;

public class MealsActivity extends AppCompatActivity {

    private ListView mMealsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        mMealsListView = (ListView) findViewById(R.id.list_view_meals);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Meal> meals = Controller.getInstance().getMeals(this);
        MealsAdapter adapter = new MealsAdapter(this, R.layout.meals_list_item, meals);
        mMealsListView.setAdapter(adapter);
    }
}
