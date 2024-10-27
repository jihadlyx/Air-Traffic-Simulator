package Airport;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

public class Plane extends Ellipse2D.Double
{
    private String name;
    private Color color;
    private boolean isClicked;
    private final double SPEED = 0.20;
    private double angle = 0;
    private double xPath, yPath, rPath;
    private boolean passBy;
    private Point2D.Double nearestPoint;

    Plane(double xPath, double yPath, double rPath) {
        Random rd = new Random();
        x = Math.abs(rd.nextInt())%1000;
        y = 50;
        width = 10;
        height = 10;
        this.xPath = xPath ;
        this.yPath = yPath ;
        this.rPath = rPath ;
        name = getNewName();
        setColor();
        nearestPoint = nearestPoint();
    }
    public void drawFill(Graphics2D g2) {
        if(!passBy) {
            Line2D.Double l1 = new Line2D.Double(x + width/2, y + height/2,
                    nearestPoint.getX(), nearestPoint.getY());
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0,
                    new float[] {3,5}, 0));
            g2.setPaint(Color.white);
            g2.draw(l1);
        }
    }
    private Point2D.Double pixelCircle() {
        double x1 = rPath * Math.cos(Math.toRadians(angle)) + xPath + rPath - 5;
        double y1 = rPath * Math.sin(Math.toRadians(angle)) + yPath + rPath - 3;
        angle = (angle + SPEED) % 360;;
        return new Point2D.Double(x1, y1);
    }
    private Point2D.Double pixelLine(Point2D.Double p) {
        double x1 = getX();
        double y1 = getY();
        double x2 = p.getX();
        double y2 = p.getY();

        double dif1 = Math.abs(x1-x2);
        double dif2 = Math.abs(y1-y2);
        int length;
        if(dif1>=dif2)
            length = (int)dif1;
        else
            length = (int)dif2;

        double xStep = dif1/length;
        double yStep = dif2/length;
        double x = x1, y = y1;
        if(x1 > x2)
            xStep *= -1;
        if(y1 > y2)
            yStep *= -1;

            x += xStep ;
            y += yStep ;
            return new Point2D.Double(x,y);
    }
    private Point2D.Double nearestPoint() {
        double space , space2;
        double x1 = rPath * Math.cos(Math.toRadians(0)) + xPath + rPath;
        double y1 = rPath * Math.sin(Math.toRadians(0)) + yPath + rPath;
        Point2D.Double point = new Point2D.Double(getX(), getY());
        Point2D.Double p = new Point2D.Double(x1, y1);
        space = point.distance(p);
        Point2D.Double point2 = p;
        for(double i = 1; i < 360; i+= SPEED) {
            x1 = rPath * Math.cos(Math.toRadians(i))+ xPath + rPath - 5;
            y1 = rPath * Math.sin(Math.toRadians(i))+ yPath + rPath - 3;
            p = new Point2D.Double(x1, y1);
            space2 = point.distance(p);
            if(space2 < space) {
                space = space2;
                point2 = p;
                angle = i;
            }
        }
        return point2;
    }
    public void update() {
        if (!passBy)
            if (x != nearestPoint.getX() && y != nearestPoint.getY()) {
                Point2D.Double p = pixelLine(nearestPoint);
                x = p.getX();
                y = p.getY();
                nearestPoint = nearestPoint();
            } else
                passBy = true;
        else{
                Point2D.Double p = pixelCircle();
                x = p.getX();
                y = p.getY();
            }
    }
    public void goDown(Point2D.Double p){
        if (x != p.getX() && y != p.getY()) {
            Point2D.Double p1 = pixelLine(p);
            x = p1.getX();
            y = p1.getY();
        }
    }
    private String getNewName() {
        int from = 65;
        int to = 90;
        Random rd = new Random();
        String s = "";
        for(int i=0;i<3;i++)
        {
            int x = Math.abs(rd.nextInt()) % (to - from + 1) + from;
            s +=(char) x;
        }
        return s + "-" + Math.abs(rd.nextInt()) %100;
    }
    public String getName() {
        return name;
    }
    public void setColor() {
        if(isClicked)
            this.color = new Color(250, 255, 20);
        else
            this.color = new Color(128,237,153);
    }
    public boolean getClick() {
        return isClicked;
    }
    public void setClick(boolean p) {
        this.isClicked = p;
    }
    public boolean isClicked(double x, double y) {
        Rectangle2D r1 = this.getBounds2D();
        if (r1.outcode(x, y) == 0) {
            if (getClick())
                isClicked = false;
            else
                isClicked = true;
            return true;
        }
        return false;
    }
    public boolean hoverMouse(double x, double y){
        Rectangle2D r1 = this.getBounds2D();
        if(r1.outcode(x, y) == 0)
            return true;
        return false;
    }
    public Color getColor() {
        return color;
    }
    public void changeLocation(Point2D.Double p) {
        x = p.getX();
        y = p.getY();
    }

}
