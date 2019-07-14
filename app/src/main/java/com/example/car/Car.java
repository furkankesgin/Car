package com.example.car;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Car {

    ////////Definitions\\\\\\
    String model;
    private int speed;
    int motorCC;
    private float gas;

    public void Start(){
        speed = 0;
        gas = 0;
        ////Save file to sdcard and which name\\\\\\\\\
        File sdCard = Environment.getDataDirectory();
        File file = new File(sdCard, "myGas.txt");
        StringBuilder text = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            //////Read file\\\\\\\\\
            while((line = br.readLine()) != null){
                text.append(line);
            }
            //////Reader Close\\\\\\\\
            br.close();
            System.out.println("Gas readed from file ==> " + text+"<==");
            if (text == null){

                text.append("0");
            }
            System.out.println("New Gas ==> " + text+" <==");
            ///////send text to gas with converting flaot
            gas = Float.parseFloat(String.valueOf(text));
        }catch (IOException e){

            System.out.println("ERROR MORE DETAIL IS HERE ====> " + e.toString());
        }
    }
/////////Methods\\\\\\\\\\\\\\\
    public void Gas(){
        speed = speed + 5;
        if (speed > 200){
            speed = 200;
        }
    }

    public void Break(){
        speed = speed -5;
        if (speed < 0){
            speed = 0;
        }
    }

    public int getSpeed(){return speed;}
    public float getGas(){return gas;}
    public void setGas(){
        /////Control of the file existence.
        File sdCard = Environment.getDataDirectory();
        try {
            File myFile = new File(sdCard, "myGas.txt");
            if (!myFile.exists())
            {
                System.out.println("File Does not exist, created!");
                myFile.createNewFile();
            }
            else {
                System.out.println("File was exist");
            }

            ////////write from file to writer
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(String.valueOf(gas));
            myOutWriter.close();
        }
        catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
    }
    public float UsedPetrol(double UsedGas){
        gas = (float) (gas-UsedGas);
        setGas();
        return gas;
    }
    public void takeGas(int iTakeLiter) {
        gas = gas + iTakeLiter;
        setGas();
    }
}

