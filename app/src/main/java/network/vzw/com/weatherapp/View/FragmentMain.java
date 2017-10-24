package network.vzw.com.weatherapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import network.vzw.com.weatherapp.Controller.Controller;
import network.vzw.com.weatherapp.Utils.HttpNetworkHandler;
import network.vzw.com.weatherapp.WeatherModel.WeatherResponse;
import network.vzw.com.weatherapp.WeatherModel.Weather;
import network.vzw.com.weatherapp.R;
import network.vzw.com.weatherapp.Utils.Utils;
import network.vzw.com.weatherapp.Utils.WeatherPrefrences;

/**
 * Created by kukresa on 10/23/2017.
 */

public class FragmentMain extends Fragment implements View.OnClickListener{

    TextView cities,date,cityName,temprature,message,sunrise,sunset;
    TextView tempMin,tempMax,humidity,wind;
    View view;
    WeatherPrefrences pref;
    RequestQueue queue;
    private Gson gson;
    ProgressBar progressBar;
    ImageView weatherImage;
    String cityNameValue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_main,container,false);
        init();
        cities.setOnClickListener(this);
        queue = Volley.newRequestQueue(getContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        return view;

    }

    private void init() {
        pref = WeatherPrefrences.getInstance(getContext());
        cities = view.findViewById(R.id.cities);
        date = view.findViewById(R.id.date);
        cityName = view.findViewById(R.id.cityName);
        temprature = view.findViewById(R.id.temprature);
        message = view.findViewById(R.id.message);
        sunrise = view.findViewById(R.id.sunrise);
        sunset = view.findViewById(R.id.sunset);
        tempMin = view.findViewById(R.id.tempMin);
        tempMax = view.findViewById(R.id.tempMax);
        humidity = view.findViewById(R.id.humidity);
        wind= view.findViewById(R.id.wind);
        progressBar = view.findViewById(R.id.progressBar);
        weatherImage = view.findViewById(R.id.imageView);
        progressBar.setVisibility(View.GONE);
        Bundle bundle = getArguments();

        if(bundle != null){
            cityNameValue= bundle.getString("cityName");
        }
        else {
            cityNameValue = pref.getPreviousLocation();
        }

        getWeatherData(cityNameValue);

    }

    private void updateUI(String cityNameValue,WeatherResponse weatherResponse) {

        cityName.setText(cityNameValue);
        date.setText(Controller.getInstance().getTime());
        if(weatherResponse != null ){
            List<Weather> weather = weatherResponse.getWeather();
            String description = weather.get(0).getDescription();
            String icon = weather.get(0).getIcon();
            String iconUrl = Utils.BASE_ICON_URL+icon+".png";
            String sunriseTime = convertTime(weatherResponse.getSys().getSunrise());
            String sunsetTime = convertTime(weatherResponse.getSys().getSunset());
            String tempMaxVal = weatherResponse.getMain().getTempMax().toString();
            String tempMinVal = weatherResponse.getMain().getTempMin().toString();
            String humidityValue = weatherResponse.getMain().getHumidity().toString();
            String windValue = weatherResponse.getWind().getSpeed().toString();
            temprature.setText(weatherResponse.getMain().getTemp().toString());
            message.setText(description);
            sunrise.setText(sunriseTime);
            sunset.setText(sunsetTime);
            tempMax.setText(tempMaxVal);
            tempMin.setText(tempMinVal);
            humidity.setText(humidityValue);
            wind.setText(windValue);
            Picasso.with(getContext())
                    .load(iconUrl)
                    .into(weatherImage);

            progressBar.setVisibility(View.GONE);
        }
        else{
            Toast.makeText(getContext(),"Got some error, try again later",Toast.LENGTH_LONG).show();
        }

    }


    private void getWeatherData(String cityName){

        if(Utils.isNetworkConnected(getContext())) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.bringToFront();
            final String url = Utils.BASE_URL + "=" + cityName + "&appid="+ Utils.API_KEY;
            HttpNetworkHandler.getInstance(getContext()).requests(Request.Method.GET, "", url, onPostsLoaded, onPostsError, false);
        }
        else{
            Toast.makeText(getContext(),"No Network",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cities:

                //It will lag here, because of loading of cities JSON
                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout,new FragmentCities(),"cities");
                transaction.commit();
                break;
        }
    }
    private final Response.Listener<JSONObject> onPostsLoaded = new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {

            Log.d("TAG","Response: "+response);
            WeatherResponse responseData = parseWeatherResponse(response.toString());
            updateUI(cityNameValue,responseData);
        }
    };

    private WeatherResponse parseWeatherResponse(String response) {


        WeatherResponse weatherResponse = gson.fromJson(response, WeatherResponse.class);
        return weatherResponse;

    }
    private String convertTime(int time){
        try {
            Date date = new Date(time);
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        }
        catch (Exception e){
            return "NA";
        }
    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("TAG", error.toString());
            updateUI(cityNameValue , null);
        }
    };

}
