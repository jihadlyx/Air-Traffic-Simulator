package Airport;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;

public class PlayerPlane extends JPanel implements ActionListener {
    private ArrayList<Plane> planes;
    private ArrayList<Plane> runwayPlanes;
    final double FBS = 60;
    private MouseAction mouseAction;
    private KeyAction keyAction;
    private Graphics2D g2;
    private double xPath = 150, yPath = 150, rPath = 200;
    private boolean start;
    private boolean show;
    private int numberintersectes = 0;
    private int numberGoDown = 0;
    private ControlBoard controlBoard;
    private JButton buttonStart;
    private  JButton goDown;
    private JCheckBox checkBox;
    private Thread thread;
    PlayerPlane(ControlBoard controlBoard) {
//        start = false;
        this.controlBoard = controlBoard;
        planes = new ArrayList<>();
        runwayPlanes = new ArrayList<>();
        mouseAction = new MouseAction();
        buttonStart = controlBoard.getButton(1);
        buttonStart.addActionListener(this);
        goDown = controlBoard.getButton(0);
        goDown.addActionListener(this);
        checkBox = controlBoard.getCheckBox(0);
        checkBox.addActionListener(this);
        this.addMouseListener(mouseAction);
        this.addMouseMotionListener(mouseAction);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }
    public void paintComponent(Graphics g) {
        g2 = (Graphics2D) g;
//        g2 =(Graphics2D) getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        background();
        drawAirport();
        drawPlane();

        g2.dispose();
    }
    public void start() {
        double drawInterval = 1000000000/FBS;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(start) {
                    long startTime = System.nanoTime();
                    long time = System.nanoTime() - startTime;
                    if(time < drawInterval){
                        double sleep = (drawInterval - time) / 1000000;
                        sleep((long)sleep);
                    }
                }
            }
        });
        addPlane();
        isClicked();
