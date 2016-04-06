package com.example.e10a324k.tpandroid1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;


public class MyActivity extends Activity implements SensorEventListener {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    private SensorManager mSensorManager;

    private Sensor mSensor;
    private Sensor mLight;
    private Sensor mGravity;
    private Sensor mGyroscope;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showtemp(View view) {
        AlertDialog.Builder bob = new AlertDialog.Builder(this);
        bob.setTitle("lololololol");
        String s = "";
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sens : deviceSensors) {
            s = s + (sens.getName() + "\n");
        }
        bob.setMessage(s);
        AlertDialog dialog = bob.create();
        dialog.show();
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        if(event.sensor == mLight) {
            float lux = event.values[0];
            TextView view = (TextView) findViewById(R.id.counter1);
            view.setText(Float.toString(lux));
        } else if(event.sensor == mGravity) {
            float val = event.values[0];
            TextView view2 = (TextView) findViewById(R.id.counter2);
            view2.setText(Float.toString(val));

        } else {

            float val1 = event.values[0];
            float val2 = event.values[1];
            float val3 = event.values[2];
            TextView view3 = (TextView) findViewById(R.id.counter3);
            view3.setText(Float.toString(val1) + " + " + Float.toString(val2) + " + " + Float.toString(val3));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
