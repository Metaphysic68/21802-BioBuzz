package org.firstinspires.ftc.teamcode.utils;

import com.seattlesolvers.solverslib.util.InterpLUT;

public class LookUpTable {
    InterpLUT rpm = new InterpLUT();
    InterpLUT hood = new InterpLUT();
    InterpLUT time = new InterpLUT();
    public LookUpTable(){

        // New values from firm table
        rpm.add(42.45, 2610);
        hood.add(42.45, 0.4);
        time.add(42.45, 0.46);


        rpm.add(52.11, 2710);
        hood.add(52.11, 0.34);
        time.add(52.11, 0.51);


        rpm.add(62.5, 2810);
        hood.add(62.5, 0.3);
        time.add(62.5, 0.56);


        rpm.add(72.44, 2920);
        hood.add(72.44, 0.25);
        time.add(72.44, 0.61);


        rpm.add(85.88, 3060);
        hood.add(85.88, 0.21);
        time.add(85.88, 0.67);


        rpm.add(107.53, 3240);
        hood.add(107.53, 0.18);
        time.add(107.53, 0.74);


        rpm.add(124.86, 3500);
        hood.add(124.86, 0.17);
        time.add(124.86, 0.82);


        rpm.add(145.5, 3800);
        hood.add(145.5, 0.17);
        time.add(145.5, 0.92);


        rpm.add(155.2, 3900);
        hood.add(155.2, 0.17);
        time.add(155.2, 0.97);



        rpm.createLUT();
        hood.createLUT();
        time.createLUT();

    }

    public double getRPM(double distance){
        return rpm.get(distance);
    }

    public double getHood(double distance){
        return hood.get(distance);
    }

    public double getTime(double distance){
        return time.get(distance);
    }

}