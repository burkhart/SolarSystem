package solarsystem;

import java.awt.Color;

/**
 * Planet.java created by aburkhar on Nov 18, 2013 at 7:45:22 PM
 */
public class Planet {

    public String name;
    public double daysPerRevolutionAroundSun;
    public int distanceFromSun;
    public int planetRadius;
    public Color planetColor;
    public double mass;
    public double diff;
    public double x;
    public double y;

    public Planet(String n, double dpras, int dfs, int planetDiameter, Color color, double m, double d, double xx, double yy) {
        name = n;
        daysPerRevolutionAroundSun = dpras;
        distanceFromSun = dfs;//  times 10^6km
        planetRadius = planetDiameter / 2;//km
        planetColor = color;
        mass = m;// times 10^24kg
        diff = d;//the theta difference that we will add to make the panets not start lined up
        x = xx;
        y = yy;
    }

    public void setXY(double xx, double yy){
        x = xx;
        y=yy;
    }
    public double getMass() {
        return mass;
    }

    public String toString() {
        String returnMe = "I am a Planet, please fill in my variables so I can be debugged.";

        return returnMe;
    }
}
