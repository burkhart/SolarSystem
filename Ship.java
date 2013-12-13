package solarsystem;

import java.util.ArrayList;

/**
 * Ship.java created by aburkhar on Nov 18, 2013 at 7:45:31 PM
 */
public class Ship {

   public String name;
   public double x;
   public double y;
   public double xDelta;
   public double yDelta;
   ArrayList<int[]> Previous = new ArrayList<>();
   public int[] trailsPair;
   public Ship(String n, double xcoord, double ycoord, double xVelocity, double yVelocity){
       name = n;
       x = xcoord;
       y = ycoord;
       xDelta = xVelocity;
       yDelta = yVelocity;
       
   }
   public void setXY(int xx, int yy){
       x=xx;
       y=yy;
   }
   public void addToVelocity(double xAdd, double yAdd){//call this method multiple times; once for each gravitational object
      // System.out.println("xDelta = " + xDelta);
      // System.out.println("xAdd = " + xAdd);
       xDelta+=xAdd;
      // System.out.println("xDelta = " + xDelta);
       yDelta+=yAdd;       
   }
   public void computeXYFromVelocityAndCurrentPosition(){//call this to use the precomputed velocity to compute position for next timestep
       x+=(int)xDelta;
       y+=(int)yDelta;
   }
   public ArrayList<int[]> trails(int[] pair){
       Previous.add(pair);
       return Previous;
   }
   public void clearData(){
       Previous.clear();
   }
}
