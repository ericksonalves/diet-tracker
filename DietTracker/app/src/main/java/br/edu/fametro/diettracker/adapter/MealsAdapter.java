package br.edu.fametro.diettracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.model.Meal;

public class MealsAdapter extends ArrayAdapter<Meal> {

    public MealsAdapter(Context context, int resource, List<Meal> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.meals_list_item, parent, false);
        }
        Meal meal = getItem(position);
        TextView mealNameTextView = (TextView) rowView.findViewById(R.id.text_view_meals_item_name);
        TextView mealAmountTextView = (TextView) rowView.findViewById(R.id.text_view_meals_item_amount);
        TextView mealCaloriesTextView = (TextView) rowView.findViewById(R.id.text_view_meals_item_calories);
        mealNameTextView.setText(meal.getName());
        mealAmountTextView.setText(meal.getAmount());
        mealCaloriesTextView.setText(String.format(getContext().getString(R.string.calories_format), String.valueOf(meal
                .getCalories())));
        return rowView;
    }
}
