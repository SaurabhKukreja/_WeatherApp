package network.vzw.com.weatherapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import network.vzw.com.weatherapp.Adapter.CitiesDTO;

/**
 * Created by kukresa on 10/23/2017.
 */

public class WeatherPrefrences {

    private SharedPreferences preferences = null;
    private String PREFERENCES = "SpeedTest";
    SharedPreferences.Editor editor = null;
    private static WeatherPrefrences weatherPrefrences;
    private final String PREVIOUS_LOCATION = "previousLocation";
    private static final String SIZE = "size";
    private String TAG_CITIES = ".cities";

    private WeatherPrefrences(Context ctx){
        preferences = ctx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

    }

    public static WeatherPrefrences getInstance(Context ctx){
        if (weatherPrefrences == null){
            weatherPrefrences = new WeatherPrefrences(ctx);
        }
        return weatherPrefrences;
    }

    public void setPreviousLocation( String randomDeviceId) {
        editor = preferences.edit();
        editor.putString(PREVIOUS_LOCATION, randomDeviceId);
        editor.commit();
    }

    public String getPreviousLocation() {
        String uuid = preferences.getString(PREVIOUS_LOCATION, null);
        return uuid;
    }
    public boolean saveRecord(CitiesDTO report) {
        try {
            int size = preferences.getInt(SIZE, 0);
            editor = preferences.edit();
            String citiesGson = (new Gson()).toJson(report);
            editor.putString(String.valueOf(size) + TAG_CITIES, citiesGson);
            size++;
            editor.putInt(SIZE, size);
            return editor.commit();
        } catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }  catch (JsonSyntaxException e){
            e.printStackTrace();
            return false;
        }
    }
    public CitiesDTO getReportRecord(int index) {
        CitiesDTO dto = null;
        try {
            dto = (new Gson()).fromJson(
                    preferences.getString(String.valueOf(index) + TAG_CITIES, ""), CitiesDTO.class);
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        return dto;
    }
    public int getSize(){
        return preferences.getInt(SIZE, 0);
    }

}
