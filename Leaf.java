import java.awt.event.*;
import javax.swing.*;

public class Leaf extends JButton implements ActionListener{
    private boolean occupied = false;
    public Leaf(){
        super(new ImageIcon("./Assets/Leaf.png"));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(null);

    }
    
    public int getXcoord(JButton button){
        //returns the x coordinate of the center of the leaf button
        int x = button.getX() + button.getWidth()/2;
        return x;
    }

    public int getYcoord(JButton button){
        //returns the y coordinate of the center of the leaf button
        int y = button.getY() + button.getHeight()/2;
        return y;
    }
    public void setOccupied(){
        occupied = true;
    }
    public void clearOccupied(){
        occupied = false; 
    }

    public Boolean isOccupied(){
        return occupied;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Leaf clicked");

    }



}
