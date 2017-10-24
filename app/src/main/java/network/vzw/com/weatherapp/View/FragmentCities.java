package network.vzw.com.weatherapp.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import network.vzw.com.weatherapp.Adapter.CitiesAdapter;
import network.vzw.com.weatherapp.Adapter.CitiesDTO;
import network.vzw.com.weatherapp.CitiesModel.CitiesModel;
import network.vzw.com.weatherapp.CitiesModel.Le;
import network.vzw.com.weatherapp.Controller.Controller;
import network.vzw.com.weatherapp.R;
import network.vzw.com.weatherapp.Utils.MyService;
import network.vzw.com.weatherapp.Utils.WeatherPrefrences;


/**
 * Created by kukresa on 10/23/2017.
 */

public class FragmentCities extends Fragment {

    AutoCompleteTextView search;
    View view;
    private WeatherPrefrences pref;
    CitiesDTO citiesDTO;
    private ArrayList<CitiesDTO> citiesList = null;
    ArrayList<String> cityNameList;
    ListView listView;
    Gson gson ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cities,container,false);
        init();
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG","Selected "+parent.getItemAtPosition(position).toString());
                getTempratureData(parent.getItemAtPosition(position).toString());
                citiesDTO = new CitiesDTO();
                citiesDTO.setCityName(parent.getItemAtPosition(position).toString());
                pref.saveRecord(citiesDTO);


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.textView);
                String text = textView.getText().toString();
                Log.d("TAG","Selected "+text);
                getTempratureData(text);
            }
        });
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        //Loading City list , I wanted to do in a better way ,
        // need to look more into, how to load this much data on runtime
        // 1 solution is to start a service on app start and load the data later
        // 2 put progress bar while on fragment
        // this technique is bit Lagging for now,
        cityNameList = Controller.getInstance().getAutoCompleteAdapter(gson,getContext());
    }

    private void getTempratureData(String cityName) {

        Bundle bundle = new Bundle();
        bundle.putString("cityName", cityName);
        FragmentMain fragmentMain = new FragmentMain();
        fragmentMain.setArguments(bundle);
        FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frameLayout,fragmentMain,"Overview");
        transaction.commit();
        pref.setPreviousLocation(cityName);
    }

    private void init() {

        pref = WeatherPrefrences.getInstance(getContext());
        search = (AutoCompleteTextView)view.findViewById(R.id.searchCity);
        listView = (ListView) view.findViewById(R.id.addedCities);
        setRecentItems();

         try {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter< String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    cityNameList );
            search.setThreshold(1);
            search.setAdapter(arrayAdapter);
            Log.d("TAG"," Adapter Complete");
        } catch (Exception e) {
             Log.d("TAG","Exception here");
            e.printStackTrace();
        }
    }

    private void setRecentItems(){

        citiesList = new ArrayList<CitiesDTO>();
        for (int i = 0; i < pref.getSize(); i++) {
            CitiesDTO citiesDTO = pref.getReportRecord(i);
            citiesList.add(citiesDTO);
        }
        Collections.reverse(this.citiesList);
        CitiesAdapter adapter = new CitiesAdapter(getContext(), citiesList);
        listView.setAdapter(adapter);

    }

}
