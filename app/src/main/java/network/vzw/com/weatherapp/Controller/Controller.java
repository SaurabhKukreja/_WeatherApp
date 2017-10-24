package network.vzw.com.weatherapp.Controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import network.vzw.com.weatherapp.CitiesModel.CitiesModel;
import network.vzw.com.weatherapp.CitiesModel.Le;

/**
 * Created by kukresa on 10/24/2017.
 */

public class Controller {

    private static Controller instance = null ;

    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

    public String getTime(){
        try {
            String date  = new SimpleDateFormat("EEE, MMMM dd, HH:mm")
                    .format(new Date());
            return date;
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return "NA";
    }

    public ArrayList<String> getAutoCompleteAdapter(Gson gson, Context context) {

        Log.d("TAG","Starting Adapter");
        String cityJSON = getCityList(context);
        CitiesModel citiesModel = gson.fromJson(cityJSON, CitiesModel.class);
        ArrayList<String> cityName = new ArrayList<String>();
        List<Le> listLe = citiesModel.getLe();
        for(int i = 0;i<listLe.size();i++){

            cityName.add(listLe.get(i).getName());
        }

        return cityName;

    }
    private String getCityList(Context context) {

        String json = null;
        try {

            InputStream is = context.getAssets().open("city.list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
