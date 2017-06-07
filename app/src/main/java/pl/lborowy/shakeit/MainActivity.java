package pl.lborowy.shakeit;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @BindView(R.id.main_accTxt)
    TextView accText;

    @BindView(R.id.cameraTxt)
    TextView cameraText;

    @BindView(R.id.fingerprintTxt)
    TextView fingerText;

    @BindView(R.id.locationTxt)
    TextView locationText;

    @BindView(R.id.mojaNazwaObiektuId)
    LinearLayout dummyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //// TODO: init sensor manager and sensor
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        accText = (TextView)findViewById(R.id.main_accTxt);
        if (getSystemService(CAMERA_SERVICE) != null) {
            cameraText.setText("Kamera dostępna!\n");
        }
        else {
            cameraText.setText("Kamera niedostepna!\n");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkFingerService();
        }
        if (getSystemService(LOCATION_SERVICE) != null) {
            locationText.setText("Usługi lokalizacji dostępne!\n");
        }
        else {
            locationText.setText("Usługi lokalizacji niedostępne!\n");
        }

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFingerService() {
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        if (fingerprintManager != null && fingerprintManager.isHardwareDetected()) {
            fingerText.setText("Czytnik linii papilarnych jest dostepny!\n");
            //use fingerprintManager
        } else {
            fingerText.setText("Czytnik linii papilarnych jest niedostepny\n");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //// TODO: unregister listener
        mSensorManager.unregisterListener(MainActivity.this);
        Log.d("Lifecycle", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //// TODO: register listener
        mSensorManager.registerListener(MainActivity.this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Lifecycle", "onResume");
    }

    @Override // zaimportowane metody z interfejsu SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            // wyświetlenie informacji w logach
            String output = "x = " + x + "\ny = " + y + "\nz = " + z;
            Log.d("ACC", output);
            accText.setText(output);

            // wyswietlanie kolorow
            int r = (int)Math.abs(x)*20;
            int g = (int)Math.abs(y)*20;
            int b = (int)Math.abs(z)*20;

            int color = Color.rgb(r,g,b);
            int negativeColor = Color.rgb(255-r,255-g,255-b);

            accText.setBackgroundColor(color);
            accText.setTextColor(negativeColor);

        }
    }

    @Override // zaimportowane metody z interfejsu SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
