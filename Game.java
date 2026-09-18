import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JFrame implements ActionListener{

    private JPanel panel;
    private JLabel backgroundLabel;
    private JButton startGame;
    private JTextField name1, name2, name3, name4;
    private ImageIcon gameBg;
    private int NumberOfPlayers;
    private String player1Name, player2Name, player3Name, player4Name;
    private JCheckBox aiCheck, aiCheck2, aiCheck3, aiCheck4, easy, easy2, easy3, easy4, hard, hard2, hard3, hard4;
    private Player p1, p2, p3, p4;
        private final Font fontStyle2, fontStyle3, fontStyle4;

    public Game(int numberOfPlayers) {
        this.NumberOfPlayers = numberOfPlayers;

        JFrame frame = new JFrame();

        fontStyle2 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",52f);
        fontStyle3 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf", 86f);
        fontStyle4 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf", 30f);

        if(numberOfPlayers == 4) {
            gameBg = new ImageIcon("./Assets/Main-Menu-4.jpg");
        } 
        else {
            gameBg = new ImageIcon("./Assets/Main-Menu-3.jpg");
        }

        backgroundLabel = new ScaledImageLabel(gameBg.getImage());

        name1 = new JTextField("");
        name1.setFont(fontStyle2);
        name1.setBounds(200, 470,575,80);
        name1.setHorizontalAlignment(JTextField.CENTER);
        name1.setOpaque(false);
        name1.setForeground(Color.WHITE);
        name1.setBorder(null);

        aiCheck = new JCheckBox("AI Player?");
        aiCheck.setFont(fontStyle4);
        aiCheck.setBounds(175, 535, 300, 80);
        aiCheck.setOpaque(false);
        aiCheck.setForeground(Color.WHITE);
        aiCheck.setFocusPainted(false);
        aiCheck.setBorder(null);
        aiCheck.addActionListener(this);

        easy = new JCheckBox("Easy");
        easy.setFont(fontStyle4);
        easy.setBounds(400, 535, 100, 80);
        easy.setOpaque(false);
        easy.setForeground(Color.WHITE);
        easy.setFocusPainted(false);
        easy.setBorder(null);
        easy.addActionListener(this);
        backgroundLabel.add(easy);
        easy.setVisible(false);

        hard = new JCheckBox("Hard");
        hard.setFont(fontStyle4);
        hard.setBounds(575, 535, 100, 80);
        hard.setOpaque(false);
        hard.setForeground(Color.WHITE);
        hard.setFocusPainted(false);
        hard.setBorder(null);
        hard.addActionListener(this);
        backgroundLabel.add(hard);
        hard.setVisible(false);

        name2 = new JTextField("");
        name2.setFont(fontStyle2);
        name2.setBounds(1125, 470,575,80);
        name2.setHorizontalAlignment(JTextField.CENTER);
        name2.setOpaque(false);
        name2.setForeground(Color.WHITE);
        name2.setBorder(null);

        aiCheck2 = new JCheckBox("AI Player?");
        aiCheck2.setFont(fontStyle4);
        aiCheck2.setBounds(1100, 535, 300, 80);
        aiCheck2.setOpaque(false);
        aiCheck2.setForeground(Color.WHITE);
        aiCheck2.setFocusPainted(false);
        aiCheck2.setBorder(null);
        aiCheck2.addActionListener(this);

        easy2 = new JCheckBox("Easy");
        easy2.setFont(fontStyle4);
        easy2.setBounds(1325, 535, 100, 80);
        easy2.setOpaque(false);
        easy2.setForeground(Color.WHITE);
        easy2.setFocusPainted(false);
        easy2.setBorder(null);
        easy2.addActionListener(this);
        backgroundLabel.add(easy2);
        easy2.setVisible(false);

        hard2 = new JCheckBox("Hard");
        hard2.setFont(fontStyle4);
        hard2.setBounds(1500, 535, 100, 80);
        hard2.setOpaque(false);
        hard2.setForeground(Color.WHITE);
        hard2.setFocusPainted(false);
        hard2.setBorder(null);
        hard2.addActionListener(this);
        backgroundLabel.add(hard2);
        hard2.setVisible(false);

        backgroundLabel.add(name1);
        backgroundLabel.add(aiCheck);
        backgroundLabel.add(name2);
        backgroundLabel.add(aiCheck2);

        if(numberOfPlayers == 4) {
            name3 = new JTextField("");
            name3.setFont(fontStyle2);
            name3.setBounds(200, 680,575,80);
            name3.setHorizontalAlignment(JTextField.CENTER);
            name3.setOpaque(false);
            name3.setForeground(Color.WHITE);
            name3.setBorder(null);

            aiCheck3 = new JCheckBox("AI Player?");
            aiCheck3.setFont(fontStyle4);
            aiCheck3.setBounds(175, 745, 300, 80);
            aiCheck3.setOpaque(false);
            aiCheck3.setForeground(Color.WHITE);
            aiCheck3.setFocusPainted(false);
            aiCheck3.setBorder(null);
            aiCheck3.addActionListener(this);

            easy3 = new JCheckBox("Easy");
            easy3.setFont(fontStyle4);
            easy3.setBounds(400, 745, 100, 80);
            easy3.setOpaque(false);
            easy3.setForeground(Color.WHITE);
            easy3.setFocusPainted(false);
            easy3.setBorder(null);
            easy3.addActionListener(this);
            backgroundLabel.add(easy3);
            easy3.setVisible(false);

            hard3 = new JCheckBox("Hard");
            hard3.setFont(fontStyle4);
            hard3.setBounds(575, 745, 100, 80);
            hard3.setOpaque(false);
            hard3.setForeground(Color.WHITE);
            hard3.setFocusPainted(false);
            hard3.setBorder(null);
            hard3.addActionListener(this);
            backgroundLabel.add(hard3);
            hard3.setVisible(false);
    
            name4 = new JTextField("");
            name4.setFont(fontStyle2);
            name4.setBounds(1125, 680,575,80);
            name4.setHorizontalAlignment(JTextField.CENTER);
            name4.setOpaque(false);
            name4.setForeground(Color.WHITE);
            name4.setBorder(null);

            aiCheck4 = new JCheckBox("AI Player?");
            aiCheck4.setFont(fontStyle4);
            aiCheck4.setBounds(1100, 745, 300, 80);
            aiCheck4.setOpaque(false);
            aiCheck4.setForeground(Color.WHITE);
            aiCheck4.setFocusPainted(false);
            aiCheck4.setBorder(null);
            aiCheck4.addActionListener(this);

            easy4 = new JCheckBox("Easy");
            easy4.setFont(fontStyle4);
            easy4.setBounds(1325, 745, 100, 80);
            easy4.setOpaque(false);
            easy4.setForeground(Color.WHITE);
            easy4.setFocusPainted(false);
            easy4.setBorder(null);
            easy4.addActionListener(this);
            backgroundLabel.add(easy4);
            easy4.setVisible(false);

            hard4 = new JCheckBox("Hard");
            hard4.setFont(fontStyle4);
            hard4.setBounds(1500, 745, 100, 80);
            hard4.setOpaque(false);
            hard4.setForeground(Color.WHITE);
            hard4.setFocusPainted(false);
            hard4.setBorder(null);
            hard4.addActionListener(this);
            backgroundLabel.add(hard4);
            hard4.setVisible(false);

            backgroundLabel.add(name3);
            backgroundLabel.add(aiCheck3);
            backgroundLabel.add(name4);
            backgroundLabel.add(aiCheck4);
        }

        startGame = new JButton("Start Game");
        startGame.setFont(fontStyle3);
        startGame.setBounds(660, 840,595,135);
        styleButton(startGame);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(backgroundLabel);
        backgroundLabel.add(startGame);
        frame.add(panel);


        frame.setSize(1920,1080);
        ScreenScaler.scaleFrame(frame);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
	    frame.setResizable(false);
	    frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }

    private void styleButton (JButton b){
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if(aiCheck.isSelected()){
            easy.setVisible(true);
            hard.setVisible(true);
        }
        if(aiCheck2.isSelected()){
            easy2.setVisible(true);
            hard2.setVisible(true);
        }

        if(!aiCheck.isSelected()){
            easy.setVisible(false);
            easy.setSelected(false);
            hard.setVisible(false);
            hard.setSelected(false);
        }
        if(!aiCheck2.isSelected()){
            easy2.setVisible(false);
            easy2.setSelected(false);
            hard2.setVisible(false);
            hard2.setSelected(false);
        }

        if(easy.isSelected()){
            hard.setSelected(false);
        }
        if(hard.isSelected()){
            easy.setSelected(false);
        }

        if(easy2.isSelected()){
            hard2.setSelected(false);
        }
        if(hard2.isSelected()){
            easy2.setSelected(false);
        }

        if(NumberOfPlayers == 4){
            if(aiCheck3.isSelected()){
                easy3.setVisible(true);
                hard3.setVisible(true);
            }
            if(aiCheck4.isSelected()){
                easy4.setVisible(true);
                hard4.setVisible(true);
            }

            if(!aiCheck3.isSelected()){
                easy3.setVisible(false);
                easy3.setSelected(false);
                hard3.setVisible(false);
                hard3.setSelected(false);
            }
            if(!aiCheck4.isSelected()){
                easy4.setVisible(false);
                easy4.setSelected(false);
                hard4.setVisible(false);
                hard4.setSelected(false);
            }

            if(easy3.isSelected()){
                hard3.setSelected(false);
            }
            if(hard3.isSelected()){
                easy3.setSelected(false);
            }

            if(easy4.isSelected()){
                hard4.setSelected(false);
            }
            if(hard4.isSelected()){
                easy4.setSelected(false);
            }
        }

        if (e.getSource()==startGame) {

            if (name1.getText().isEmpty()){
            player1Name = "Player 1";
            }
            else{
                player1Name = name1.getText();
            }
            if(aiCheck.isSelected()){
                if(easy.isSelected()){
                    p1 = new AIPlayer(player1Name, "Easy");
                }
                if(hard.isSelected()){
                    p1 = new AIPlayer(player1Name, "Hard");
                }
            }
            else{
                p1 = new Player(player1Name);
            }
            
            if (name2.getText().isEmpty()){
                player2Name = "Player 2";
            }
            else{
                player2Name = name2.getText();
            }
            if(aiCheck2.isSelected()){
                if(easy2.isSelected()){
                    p2 = new AIPlayer(player2Name, "Easy");
                }
                if(hard2.isSelected()){
                    p2 = new AIPlayer(player2Name, "Hard");
                }
            }
            else{
                p2 = new Player(player2Name);
            }
            if (NumberOfPlayers >2){
                if (name3.getText().isEmpty()){
                    player3Name = "Player 3";
                }
                else{
                    player3Name = name3.getText();
                }
                if(aiCheck3.isSelected()){
                    if(easy3.isSelected()){
                        p3 = new AIPlayer(player3Name, "Easy");
                    }
                    if(hard3.isSelected()){
                        p3 = new AIPlayer(player3Name, "Hard");
                    }
                }
                else{
                    p3 = new Player(player3Name);
                }

                if (name4.getText().isEmpty()){
                    player4Name = "Player 4";
                }
                else{
                    player4Name = name4.getText();
                }
                if(aiCheck4.isSelected()){
                    if(easy4.isSelected()){
                        p4 = new AIPlayer(player4Name, "Easy");
                    }
                    if(hard4.isSelected()){
                        p4 = new AIPlayer(player4Name, "Hard");
                    }
                }
                else{
                    p4 = new Player(player4Name);
                }
            }

            // Start the game with the selected number of players
            this.dispose();
            Board Board = new Board(p1.getPlayers(), NumberOfPlayers);
            // You can add code here to transition to the game screen
        }
    }
}


