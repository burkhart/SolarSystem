package solarsystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

public class SolarSystemView extends JComponent implements Observer {

    // Make the sun this proportion of screen DEFAULT_WIDTH
//    public static final double SUN_RADIUS_PROPORTION = 0.02;
    //   private static final double EARTH_RADIUS_PROPORTION = .3 * SUN_RADIUS_PROPORTION;
    //  private static final double EARTH_DISTANCE_PROPORTION_SCREEN = 0.4;
    private static final int defaultSize = 1040;
    private static final int distanceProportion = 1;//changes the distance between planets and the sun Bigger number = more zoomed in
    private static final double planetRadiusProportion = 1100; // changes the size of the planets Bigger number = smaller planets
    private static final double TWO_PI = 2.0 * Math.PI;
    static double proportionalityConstant = .00001;
    static int selectedShip = 0;
    private SolarSystemModel model;
    PlanetList planetList = new PlanetList();
    static ShipList shipList = new ShipList();
    Ship previousShip;
    static boolean planetGravityEnabled = false;

    public SolarSystemView(SolarSystemModel model) {
        super();
        this.model = model;
        setPreferredSize(new Dimension(defaultSize, defaultSize));
    }

    private void drawSpaceBackdrop(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    public void step() {
        model.setDay(model.getDay() + 1);
    }

    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        step();
    }

    private void drawSun(Graphics2D g2) {
        int sunRadius = (int) (1391000 * proportionalityConstant);
        // int sunRadius = (int) (SUN_RADIUS_PROPORTION * getWidth());
        GradientPaint sunColor = new GradientPaint(0, 0, Color.YELLOW, 0, sunRadius, Color.RED);
        g2.setPaint(sunColor);
        g2.fillOval(-sunRadius / 2, -sunRadius / 2, sunRadius, sunRadius);
    }

    private void drawShips(Graphics2D g2) {
        int shipDiameter = 8;
        if (shipList.listOfShips.size() >= selectedShip) {
            // for (int i = 0; i < shipList.listOfShips.size(); i++) { //if i want to paint all the ships at once
            Ship s = shipList.getShip(selectedShip);

            double[] xy = getXYDelta(50000, 5000000, s.x, s.y, 0, 0); // original works for the sun
            s.addToVelocity(((int) xy[0]) - s.x, ((int) xy[1]) - s.y);
            if (planetGravityEnabled == true) { //Iterate gravity funtion over planet list
                for (int a = 0; a < 8; a++) {
                    xy = getXYDelta(20, (int) planetList.getPlanet(a).mass / 5, s.x, s.y, 0, 0);
                    s.addToVelocity(((int) xy[0]) - s.x, ((int) xy[1]) - s.y);
                }
            }

            s.computeXYFromVelocityAndCurrentPosition();
            g2.setColor(Color.red);
            int[] addToTrails = {(int) (s.x), (int) (s.y)};
            ArrayList<int[]> trail = s.trails(addToTrails);
            int[] ship;
            int trailRadius = 4;
            for (int f = 0; f < trail.size(); f++) {
                ship = trail.get(f);
                g2.fillOval((int) (ship[0] * 1000000 * proportionalityConstant - trailRadius / 2), (int) (ship[1] * 1000000 * proportionalityConstant - trailRadius / 2), trailRadius, trailRadius);
            }
            g2.fillOval((int) (s.x * 1000000 * proportionalityConstant - shipDiameter / 2), (int) (s.y * 1000000 * proportionalityConstant - shipDiameter / 2), shipDiameter, shipDiameter);
            g2.setColor(Color.white);
        }
    }

    public double[] getXYDelta(int mass1, int mass2, double x, double y, double x2, double y2) {//this method is so fucked

        x -= x2;
        y -= y2;
        double hypotenuse = (Math.sqrt((Math.pow(x, 2)) + (Math.pow(y, 2))));
//        System.out.println("hypotenuse = " + hypotenuse);
        double hypotenuseDelta = ((0.00000000006674) * (mass1 * mass2) / (Math.pow(hypotenuse, 2)));
//        System.out.println("hypotenuseDelta = " + hypotenuseDelta);
        double proportion = hypotenuseDelta / hypotenuse * 100;
//        System.out.println("proportion = " + proportion);
        double xDelta = x - (x * proportion) / 500000;
//        System.out.println("xDelta = " + xDelta);
        double yDelta = y - (y * proportion) / 500000;
//        System.out.println("yDelta = " + yDelta);
//        System.out.println("Hypotenuse from x,y deltas is: " + Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2)));
        double[] xy = {xDelta + x2, yDelta + y2};
        return xy;
    }

