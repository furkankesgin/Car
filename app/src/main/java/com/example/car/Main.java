package com.example.car;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends AppCompatActivity {

    ////////////////DEFINITIONS\\\\\\\\\\\\\\\\\\\\\

        /////Buttons\\\\\\\\\\\
    Button btnGas;
    Button btnBrake;
    Button btnStart;
    Button btnFill;
    Button btnStatus;
    Button btnLiter;

        //////TextViews\\\\\\\\
    TextView tvPanel;
    TextView tvGas;
    TextView tvKSYA;
    TextView tvKm;
    TextView tvLiter;

        ////Values\\\\\\\\\\
    long second=0;
    long num1, num2;
    float Liter = (float) 0.06;
    double fKEY = (float) 0.0;
    double fWaytogo =(float) 0.0;
    double fBurnedLiter =(float) 0.0;
    double fRemainingLiter =(float) 0.0;
    DecimalFormat df = new DecimalFormat("#0.##");

    Car car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        car = new Car();
        car.model = "Lamborghini";
        car.motorCC = (5000);
        car.Start();

        ////Timer for current status
        final Timer timer=new Timer();

        ///////////Connect frond-end to Backend\\\\\\\\\\\
        btnGas = (Button) findViewById(R.id.lbtnGas);
        btnStart = (Button) findViewById(R.id.lbtnStart);
        btnBrake = (Button) findViewById(R.id.lbtnBrake);
        tvKSYA = (TextView) findViewById(R.id.ltvKSYA);
        tvLiter = (TextView) findViewById(R.id.ltvLiter);
        btnLiter = (Button)  findViewById(R.id.lbtnGasoline);
        btnStatus=(Button) findViewById(R.id.lbtnStatus);
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatus();
            }
        });

        btnLiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvKm.equals(5)){
                    tvLiter.setText("1");
                }
                else if (tvKm.equals(10)){
                    tvLiter.setText("2");
                }
            }
        });
        tvKm = (TextView) findViewById(R.id.ltvKm);
        btnFill = (Button) findViewById(R.id.lbtnGasoline);
        btnFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                car.takeGas(120);

            }
        });
        tvGas = (TextView) findViewById(R.id.ltvGas);
        tvPanel = (TextView) findViewById(R.id.ltxtStatus);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        second++;
                        fWaytogo= (float) fKEY+fWaytogo;
                        fBurnedLiter=(float) (fKEY*car.getSpeed()/10)/100;   //100 mean 100km
                        car.UsedPetrol(fBurnedLiter);

                    }
                },1,1000);

                if (btnStart.getText().equals("START")){
                    car.Start();
                    btnGas.setEnabled(true);
                    btnBrake.setEnabled(true);
                    btnStart.setText("STOP");
                    btnStart.setBackgroundColor(0xFF00FF00);
                    tvGas.setEnabled(true);


                }
                else if (car.getSpeed()==0)
                {
                    btnGas.setEnabled(false);
                    btnBrake.setEnabled(false);
                    btnStart.setText("START");
                    btnStart.setBackgroundColor(0xFF00FFFF);
                    tvGas.setEnabled(false);
                }
            }


        });
        btnGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (car.getGas()<=0) {
                    Toast.makeText(Main.this, "Lütfen benzin alın!!", Toast.LENGTH_LONG).show();
                }

                else {
                    car.Gas();
                    showStatus();

                }


            }
        });


        btnBrake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car.Break();
            }
        });

    }

    public void showStatus(){
        tvPanel.setText(String.valueOf(car.getSpeed()));
        if(car.getGas()<0){
            tvPanel.setText("0");
        }
        tvKm.setText(String.valueOf(fWaytogo));
        tvKm.setText( df.format(fWaytogo));
        if(car.getSpeed()<=0){
            tvKm.setText("0");
        }

        tvKSYA.setText(String.valueOf(second));
        tvGas.setText(String.valueOf(car.getSpeed()/10));
        if (car.getSpeed()<=0){
            tvGas.setText("0");
        }
        if(car.getGas()<=0){

        }
        tvLiter.setText(String.valueOf(car.getGas()));
        if(car.getGas()<0){
            tvLiter.setText("0");
        }
        if (car.getSpeed()>199){
            tvKSYA.setText(String.valueOf(second));
        }


    }

}
