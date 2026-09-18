import javax.swing.*;

public class Bridge extends JButton{
    public boolean isRestored = false;
    public boolean isRemoved = false;


    public Bridge(){
        super(new ImageIcon("./Assets/bridge-hor.jpg"));
        //setOpaque(false);
        //setContentAreaFilled(false);
        //setBorderPainted(false);
        //setFocusPainted(false);
        //setBorder(null);
        setEnabled(false);
        setDisabledIcon(new ImageIcon("./Assets/bridge-hor.jpg"));
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
    public void changeImage(){
        this.setIcon(new ImageIcon("./Assets/bridge-Vertical.jpg"));
        this.setDisabledIcon(new ImageIcon("./Assets/bridge-Vertical.jpg"));

    }
    public void placeHorBridge(){
        this.setIcon(new ImageIcon("./Assets/bridge-hor.jpg"));
    }
    public void placeVerBridge(){
        this.setIcon(new ImageIcon("./Assets/bridge-Vertical.jpg"));
    }



}
