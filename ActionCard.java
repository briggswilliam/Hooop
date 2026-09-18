import javax.swing.*;

public class ActionCard extends JButton{

    private static int counter = 0 ;
    private String cardName = "Parachute", playerName = "";
    private boolean done = false;

    public ActionCard(String playerName){
        super(new ImageIcon("./Assets/Jumping-Frog.png"));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(null);
        setEnabled(false);
        this.playerName = playerName;

    }
    public void nextCard(){
        counter++;
        if(counter==2){
            setSecondCard();
            cardName = "Extra Jump";
        }
        else if(counter==3){
            setThirdCard();
            cardName = "Extra Bridge";
        }
        else if(counter==4){
            setFourthCard();
            cardName = "Bridge Removal";
            counter = 0;
        }
        

        
    }
    public void setSecondCard(){
        this.setIcon(new ImageIcon("./Assets/Two-Jumps.png"));
    }
    public void setThirdCard(){
        this.setIcon(new ImageIcon("./Assets/Two-Bridges.png"));
    }
    public void setFourthCard(){
        this.setIcon(new ImageIcon("./Assets/No-Bridge.png"));
    }
    public void makeDone(){
        done = true;
    }
    public void doneCard(){
        this.setIcon(new ImageIcon("./Assets/Hooop!.png"));
        this.setDisabledIcon(new ImageIcon("./Assets/Hooop!.png"));
        this.setEnabled(false);
        makeDone();

    }
    
    public boolean isDone(){
        return done;
    }

    public int getCounter(){
        return counter;
    }   
    public String getCardName(){
        return cardName;
    }
    public String getPlayerName(){
        return playerName;
    }
    public void playerTurn(){
        this.setEnabled(true);
    }




}
