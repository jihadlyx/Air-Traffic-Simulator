package Airport;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAction implements KeyListener {
    protected boolean upPressed, downPressed, leftPressed, rightPressed, goDown;
    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_G);
            goDown = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)
            upPressed = true;
        if(key == KeyEvent.VK_S)
            downPressed = true;
        if(key == KeyEvent.VK_A)
            leftPressed = true;
        if(key == KeyEvent.VK_D)
            rightPressed = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)
            upPressed = false;
        if(key == KeyEvent.VK_S)
            downPressed = false;
        if(key == KeyEvent.VK_A)
            leftPressed = false;
        if(key == KeyEvent.VK_D)
            rightPressed = false;
    }
    public void setGoDown(boolean p){
        this.goDown = p;
    }
}
