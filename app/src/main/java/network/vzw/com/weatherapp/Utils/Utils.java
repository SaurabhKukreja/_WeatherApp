package network.vzw.com.weatherapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.ArrayList;

/**
 * Created by kukresa on 10/23/2017.
 */

public class Utils {
    public static String API_KEY= "a8556323186c66c681519cfecd3ef2af";
    public static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q";
    public static String BASE_ICON_URL = "http://openweathermap.org/img/w/";
    public ArrayList<String> cityName;
    public static boolean isNetworkConnected(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public ArrayList<String> getCityName() {
        return cityName;
    }

    public void setCityName(ArrayList<String> cityName) {
        this.cityName = cityName;
    }
}
