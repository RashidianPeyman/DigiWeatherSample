
package sample.com.digiweathersample.data.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Main extends RealmObject {

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;
    @SerializedName("sea_level")
    @Expose
    private Double seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private Double grndLevel;

    public String getTemp() {
        return convertTemp(temp);
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return String.valueOf(humidity);
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getTempMin() {
        return convertTemp(tempMin);
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return convertTemp(tempMax);
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    private String convertTemp(double temperature){

        // C= K-273.15 -> kelvin to centigrade
        return String.valueOf(Math.round(temperature-273.15));
    }

}
