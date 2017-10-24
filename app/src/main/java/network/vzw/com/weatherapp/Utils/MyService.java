package network.vzw.com.weatherapp.Utils;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import network.vzw.com.weatherapp.CitiesModel.CitiesModel;
import network.vzw.com.weatherapp.CitiesModel.Le;

/**
 * Created by kukresa on 10/23/2017.
 */

public class MyService extends IntentService{

    Gson gson;
    public MyService(){
        super("My Service");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("TAG","On Handle Intent");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        setAutoCompleteAdapter(getCityList());
    }
    private String getCityList() {

        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("city.list.json");
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
    private void setAutoCompleteAdapter(String cityJSON) {

        CitiesModel citiesModel = gson.fromJson(cityJSON, CitiesModel.class);
        ArrayList<String> cityName = new ArrayList<String>();
        List<Le> listLe = citiesModel.getLe();
        for(int i = 0;i<listLe.size();i++){

            cityName.add(listLe.get(i).getName());
        }

        Intent intent = new Intent("CITY_LIST");
        intent.putStringArrayListExtra("CITY_NAME",cityName);
        /*Utils utils = new Utils();
        utils.setCityName(cityName)*/;
        sendBroadcast(intent);

        /*try {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter< String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    cityName );
            search.setThreshold(1);
            search.setAdapter(arrayAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