    private void savePlanetXY(Planet p, double theta, int distanceFromSun) {
        // System.out.println("X = " + (0+Math.cos(theta)*distanceFromSun));
        // System.out.println("Y = "+ (0-Math.sin(theta)*distanceFromSun));
        p.setXY(0 + Math.cos(-theta) * distanceFromSun, 0 - Math.sin(-theta) * distanceFromSun);


    }

    private void drawPlanets(Graphics2D g2) {
        for (int i = 0; i < 8; i++) {
            Planet p = planetList.getPlanet(i);
            double planetTheta = map(model.getDay(), 0, p.daysPerRevolutionAroundSun, 0, TWO_PI, p.diff);
            g2.rotate(planetTheta);
            int distanceFromPlanetToSun = (int) ((p.distanceFromSun * Math.pow(10, 6)) * proportionalityConstant * 0.06);
            savePlanetXY(p, planetTheta, distanceFromPlanetToSun * 2);

            g2.translate(distanceFromPlanetToSun * 2, 0);

            int planetR = (int) (p.planetRadius * proportionalityConstant * planetRadiusProportion);

            g2.setPaint(p.planetColor);
            g2.fillOval(-planetR / 2, -planetR / 2, planetR, planetR);//draw the planet
            g2.drawString(p.name, planetR / 2 + 1, 0);
            g2.translate(-distanceFromPlanetToSun * 2, 0);
            g2.drawOval(0 - distanceFromPlanetToSun * 2, 0 - distanceFromPlanetToSun * 2, distanceFromPlanetToSun * 4, distanceFromPlanetToSun * 4);//draw its orbit
            g2.rotate(-planetTheta);
            //  g2.drawOval((int)p.x-planetR / 2, (int)p.y-planetR / 2, planetR+4, planetR+4); //test to make sure the saveXY function worked
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawSpaceBackdrop(g2);
        // Set the origin to be in the center of the screen
        g2.translate(getWidth() / 2, getHeight() / 2);
        drawSun(g2);

        drawPlanets(g2);
        drawShips(g2);
        run();
    }

    public static void main(String[] args) {
        System.out.println("Entered SolarSystemView");
        JFrame frame = new JFrame("Solar System");
        final SolarSystemModel model = new SolarSystemModel();
        final SolarSystemView view = new SolarSystemView(model);

        model.addObserver(view);

        JPanel panel = new JPanel();
        panel.add(view);
        //  panel.setLayout(null);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        final JSlider zoomSlider = new JSlider(0, 25);
        final JComboBox shipSelectComboBox;
        // final Checkbox planetGravity = new Checkbox("g");
        final JButton planetGravity = new JButton("gOFF");
        zoomSlider.setPaintLabels(true);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setMajorTickSpacing(5);
        zoomSlider.setValue(5);
        proportionalityConstant = 5 * 0.0000006;
        panel.add(zoomSlider);
        String[] shipListSelect = {"Earth", "Jupiter", "Sorta Saturn", "Middle", "Erratic Orbit"};
        panel.add(shipSelectComboBox = new JComboBox(shipListSelect));
        panel.add(planetGravity);
        planetGravity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent f) {
                if (planetGravity.getText().equals("gOFF")) {
                    planetGravity.setText("gON");
                    planetGravityEnabled = true;
                } else if (planetGravity.getText().equals("gON")) {
                    planetGravity.setText("gOFF");
                    planetGravityEnabled = false;

                }
                shipList.listOfShips.get(selectedShip).clearData();
            }
        });
        shipSelectComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shipList.listOfShips.set(0, new Ship("Earth Orbit", 0, 250 * 0.06, -5, 0));
                shipList.listOfShips.set(1, new Ship("Jupiter", 0, 100, -13.5, 0));
                shipList.listOfShips.set(2, new Ship("Saturn", 180, 0, 0.0, 18.5));
                shipList.listOfShips.set(3, new Ship("Middle", 0, -150, 16.0, 0.0));
                shipList.listOfShips.set(4, new Ship("Erratic", 0, 100, -19.0, 0));

                selectedShip = shipSelectComboBox.getSelectedIndex();
            }
        });
        zoomSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int val = zoomSlider.getValue() + 1;
                proportionalityConstant = val * 0.0000006;
            }
        });
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        repaint();
    }

    /**
     * @param value The incoming value to be converted
     * @param low1 Lower bound of the value's current range
     * @param high1 Upper bound of the value's current range
     * @param low2 Lower bound of the value's target range
     * @param high2 Upper bound of the value's target range
     */
    public static final double map(double value, double low1, double high1, double low2, double high2, double offset) {

        double diff = value - low1;
        double proportion = (diff / (high1 - low1));
        return lerp(low2, high2, proportion, offset);
    }

    // Linearly interpolate between two values
    public static final double lerp(double value1, double value2, double amt, double offset) {
        return ((value2 - value1) * amt) + value1 + offset;
    }
}
