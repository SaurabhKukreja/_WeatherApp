package network.vzw.com.weatherapp.View;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import network.vzw.com.weatherapp.R;
import network.vzw.com.weatherapp.Utils.WeatherPrefrences;

public class MainActivity extends AppCompatActivity {

    WeatherPrefrences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //currently only working in Potraint mode, to prevent from crashing
        // I can use interface and to avoid any crash on hitting the API Call and orientation change
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        pref = WeatherPrefrences.getInstance(this);
        //loading search screen , if user is coming for first time
        // we can also show the current loaction , for first time
        if(pref.getPreviousLocation() == null){

            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frameLayout,new FragmentCities(),"Cities");
            transaction.commit();
        }
        else{
            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frameLayout,new FragmentMain(),"Overview");
            transaction.commit();
        }



    }
}
