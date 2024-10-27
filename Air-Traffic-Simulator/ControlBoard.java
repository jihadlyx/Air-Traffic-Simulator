package Airport;

import javax.swing.*;
import java.awt.*;
public class ControlBoard extends JPanel {
    private String[] nameButton = new String[5];
    private JButton[] buttons;
    private JTextArea[] textAreas;
    private JCheckBox[] checkBoxes;
    final int margin = 10;
    private int x, y, w, h;
    private Font font;
    ControlBoard() {
        textAreas = new JTextArea[2];
        setBackground(new Color(0xD1E7F8));
        font = new Font("Arial", Font.BOLD, 15);
        setLayout(null);
        x = margin; y = margin; w = 320; h = 200;
        this.add(initTextArea(0, "text-1"));
        y += h; h = 30;
        this.add(initCheckBox("Show"));
        y += h; w -= margin;
        this.add(initButton());
        y += h+margin; w += margin; h = 200;
        this.add(initTextArea(1, "text-2"));
    }
    private JPanel initTextArea(int index, String name){
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setLayout(new GridLayout(1, 0));
        textAreas[index] = new JTextArea(10, 26);
        textAreas[index].setLineWrap(true);
        textAreas[index].setFocusable(false);
        textAreas[index].setName(name);
        textAreas[index].setBackground(new Color(0x444444));
        textAreas[index].setForeground(Color.white);
        textAreas[index].setFont(new Font("Arial", Font.BOLD, 15));
        textAreas[index].setMargin(new Insets(10,10,10,10));
        JScrollPane scrollPane = new JScrollPane(textAreas[index], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setForeground(Color.red);
        panel.setBounds(x, y, w, h);
        panel.add(scrollPane);
        return panel;
    }
    private JPanel initButton(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        nameButton = new String[]{"go Down", "Start"}; // add button
        buttons = new JButton[nameButton.length];
        for(int i = 0; i < nameButton.length; i++){
            buttons[i] = new JButton(nameButton[i]);
            buttons[i].setFont(font);
            buttons[i].setForeground(Color.white);
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(0x5353FF));
            buttons[i].setCursor(new Cursor(12));
            panel.add(buttons[i]);
        }
        panel.setBounds(x,y, w, h);
        return panel;
    }
    private JPanel initCheckBox(String...s){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0,0,0,0));
        panel.setLayout(new GridLayout(1,2));
        checkBoxes = new JCheckBox[s.length];
        for(int i = 0; i < s.length; i++){
            checkBoxes[i] = new JCheckBox(s[i]);
            checkBoxes[i].setFocusable(false);
            checkBoxes[i].setBackground(new Color(0xD1E7F8));
            panel.add(checkBoxes[i]);
        }
        panel.setBounds(x, y, w,h);
        return panel;
    }

    public JButton getButton(int index){
        return buttons[index];
    }
    public JTextArea getTextArea(int index){
        return textAreas[index];
    }
    public JCheckBox getCheckBox(int index){
        return checkBoxes[index];
    }
}
