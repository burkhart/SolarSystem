package solarsystem;
 
import java.util.Observable;

public class SolarSystemModel extends Observable {
 
    private int day;

    public int getDay() {
        return day;
    }

        public void setDay(int day) {
        this.day = day;
            setChanged();
            notifyObservers();
    }
}
