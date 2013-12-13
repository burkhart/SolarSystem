package solarsystem;

import java.awt.Color;

/**
 * PlanetList.java created by aburkhar on Nov 18, 2013 at 7:25:09 PM
 */
public class PlanetList {

    static Planet[] listOfPlanets = new Planet[8];

    public PlanetList() {
       // listOfPlanets[0] = new Planet("Name", orbital period, distance from sun (10^6km), diameter (km), color, mass (10^24kg), xVal, yVal;
        listOfPlanets[0] = new Planet("Mercury", 88.0, 58, 4880, Color.GREEN, 0.330, 1.0, 0, 0);
        listOfPlanets[1] = new Planet("Venus", 225.0, 108, 12104, Color.MAGENTA, 4.87, 3.0, 0, 0);
        listOfPlanets[2] = new Planet("Earth", 365.0, 150, 12756, Color.BLUE, 5.97, 2.0, 0, 0);
        listOfPlanets[3] = new Planet("Mars", 687.0, 228, 6792, Color.lightGray, 0.642, 0.5, 0, 0);
        listOfPlanets[4] = new Planet("Jupiter", 4331.0, 779, 142984, Color.PINK, 1898, 4.5, 0, 0);
        listOfPlanets[5] = new Planet("Saturn", 10747.0, 1434, 120536, Color.RED, 568, 3.0, 0, 0);
        listOfPlanets[6] = new Planet("Uranus", 30589.0, 2873, 51118, Color.CYAN, 86.8, 2.5, 0, 0);
        listOfPlanets[7] = new Planet("Neptune", 59800.0, 4495, 49528, Color.WHITE, 102, 5.2, 0, 0);
        
    }
    public Planet getPlanet(int i){
        return listOfPlanets[i];
    }
}
