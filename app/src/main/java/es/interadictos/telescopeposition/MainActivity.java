package es.interadictos.telescopeposition;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.location.LocationListener;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        this.prepareGPS();
    }

    public void setLocation(String text) {
        TextView textPosition = (TextView)findViewById(R.id.textPosition);
        textPosition.setText(text);
    }

    public void setAltitude(String text) {
        TextView textAltitude = (TextView)findViewById(R.id.textAltitude);
        textAltitude.setText(text);
    }

    public void prepareGPS() {
        if (this.checkPermission()) {
            LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            telescopePostionLocationListener mlocListener = new telescopePostionLocationListener();
            mlocListener.setMainActivity(this);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) mlocListener);
        }
        else {
            this.setMessageError("GPS no está activo");
        }
    }

    private boolean checkPermission() {
        int resultPermissionFineLocation = ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION);
        int resultPermissionLocationExtraCommands = ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        this.setMessageLog("Hola hola "+resultPermissionFineLocation + " - " + resultPermissionLocationExtraCommands);
        if (resultPermissionFineLocation != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.prepareGPS();
                }
                else {

                }
                return;
            }
        }
    }

    public void setMessageError(String text) {
        TextView textError = (TextView)findViewById(R.id.textError);
        textError.setText(text);
    }

    public void setMessageLog(String text) {
        Log.d(TAG, text);
    }
}