//        hoverMouse();
        controlPlane();
        thread.start();
    }
    private void drawPlane() {
        for(int i = 0; i < planes.size(); i++) {
            Plane plane = planes.get(i);
            if (plane != null) {
                if(show)
                    plane.drawFill(g2);
                g2.setPaint(plane.getColor());
                g2.fill(plane);
                g2.drawString(plane.getName(), (int) plane.getX() - 5, (int) plane.getY() - 3);
            }
            drawLine();
        }
        for (int i = 0; i < runwayPlanes.size(); i++){
            Plane plane = runwayPlanes.get(i);
            if (plane != null) {

                g2.setPaint(plane.getColor());
                g2.fill(plane);
                g2.drawString(plane.getName(), (int) plane.getX() - 5, (int) plane.getY() - 3);
            }
        }
    }
    private void addPlane() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(start) {
                    Plane plane = new Plane(xPath, yPath, rPath);
                    planes.add(plane);
                    repaint();
                    sleep(1000);
                }
            }
        }).start();
    }
    private void drawLine() {
        Line2D.Double line = new Line2D.Double(0,0, 0,0);
        for(int i = 0; i < planes.size(); i++)
            if(planes.get(i) != null && planes.get(i).getClick()) {
                Plane plane = planes.get(i);
                Point2D.Double point = new Point2D.Double(plane.getX()+5, plane.getY()+5);
                if(keyAction.upPressed)
                    line = new Line2D.Double(point,new Point2D.Double(point.getX(), 0));
                if(keyAction.downPressed)
                    line = new Line2D.Double(point,new Point2D.Double(point.getX(), getHeight()));
                if(keyAction.leftPressed)
                    line = new Line2D.Double(point,new Point2D.Double(0, point.getY()));
                if(keyAction.rightPressed)
                    line = new Line2D.Double(point,new Point2D.Double(getWidth(), point.getY()));
                g2.setPaint(planes.get(i).getColor());
                g2.draw(line);
            }
    }
    private void isClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    double x = mouseAction.getXMouse;
                    double y = mouseAction.getYMouse;
                    if(x != 0 && y != 0)
                    if(x != 0 && y != 0)
                        for(int i = 0; i<planes.size();i++){
                            Plane plane = planes.get(i);
                            if (plane != null)
                                if (plane.isClicked(x, y)) {
                                    setClicked(i);
                                    plane.setColor();
                                    repaint();
                                    break;
                                }
                        }
                    for(int i = 0; i<runwayPlanes.size();i++){
                        Plane plane = runwayPlanes.get(i);
                        if (plane != null)
                            if (plane.isClicked(x, y)) {
                                setClicked(i);
                                plane.setColor();
                                repaint();
                                break;
                            }
                    }
                    mouseAction.getXMouse = 0;
                    mouseAction.getYMouse = 0;
                    sleep(1000/60);
                }
            }
        }).start();
    }
    private void controlPlane() {
        keyAction = new KeyAction();
        this.addKeyListener(keyAction);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(start) {
                    double x, y;
                    double move = 2;
                    Plane plane1 = null;
                    for (int i = 0; i < planes.size(); i++) {
                        Plane plane = planes.get(i);
                        if (planes.get(i) != null && plane.getClick()) {
                            x = plane.getX();
                            y = plane.getY();
                            if (keyAction.upPressed) {
                                y -= move;
                            }
                            if (keyAction.downPressed) {
                                y += move;
                            }
                            if (keyAction.leftPressed){
                                x -= move;
                            }
                            if (keyAction.rightPressed) {
                                x += move;
                            }
                            if(keyAction.goDown)
                                plane1 = plane;
                            plane.x = x;
                            plane.y = y;
                            repaint();
                        }
                    }
                    intersects();
                    for(int i = 0; i < planes.size(); i++) {
                        Plane plane = planes.get(i);
                        if(plane != null) {
                            if(plane1 != plane)
                                plane.update();
                            else{
                                double x1 = xPath+rPath+90*0.8 - plane1.width/2,
                                       y1 = yPath+rPath-plane1.height/2;
                                plane1.goDown(new Point2D.Double(x1, y1));
                                if (plane1.x==x1 && plane1.y == y1){
                                    keyAction.goDown = false;
                                    runwayPlanes.add(plane1);
                                    JTextArea textArea = controlBoard.getTextArea(0);
                                    numberGoDown++;
                                    textArea.setText("Number Planes : " + numberGoDown
                                            + "\nPlane get Off : \n\tName : " + plane1.getName()
                                            + "\n\t true");
                                    planes.remove(plane1);
                                    plane1 = null;
                                }
                            }
                            repaint();
                        }
                    }
                    for (int i = 0; i < runwayPlanes.size(); i++){
                        Plane p = runwayPlanes.get(i);
                        double x1 = xPath+rPath-45, y1 = yPath+rPath-2;
                        if(p != null) {
                            p.goDown(new Point2D.Double(x1, y1));
                            if(p.getX() == x1 && p.getY() == y1)
                                runwayPlanes.remove(p);
                            repaint();
                        }
                    }
                    sleep(1000 / 60);
                }
            }
        }).start();
    }
    private void intersects() {
        for(int i = 0; i < planes.size(); i++){
            Plane plane = planes.get(i);
            if (plane != null)
                for(int j = 0; j < planes.size(); j++) {
                    if (i != j) {
                        Rectangle2D r1 = plane.getBounds2D();
                        Rectangle2D r2 = planes.get(j).getBounds2D();
                        if (r1.intersects(r2)) {
                            JTextArea textArea = controlBoard.getTextArea(1);
                            numberintersectes+=2;
                            textArea.setText("Number Planes : " + numberintersectes
                                    + "\nPlanes intersects : \n\tName : " + plane.getName()
                                    + "\n\tName : " + planes.get(j).getName()
                                    + "\n\t False");
                            planes.remove(planes.get(j));
                            planes.remove(planes.get(i));
                        }
                    }
                }
        }
    }
    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {}
    }
    private void background() {
        g2.setPaint(new Color(60, 60, 60));
        g2.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
        Ellipse2D.Double e1 = new Ellipse2D.Double(xPath, yPath, rPath*2, rPath*2);
        g2.setPaint(new Color(232, 51, 51, 223));
        g2.draw(e1);
    }
    private void drawAirport(){
        double x = xPath+rPath, y = yPath+rPath, w = 90, h = 25;
        Rectangle2D.Double r1 = new Rectangle2D.Double(x-w/2, y-h/2, w, h);
        g2.setPaint(new Color(255, 255, 255));
        Area a1 = new Area(r1);
        r1 = new Rectangle2D.Double(x+1/2.0*w/2, y+0.5*h, 15, 20);
        a1.add(new Area(r1));
        g2.setPaint(new Color(0xB2B2B2));
        g2.fill(a1);
        g2.setPaint(new Color(255, 255, 255));
        g2.draw(a1);
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0,
                new float[] {5,5}, 0));
        Line2D.Double l1 = new Line2D.Double(x-w/2, y, x+w/2, y);
        g2.draw(l1);
        Ellipse2D.Double e1 = new Ellipse2D.Double(x+w*0.8, y, 2,2);
        g2.fill(e1);
    }
    private void setClicked(int index) {
        for (int i = 0; i < planes.size() || i < runwayPlanes.size(); i++) {
            if(i != index) {
                planes.get(i).setClick(false);
                planes.get(i).setColor();
                if(i < runwayPlanes.size()) {
                    runwayPlanes.get(i).setClick(false);
                    runwayPlanes.get(i).setColor();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonStart){
            if(start){
                start = false;
                buttonStart.setText("Start");
            }
            else {
                start = true;
                buttonStart.setText("Fales");
            }
            start();
        }
        if(e.getSource() == goDown){
            if(!keyAction.goDown){
                keyAction.setGoDown(true);
                repaint();
            }
        }
        if(checkBox.isSelected())
            show = true;
        else
            show = false;
        repaint();

    }
}
