package org.firstinspires.ftc.teamcode.utils;

public abstract class Filter {
    public abstract double update(double value);
    public abstract double getValue();
    public abstract double getRawValue();
    public abstract void forceSet(double value);
}