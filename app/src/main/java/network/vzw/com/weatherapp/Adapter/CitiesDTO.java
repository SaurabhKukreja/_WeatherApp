package network.vzw.com.weatherapp.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kukresa on 10/23/2017.
 */

public class CitiesDTO implements Parcelable {


        @Expose
        @SerializedName("cityName")
        String cityName;

    public CitiesDTO(String cityName) {
        this.cityName = cityName;
    }
    public CitiesDTO(){
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
    }
    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }

    protected CitiesDTO(Parcel in) {
        cityName = in.readString();
    }
    public static final Creator<CitiesDTO> CREATOR = new Creator<CitiesDTO>() {
        @Override
        public CitiesDTO createFromParcel(Parcel in) {
            return new CitiesDTO(in);
        }

        @Override
        public CitiesDTO[] newArray(int size) {
            return new CitiesDTO[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
