
package network.vzw.com.weatherapp.CitiesModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitiesModel {

    @SerializedName("le")
    @Expose
    private List<Le> le = null;

    public List<Le> getLe() {
        return le;
    }

    public void setLe(List<Le> le) {
        this.le = le;
    }

}
