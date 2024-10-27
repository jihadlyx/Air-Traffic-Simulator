package Airport;

import javax.swing.*;

public class MainPanel extends JPanel {

    public MainPanel(int width, int height){
        this.setLayout(null);

        ControlBoard controlBoard = new ControlBoard();
        PlayerPlane playerPlane = new PlayerPlane(controlBoard);
        playerPlane.setBounds(0,0,width-350, height);
        controlBoard.setBounds(playerPlane.getWidth(), 0,width-playerPlane.getWidth(), height);
        this.add(playerPlane);
        this.add(controlBoard);
//        playerPlane.start();
        this.setVisible(true);
    }
}
