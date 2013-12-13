package solarsystem;

import java.util.ArrayList;

/**
 * ShipList.java created by aburkhar on Nov 18, 2013 at 7:25:21 PM
 */
public class ShipList {
ArrayList<Ship> listOfShips = new ArrayList<>();

public ShipList(){
 
   listOfShips.add(new Ship("Earth Orbit", 0, 250*0.06, -5, 0));
   
   
   
   
   listOfShips.add(new Ship("Jupiter", 0, 100, -13.5, 0));
   // listOfShips.add(new Ship("Ship1", 200, 0, 0.0, 19.5));
    listOfShips.add(new Ship("Saturn", 180, 0, 0.0, 18.5));
    listOfShips.add(new Ship("Middle", 0, -150, 16.0, 0.0));
      listOfShips.add(new Ship("Erratic", 0, 100, -19.0, 0)); //probably keep
    //listOfShips.add(new Ship("Ship3", 0, 100, 18.0, 0.0));
 //   listOfShips.add(new Ship("Ship3", 250, 0, 0, 21.5));
 //   listOfShips.add(new Ship("Ship1", 0, 275*0.06, -8.5, 0));//keep
    
  //  listOfShips.add(new Ship("Ship1", 0, 300*0.06, -5.5, 0)); // work with double[] xy = getXYDelta(50000, 2000000000, s.x, s.y);
}
    public Ship getShip(int i){
        //probably bad, but I'm not checking if i is in the bounds of 0-listOfShips.size();
        return listOfShips.get(i);
    }
}
