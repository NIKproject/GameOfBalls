package nik.mobil.gameofballs;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class LevelSelect_Activity extends Activity {

    private MapView mapView;
    private SensorManager sensorManager;
    int x;
    int y;
    Ball ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select_);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        mapView=(MapView)findViewById(R.id.view);
        x=0;
        y=0;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //ball=new Ball(5,5); //majd a pályátó függően kéne

    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(
                listener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);


    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values=sensorEvent.values;
            mapView.BallMove(values[0],-values[1]);

            //BallMoveban van valami hiba féleség ami a program leáálását jelentiát kell nézni
            /*if(x!=values[0])
            {
                x=(int)values[0];
                ball.Move(x);
            }
            if(y!=values[0])
            {
               y=(int)values[0];
               //Mapview eltolása föl le
               //Mapview.Move(y);
            }*/

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_select_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
