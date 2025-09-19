package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener,
        EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    private int editingPosition = -1;

    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityEdit(int ignored, String newName, String newProvince) {
        /* Two if cases: one for city and one for province
        * If the city is not filled in, it keeps the city the same
        * If the province is not filled in, it keeps the province the same
        * */
        if (editingPosition >= 0 && editingPosition < dataList.size()) {
            City city = dataList.get(editingPosition);
            if (newName != null && !newName.trim().isEmpty()) {
                city.setName(newName);
            }
            if (newProvince != null && !newProvince.trim().isEmpty()) {
                city.setProvince(newProvince);
            }
            cityAdapter.notifyDataSetChanged();
        }
        editingPosition = -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};

        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            editingPosition = position;
            new EditCityFragment().show(getSupportFragmentManager(), "EditCity");
        });

    FloatingActionButton fab = findViewById(R.id.button_add_city);
    fab.setOnClickListener(v -> {new AddCityFragment().show(getSupportFragmentManager(),
            "Add City");});
    }

}