package org.firstinspires.ftc.teamcode.utils;

import com.seattlesolvers.solverslib.util.InterpLUT;

public class LookUpTable {
    InterpLUT rpm = new InterpLUT();
    InterpLUT hood = new InterpLUT();
    InterpLUT time = new InterpLUT();
    public LookUpTable(){

        rpm.add(26.8622, 1220);
        hood.add(26.8622, 0.33);
        time.add(26.8622, 0.95);

        rpm.add(36.0094, 1240);
        hood.add(36.0094, 0.4);
        time.add(36.0094, 0.93);

        rpm.add(46.8638, 1280);
        hood.add(46.8638, 0.45);
        time.add(46.8638, 0.95);

        rpm.add(55.0981, 1300);
        hood.add(55.0981, 0.49);
        time.add(55.0981, 0.79);


        rpm.add(63.0217, 1340);
        hood.add(63.0217, 0.51);
        time.add(63.0217, 0.86);


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