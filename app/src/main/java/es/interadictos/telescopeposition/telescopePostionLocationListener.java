package es.interadictos.telescopeposition;

import android.icu.text.DecimalFormat;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by mikel on 11/04/17.
 */

public class telescopePostionLocationListener implements LocationListener{
    MainActivity mainActivity;

    private static final char DEGREE_SYMBOL= 0x00B0;
    private static final String MINUTES_SYMBOL= "'";
    private static final String SECONDS_SYMBOL= "\"";

    public MainActivity getMainActivity() {
        return  mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        // Este método se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la detección de un cambio de ubicación
        Double dLatitude = loc.getLatitude();
        Double dLongitude = loc.getLongitude();

        String hLat = "N";
        String hLon = "O";

        if (dLatitude < 0) {
            hLat = "S";
        }

        if (dLongitude < 0) {
            hLon = "E";
        }

        Integer latDegrees = (int) Math.floor(Math.abs(dLatitude));
        Integer latMinutes = (int) Math.floor((dLatitude - latDegrees) * 60);
        Integer latSecondsResult = (int) Math.floor(((((dLatitude - latDegrees) * 60) - latMinutes) * 60) * 100);
        Double latSeconds = (double) latSecondsResult / 100;
        String latitude = latDegrees + "" + DEGREE_SYMBOL + " " + latMinutes + MINUTES_SYMBOL + " " + latSeconds + SECONDS_SYMBOL + " " + hLat;

        Integer lonDegrees = (int) Math.floor(Math.abs(dLongitude));
        Integer lonMinutes = (int) Math.floor((Math.abs(dLongitude) - lonDegrees) * 60);
        Integer lonSecondsResult = (int) Math.floor(((((Math.abs(dLongitude) - lonDegrees) * 60) - lonMinutes) * 60) * 100);
        Double lonSeconds = (double) lonSecondsResult / 100;
        String longitude = lonDegrees + "" + DEGREE_SYMBOL + " " + lonMinutes + MINUTES_SYMBOL + " " + lonSeconds + SECONDS_SYMBOL + " " + hLon;

        String Text = latitude + " - " + longitude;
        this.mainActivity.setLocation(Text);
        this.mainActivity.setAltitude("" + loc.getAltitude() + " metros");
        //this.mainActivity.setMessageLog(Text);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este método se ejecuta cuando el GPS es desactivado
        this.mainActivity.setMessageError("GPS desactivado");
        //this.mainActivity.setMessageLog("GPS desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este método se ejecuta cuando el GPS es activado
        this.mainActivity.setMessageError("GPS activado");
        //this.mainActivity.setMessageLog("GPS activado");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Este método se ejecuta cada vez que se detecta un cambio en el
        // status del proveedor de localizacion (GPS)
        // Los diferentes Status son:
        // OUT_OF_SERVICE -> Si el proveedor está fuera de servicio
        // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
        // espera que esté disponible en breve
        // AVAILABLE -> Disponible
        //this.mainActivity.setLocation("" + status);
        //this.mainActivity.setMessageLog("" + status);
    }
}
