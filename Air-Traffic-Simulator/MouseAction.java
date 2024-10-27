package Airport;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseAction implements MouseListener, MouseMotionListener {
    protected double getXMouse;
    protected double getYMouse;
    protected double getXMouse1;
    protected double getYMouse1;

    @Override
    public void mouseClicked(MouseEvent e) {
        this.getXMouse = e.getX() ;
        this.getYMouse = e.getY() ;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        getXMouse1 = e.getX();
        getYMouse1 = e.getY();
    }
}
