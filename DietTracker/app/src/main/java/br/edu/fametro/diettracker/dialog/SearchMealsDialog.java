package br.edu.fametro.diettracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.adapter.MealsAdapter;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.model.Meal;

public class SearchMealsDialog extends Dialog {

    private SearchMealDialogListener mListener;

    public SearchMealsDialog(Context context, SearchMealDialogListener listener) {
        super(context);
        mListener = listener;
    }

    private EditText mSearchMealEditText;
    private ListView mSearchMealsListView;
    private MealsAdapter mAdapter;
    private List<Meal> mMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* Atribui o visual do diálogo de acordo com o especificado nos recursos da aplicação */
        setContentView(R.layout.dialog_search_meal);
        /* Instanciação de um objeto para configurar os parâametros visuais da tela */
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        /* Obtenção de um objeto representando a tela */
        Window window = getWindow();
        /* Cópia dos atributos da tela */
        layoutParams.copyFrom(window.getAttributes());
        /* Atribuição da largura para o tamanho disponível na tela */
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        /* Atribuição da altura para o tamanho necessário para os componentes */
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        /* Atribuição dos atributos visuais para o diálogo */
        window.setAttributes(layoutParams);
        mSearchMealEditText = (EditText) findViewById(R.id.edit_text_search_meal);
        mSearchMealsListView = (ListView) findViewById(R.id.list_view_search_meals);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMeals = Controller.getInstance().getMeals(getContext());
        List<Meal> meals = new ArrayList<>();
        meals.addAll(mMeals);
        mAdapter = new MealsAdapter(getContext(), R.layout.meals_list_item, meals);
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
        assignListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dissociateListeners();
    }

    private void assignListeners() {
        mSearchMealsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal meal = (Meal) mSearchMealsListView.getAdapter().getItem(position);
                mListener.onMealSelected(meal);
                dismiss();
            }
        });
    }

    private void dissociateListeners() {
        mSearchMealsListView.setOnItemClickListener(null);
        mSearchMealEditText.addTextChangedListener(null);
    }

    public interface SearchMealDialogListener {
        void onMealSelected(Meal meal);
    }
}
