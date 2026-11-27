package org.firstinspires.ftc.teamcode.utils;

import com.seattlesolvers.solverslib.util.InterpLUT;

public class LookUpTable {
    InterpLUT rpm = new InterpLUT();
    InterpLUT hood = new InterpLUT();
    InterpLUT time = new InterpLUT();
    public LookUpTable(){




       /* rpm.add(27.3097, 1180);
        hood.add(27.3097, 0.295); //FUNCIONAL
        time.add(27.3097, 0.93);


        rpm.add(30.287, 1200);
        hood.add(30.287, 0.35); //FUNCIONAL
        time.add(30.287, 0.93);

        rpm.add(37.1246, 1220);
        hood.add(37.1246, 0.4254);//FUNCIONAL
        time.add(37.1246, 0.93);

        /*rpm.add(45.8520, 1240);
        hood.add(45.8520, 0.479);  //FUNCIONAL
        time.add(45.8520, 0.93);*/

        rpm.add(35.6085, 1220);
        hood.add(35.6085, 0.28);
        time.add(35.6085, 0.93);

        rpm.add(44.863, 1240);
        hood.add(44.863, 0.33);
        time.add(44.863, 0.93);

        rpm.add(50.8468, 1280);
        hood.add(50.8468, 0.38);
        time.add(50.8468, 0.93);

        rpm.add(56.3812, 1340);
        hood.add(56.3812, 0.4);
        time.add(56.3812, 0.93);

        rpm.add(60.4544, 1360);
        hood.add(60.4544, 0.43);
        time.add(60.4544, 0.93);

        /*rpm.add(50.8777, 1280);
        hood.add(50.8777, 0.545); /FUNCIONAL
        time.add(50.8777, 0.95);*/




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