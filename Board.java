import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class Board extends JFrame implements ActionListener {

    private final JLayeredPane pane;
    private final JLabel backgroundLabel, leafsLabel, baseLabel, popupLabel, saveLabel, blueScoreLabel, redScoreLabel, yellowScoreLabel, purpleScoreLabel;
    public String player1, player2, player3, player4, playerColor, playerTurn, turn;
    private static int currentIndex = 0, leafCount = 0;
    private final ImageIcon leafsBg,baseBg,popup,gameBg, saveIcon, blueScoreIcon, redScoreIcon
    , yellowScoreIcon, purpleScoreIcon;
    private final int playerCount;
    private String[] playerNames;
    private final String[] playerColors = {"Blue", "Yellow","Red","Purple"},playerColors2 = {"Blue","Yellow"},
    baseLeaves = {"leaf 23","leaf 3","leaf 15","leaf 11"}, baseLeaves2 = {"leaf 23","leaf 3"};
    private JButton MoveAFrog, PlaceABridge,SaveGame, blueHome, redHome, yellowHome, purpleHome;
    private final Font fontStyle1, fontStyle2, fontStyle3;
    private static Frog[] blueFrogs, yellowFrogs, redFrogs, purpleFrogs;
    private final ArrayList<Bridge> removedHorList = new ArrayList<>();
    private final ArrayList<Bridge> removedVerList = new ArrayList<>();
    private Frog selectedFrog = null, pushedFrog = null;
    private ActionCard selectedCard= null;
    private final ArrayList<Bridge> bridges = new ArrayList<>();
    private final ArrayList<Leaf> leaves = new ArrayList<>();
    private final HashMap<Leaf, Frog> leafFrogMap = new HashMap<>();
    private boolean parachuteMode = false, extraJumpActivated = false, extraBridgeMode = false, moveFrog = false, placeBridge= false, hasBridge = false, moved = false, bridgeRemovalMode = false;
    private final ActionCard[] player1Cards, player2Cards, player3Cards, player4Cards;
    private final JFrame frame;
    private final JPanel popupOverlay;
    private static int blueScore = 0, redScore = 0, yellowScore = 0, purpleScore = 0;
    private boolean blueinRed = false, blueinYellow = false, blueinPurple = false, redinBlue = false, redinYellow = false, redinPurple = false, yellowinRed = false, yellowinBlue = false, yellowinPurple = false, purpleinRed = false, purpleinYellow = false, purpleinBlue = false;
    private ArrayList<String> scoreboard = new ArrayList<>();
    private JLabel turnText, blueText, redText, yellowText, purpleText;
    private int removedBridges = 0, bridgesPlaced = 0;

    

    public Board(GameState state) {
        this(state.playerNames, state.playerCount);
        currentIndex = state.currentIndex;
        this.playerColor = state.playerColor;
        this.turn = state.turn;
        parachuteMode = state.parachuteMode;
        extraJumpActivated = state.extraJumpActivated;
        int frogIndex = 0;
        for (GameState.FrogState fs : state.frogs) {
            Frog frog = null;
            if (fs.color.equals("Blue")) {
                frog = (Frog) blueFrogs[frogIndex % blueFrogs.length];
            } else if (fs.color.equals("Yellow")) {
                frog = (Frog) yellowFrogs[frogIndex % yellowFrogs.length];
            } else if (fs.color.equals("Red") && redFrogs != null) {
                frog = (Frog) redFrogs[frogIndex % redFrogs.length];
            } else if (fs.color.equals("Purple") && purpleFrogs != null) {
                frog = (Frog) purpleFrogs[frogIndex % purpleFrogs.length];
            }

            if (frog != null) {
                frog.setLocation(fs.x, fs.y);
                frog.setEnabled(fs.enabled);
                if (fs.onLeaf) {
                    frog.setOnLeaf();
                }
            }

            frogIndex++;
        }
        for (int i = 0; i < state.bridges.size() && i < bridges.size(); i++) {
            GameState.BridgeState bs = state.bridges.get(i);
            Bridge b = bridges.get(i);
            b.setLocation(bs.x, bs.y);
            b.setVisible(bs.visible);
            b.setEnabled(bs.enabled);
            if (bs.horizontal) {
                b.placeHorBridge(); // or make sure image/orientation is horizontal
            } else {
                b.placeVerBridge();
            }
        }
        for (int i = 0; i < state.leaves.size() && i < leaves.size(); i++) {
            GameState.LeafState ls = state.leaves.get(i);
            Leaf l = leaves.get(i);
            l.setLocation(ls.x, ls.y);
            if (ls.occupied) {
                l.setOccupied();
            }
        }
        switchFrogs();
        showPopUp();
    }

    public Board(String[] players, int playerCount) {
        
        frame = new JFrame();

        popup = new ImageIcon("./Assets/Popup.png");
        popupLabel = new ScaledImageLabel(popup.getImage());
        popupLabel.setOpaque(false);
        popupLabel.setBounds(0,0,420,90);
        blueScoreIcon = new ImageIcon("./Assets/blue-0.png");
        redScoreIcon = new ImageIcon("./Assets/red-0.png");
        yellowScoreIcon = new ImageIcon("./Assets/yellow-0.png");
        purpleScoreIcon = new ImageIcon("./Assets/purple-0.png");
        blueScoreLabel = new ScaledImageLabel(blueScoreIcon.getImage());
        redScoreLabel = new ScaledImageLabel(redScoreIcon.getImage());
        yellowScoreLabel = new ScaledImageLabel(yellowScoreIcon.getImage());
        purpleScoreLabel = new ScaledImageLabel(purpleScoreIcon.getImage());
        blueScoreLabel.setBounds(15,477,297,77);
        yellowScoreLabel.setBounds(15,618,297,77);
        redScoreLabel.setBounds(15,759,297,77); 
        purpleScoreLabel.setBounds(15,900,297,77);
        

        


        playerNames = new String[playerCount];
        playerNames = players;
        this.playerCount = playerCount;
        turn = players[0];
        playerColor = playerColors[currentIndex];

        fontStyle1 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",25f);
        fontStyle2 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",45f);
        fontStyle3 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",40f);
        gameBg = new ImageIcon("./Assets/Main-Menu-5.jpg");
        saveIcon = new ImageIcon("./Assets/Save-Game.png");
        blueText = new JLabel(playerNames[0] + "'s Score: " + blueScore);
        blueText.setText(playerNames[0] + "'s Score: " + blueScore);
        blueText.setFont(fontStyle1);
        blueText.setForeground(Color.decode("#2084ee"));
        blueText.setBounds(80, 420, 200, 50);

        yellowText = new JLabel(playerNames[1] + "'s Score: " + yellowScore);
        yellowText.setFont(fontStyle1);
        yellowText.setForeground(Color.decode("#f5c81f"));
        yellowText.setBounds(80, 561, 200, 50);

        redText = new JLabel(playerNames[2] + "'s Score: " + redScore);
        redText.setFont(fontStyle1);
        redText.setForeground(Color.decode("#f5251d"));
        redText.setBounds(80, 702, 200, 50);

        purpleText = new JLabel(playerNames[3] + "'s Score: " + purpleScore);
        purpleText.setFont(fontStyle1);
        purpleText.setForeground(Color.decode("#871ff6"));
        purpleText.setBounds(80, 843, 200, 50);

        blueFrogs = new Frog[3];
        yellowFrogs = new Frog[3];
        redFrogs = new Frog[3];
        purpleFrogs = new Frog[3];

        player1Cards = new ActionCard[4];
        player2Cards = new ActionCard[4];
        player3Cards = new ActionCard[4];
        player4Cards = new ActionCard[4];
        SaveGame = new JButton("Save Game");
        SaveGame.setFont(fontStyle3);
        SaveGame.setBounds(23, 27, 300, 70);
        styleButton(SaveGame);

 
        popupOverlay = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                double scale = ScreenScaler.getUniformScale();
                int baseW = popup.getIconWidth();
                int baseH = popup.getIconHeight();
                int popupW = (int) (baseW * scale);
                int popupH = (int) (baseH * scale);
                int x = (getWidth()  - popupW) / 2;
                int y = (getHeight() - popupH) / 2;
                g.drawImage(
                    popup.getImage(),
                    x, y,
                    popupW, popupH,
                    null
                );
            }
        };

        popupOverlay.setOpaque(false);
        popupOverlay.setVisible(false);
        popupOverlay.setPreferredSize(new Dimension(
        popup.getIconWidth(),
        popup.getIconHeight()
        ));
        popupOverlay.setEnabled(true);
        popupOverlay.setFocusable(true);
        frame.setGlassPane(popupOverlay);
        
        if(playerCount==2){
            baseBg = new ImageIcon("./Assets/Main-Menu-5-4.png");
        }
        else{
            baseBg = new ImageIcon("./Assets/Main-Menu-5-2.png");
        }

        if(playerCount==2){
            leafsBg = new ImageIcon("./Assets/Main-Menu-5-5.png");
        }
        else{
            leafsBg = new ImageIcon("./Assets/Main-Menu-5-3.png");
        }

        backgroundLabel = new ScaledImageLabel(gameBg.getImage());
        baseLabel = new ScaledImageLabel(baseBg.getImage());
        leafsLabel = new ScaledImageLabel(leafsBg.getImage());
        saveLabel = new ScaledImageLabel(saveIcon.getImage());

        pane = new JLayeredPane();
        backgroundLabel.setBounds(0, 0, 1920, 1080);
        saveLabel.setBounds(0,0,1920,1080);
        baseLabel.setBounds(2, 3, 1920, 1080);
        leafsLabel.setBounds(10,10,1920, 1080);

        for(int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                Leaf leaf = new Leaf();
                leafCount++;
                leaf.setBounds(550+171*j,140+166*i,154,155) ;
                leaf.addActionListener(this);
                leaf.setActionCommand("leaf " + leafCount);
                leaves.add(leaf);
                pane.add(leaf, Integer.valueOf(3));
            }
        }

        blueHome = new JButton();
        yellowHome = new JButton();

        blueHome.setBounds(920,980,100,100) ;
        blueHome.addActionListener(this);
        blueHome.setActionCommand("bluebase");
        blueHome.setVisible(true);
        blueHome.setEnabled(false);
        if (playerCount == 2) {blueHome.setEnabled(true);}
        styleButton(blueHome);

        yellowHome.setBounds(920,980-958,100,100) ;
        yellowHome.addActionListener(this);
        yellowHome.setActionCommand("yellowbase");
        yellowHome.setVisible(true);
        yellowHome.setEnabled(false);
        if (playerCount == 2) {yellowHome.setEnabled(true);}
        styleButton(yellowHome);

        pane.add(blueHome, Integer.valueOf(7));
        pane.add(yellowHome, Integer.valueOf(7));

        if (playerCount == 4) {
            redHome = new JButton();
            purpleHome = new JButton();

            redHome.setBounds(1390,565-80,100,70) ;
            redHome.addActionListener(this);
            redHome.setActionCommand("redbase");
            redHome.setVisible(true);
            redHome.setEnabled(false);
            styleButton(redHome);

            purpleHome.setBounds(1412-975,565-80,100,70) ;
            purpleHome.addActionListener(this);
            purpleHome.setActionCommand("purplebase");
            purpleHome.setVisible(true);
            purpleHome.setEnabled(false);
            styleButton(purpleHome);

            pane.add(purpleHome, Integer.valueOf(7));
            pane.add(redHome, Integer.valueOf(7));
        }
        for(int i=0;i<5;i++) {
            for (int j=0;j<4;j++) {
                Bridge bridge = new Bridge(); 
                bridge.setBounds(668+173*j, 193+168*i,79, 32);
                bridge.addActionListener(this);
                bridge.setActionCommand("Bridge");
                bridges.add(bridge);
                pane.add(bridge, Integer.valueOf(4));
            }
        }
        
        for(int i=0;i<4;i++) {
            for (int j=0;j<5;j++) {
                Bridge bridge = new Bridge();
                bridge.changeImage();
                bridge.setBounds(607+173*j, 247+170*i,32, 79);
                bridge.addActionListener(this);
                bridge.setActionCommand("Bridge");
                bridges.add(bridge);
                pane.add(bridge, Integer.valueOf(4));
            }
        }

        // player 1
        for (int i=0;i<3;i++) {
            Frog frog = new Frog("Blue");
            frog.setBounds(1041+100*i, 983,72, 77);
            frog.addActionListener(this);
            frog.setEnabled(false);
            frog.setActionCommand("blueFrog" + i);
            blueFrogs[i] = frog;
            pane.add(frog, Integer.valueOf(5));
        }
        // player 1
        for(int i=0;i<4;i++){
                ActionCard card = new ActionCard(playerNames[0]);
                card.setBounds(592 + 82*i,980,72, 88);
                pane.add(card, Integer.valueOf(5));
                card.setFocusPainted(false);
                card.addActionListener(this);
                card.setEnabled(false);
                card.setActionCommand("ActionCard" + i);
                player1Cards[i]=card;
                card.nextCard();
        }

        // player 2
        for (int i=0;i<3;i++) {
            Frog frog = new Frog("Yellow");
            frog.setPlayer2();
            frog.setBounds(1041+100*i, 26,72, 77);
            frog.addActionListener(this);
            yellowFrogs[i] = frog;
            frog.setActionCommand("yellowFrog" + i);
            pane.add(frog, Integer.valueOf(5));
        }
        // player 2
        for(int i=0;i<4;i++){
            ActionCard card = new ActionCard(playerNames[1]);
            card.setBounds(592 + 82*i,980-958,72, 88);
            pane.add(card, Integer.valueOf(5));
            card.setFocusPainted(false);
            card.addActionListener(this);
            card.setActionCommand("ActionCard" + i);
            player2Cards[i]=card;
            card.nextCard();
            card.setEnabled(false);
        }
        
        if (playerCount>2){
            // player 3
            for (int i=0;i<3;i++) {
                Frog frog = new Frog("Red");
                frog.setPlayer3();
                frog.setBounds(1412, 200+100*i,72, 77);
                frog.addActionListener(this);
                frog.setActionCommand("redFrog" + i);
                redFrogs[i] = frog;
                pane.add(frog, Integer.valueOf(5));
            }
            // player 3
            for(int i=0;i<4;i++){
                    ActionCard card = new ActionCard(playerNames[2]);
                    card.setBounds(1412,565+99*i,72, 88);
                    pane.add(card, Integer.valueOf(5));
                    card.setFocusPainted(false);
                    card.addActionListener(this);
                    card.setActionCommand("ActionCard" + i);
                    player3Cards[i]=card;
                    card.nextCard();
                    card.setEnabled(false);
            }

            // player 4
            for (int i=0;i<3;i++) {
                Frog frog = new Frog("Purple");
                frog.setPlayer4();
                frog.setBounds(437, 200+100*i,72, 77);
                frog.addActionListener(this);
                purpleFrogs[i] = frog;
                frog.setActionCommand("purpleFrog" + i);
                pane.add(frog, Integer.valueOf(5));
            }
            // player 4
            for(int i=0;i<4;i++){
                    ActionCard card = new ActionCard(playerNames[3]);
                    card.setBounds(1412-975,565+99*i,72, 88);
                    pane.add(card, Integer.valueOf(5));
                    card.setFocusPainted(false);
                    card.addActionListener(this);
                    card.setActionCommand("ActionCard" + i);
                    player4Cards[i]=card;
                    card.nextCard();
                    card.setEnabled(false);
            }
        }


        pane.add(backgroundLabel, Integer.valueOf(0));
        pane.add(baseLabel, Integer.valueOf(1));
        pane.add(leafsLabel, Integer.valueOf(6));
        pane.add(saveLabel, Integer.valueOf(6));
        pane.add(SaveGame, Integer.valueOf(7));
        pane.add(blueScoreLabel, Integer.valueOf(7));
        pane.add(yellowScoreLabel, Integer.valueOf(7));
        pane.add(redScoreLabel, Integer.valueOf(8));
        pane.add(purpleScoreLabel, Integer.valueOf(8));
        pane.add(blueText, Integer.valueOf(8));
        pane.add(yellowText, Integer.valueOf(8));
        pane.add(redText, Integer.valueOf(8));
        pane.add(purpleText, Integer.valueOf(8));
        frame.add(pane);
        frame.setSize(1920,1080);
        ScreenScaler.scaleFrame(frame);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        popup();
        showPopUp();
    }
    private int cx(JComponent c) { 
        return c.getX() + c.getWidth()/2; 
    }
    private int cy(JComponent c) { 
        return c.getY() + c.getHeight()/2; 
    }

    public void switchFrogs() {

        if (playerCount == 2) {
            for (int i = 0; i < 3; i++) {
            blueFrogs[i].setEnabled(false);
            yellowFrogs[i].setEnabled(false);
            }
            switch (currentIndex) {
                case 0 -> { for (int i = 0; i < 3; i++) blueFrogs[i].setEnabled(true); }
                case 1 -> { for (int i = 0; i < 3; i++) yellowFrogs[i].setEnabled(true); }
            }
        } 
        else if (playerCount == 4) {
            for (int i = 0; i < 3; i++) {
                blueFrogs[i].setEnabled(false);
                yellowFrogs[i].setEnabled(false);
                redFrogs[i].setEnabled(false);
                purpleFrogs[i].setEnabled(false);
            }
            switch (currentIndex) {
                case 0 -> { for (int i = 0; i < 3; i++) blueFrogs[i].setEnabled(true); }
                case 1 -> { for (int i = 0; i < 3; i++) yellowFrogs[i].setEnabled(true); }
                case 2 -> { for (int i = 0; i < 3; i++) redFrogs[i].setEnabled(true); }
                case 3 -> { for (int i = 0; i < 3; i++) purpleFrogs[i].setEnabled(true); }
            }
        }
    }
    public GameState toGameState() {
        GameState state = new GameState();
        state.playerNames = this.playerNames;
        state.playerCount = this.playerCount;
        state.currentIndex = currentIndex;
        state.playerColor = this.playerColor;
        state.turn = this.turn;
        state.removedBridges = removedBridges;
        state.bridgesPlaced = bridgesPlaced;

        state.parachuteMode = parachuteMode;
        state.extraJumpActivated = extraJumpActivated;
        GameState.FrogState fs;
        if (blueFrogs != null) {
            for (JButton b : blueFrogs) {
                if (b == null) continue;
                Frog f = (Frog) b;
                fs = new GameState.FrogState();
                fs.color = f.getPlayerColor();
                fs.x = f.getX();
                fs.y = f.getY();
                fs.onLeaf = f.isOnLeaf();
                fs.enabled = f.isEnabled();
                state.frogs.add(fs);
            }
        }
        if (yellowFrogs != null) {
            for (JButton b : yellowFrogs) {
                if (b == null) continue;
                Frog f = (Frog) b;
                fs = new GameState.FrogState();
                fs.color = f.getPlayerColor();
                fs.x = f.getX();
                fs.y = f.getY();
                fs.onLeaf = f.isOnLeaf();
                fs.enabled = f.isEnabled();
                state.frogs.add(fs);
            }
        }
        if (redFrogs != null) {
            for (JButton b : redFrogs) {
                if (b == null) continue;
                Frog f = (Frog) b;
                fs = new GameState.FrogState();
                fs.color = f.getPlayerColor();
                fs.x = f.getX();
                fs.y = f.getY();
                fs.onLeaf = f.isOnLeaf();
                fs.enabled = f.isEnabled();
                state.frogs.add(fs);
            }
        }
        if (purpleFrogs != null) {
            for (JButton b : purpleFrogs) {
                if (b == null) continue;
                Frog f = (Frog) b;
                fs = new GameState.FrogState();
                fs.color = f.getPlayerColor();
                fs.x = f.getX();
                fs.y = f.getY();
                fs.onLeaf = f.isOnLeaf();
                fs.enabled = f.isEnabled();
                state.frogs.add(fs);
            }
        }
        for (Bridge b : bridges) {
            GameState.BridgeState bs = new GameState.BridgeState();
            bs.x = b.getX();
            bs.y = b.getY();
            bs.visible = b.isVisible();
            bs.enabled = b.isEnabled();
            bs.horizontal = isHorizontal(b);
            state.bridges.add(bs);
        }
        for (Leaf l : leaves) {
            GameState.LeafState ls = new GameState.LeafState();
            ls.x = l.getX();
            ls.y = l.getY();
            ls.occupied = l.isOccupied(); // you can add this getter in Leaf
            state.leaves.add(ls);
        }

        return state;
    }

    public void turnSwitch() {
        currentIndex++;

        if (playerCount == 2 && (yellowScore == 3 || blueScore == 3)) {
            // Winning for 2 Player Graphics and Game End implementation here!
            System.out.println(scoreboard.get(0) + " Wins!");
            this.dispose();
            MainMenu mm = new MainMenu();
        }

        if (playerCount == 4 && ((redScore == 3 && blueScore == 3 && yellowScore == 3) || (redScore == 3 && blueScore == 3 && purpleScore == 3) || (redScore == 3 && purpleScore == 3 && yellowScore == 3) || (purpleScore == 3 && blueScore == 3 && yellowScore == 3))) {
            // Winning for 4 Player Graphics and Game End implementation here!
            String last = "";
            if (scoreboard.contains("Red") == false) {
                last = "Red";
            }
            if (scoreboard.contains("Blue") == false) {
                last = "Blue";
            }
            if (scoreboard.contains("Purple") == false) {
                last = "Purple";
            }
            if (scoreboard.contains("Yellow") == false) {
                last = "Yellow";
            }
            
            System.out.println(scoreboard.get(0) + " is First Place! " + scoreboard.get(1) + " is Second Place! " + scoreboard.get(2) + " is Third Place! " + last + " is Last Place!");
            this.dispose();
            MainMenu mm = new MainMenu();
        }

        PlaceABridge.setEnabled(true);

        if (extraJumpActivated == true) {
            currentIndex--;
            extraJumpActivated = false;
            PlaceABridge.setEnabled(false);
        }

        if (playerCount ==2){

            if (currentIndex > 1){
            //if (currentIndex == 2){
                currentIndex = 0;
            }
            if(currentIndex ==1){
                for (int i=0; i<4;i++){
                    player2Cards[i].setEnabled(true);
                    player1Cards[i].setEnabled(false);
                }
            }
            if(currentIndex ==0){
                for (int i=0; i<4;i++){
                    player1Cards[i].setEnabled(true);
                    player2Cards[i].setEnabled(false);
                }
            }
        }
        if (playerCount == 4){

            //Changing turns based on who's left in the game.

            if (currentIndex == 4 && blueScore != 3){
                currentIndex = 0;
            }
            if (currentIndex == 4 && blueScore == 3){
                currentIndex = 1;
            }
            if (currentIndex == 1 && yellowScore == 3) {
                currentIndex = 2;
            }
            if (currentIndex == 2 && redScore == 3) {
                currentIndex = 3;
            }
            if (currentIndex == 3 && purpleScore == 3) {
                currentIndex = 0;
            }

            
            if(currentIndex ==1 && yellowScore != 3){
                for (int i=0; i<4;i++){
                    player2Cards[i].setEnabled(true);
                    player1Cards[i].setEnabled(false);
                }
            }
        if(currentIndex ==2 && redScore != 3){
            for (int i=0; i<4;i++){
                player3Cards[i].setEnabled(true);
                player2Cards[i].setEnabled(false);
            }
        }
        if(currentIndex ==3 && purpleScore != 3){
            for (int i=0; i<4;i++){
                player4Cards[i].setEnabled(true);
                player3Cards[i].setEnabled(false);
            }
        }
        if(currentIndex ==0 && blueScore != 3){
            for (int i=0; i<4;i++){
                player1Cards[i].setEnabled(true);
                player4Cards[i].setEnabled(false);
            }
        }
            
        }

        if (playerCount ==4){
            playerColor = playerColors[currentIndex];
        }
        else{
            playerColor = playerColors2[currentIndex];
        }

        turn = playerNames[currentIndex];
        turnText.setText("It is " + turn + "'s turn!");
        moveFrog = false;
        placeBridge = false;
        hasBridge = false;
        bridgeRemovalMode = false;
        switchFrogs();
        showPopUp();
         
    }

    public void popup() {
        MoveAFrog = new JButton("Move a Frog");
        PlaceABridge = new JButton("Place a Bridge");
        styleButton(MoveAFrog);
        styleButton(PlaceABridge);
        MoveAFrog.setFont(fontStyle1);
        PlaceABridge.setFont(fontStyle1);
        MoveAFrog.setBounds(770, 535, 185, 50);
        PlaceABridge.setBounds(963, 535, 185, 50);
        turnText = new JLabel(("It is " + turn + "'s turn!"));
        turnText.setFont(fontStyle3);
        turnText.setForeground(Color.WHITE);
        turnText.setBounds(800, 460, 500, 70);
        popupOverlay.add(turnText);
        popupOverlay.add(MoveAFrog);
        popupOverlay.add(PlaceABridge);
    }

    public void showPopUp(){
        popupOverlay.setVisible(true);
        popupOverlay.requestFocusInWindow();
    }
    public void hidePopup(){
        popupOverlay.setVisible(false);
    }
    private void styleButton (JButton b){
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
    }

    private boolean isHorizontal(JComponent c) { 
        return c.getWidth() > c.getHeight(); 
    }

    private void addRemovedBridge(Bridge b) {
        if (b == null) return;
        if (isHorizontal(b)) {
            if (!removedHorList.contains(b)) removedHorList.add(b);
        } else {
            if (!removedVerList.contains(b)) removedVerList.add(b);
        }
        b.isRemoved = true;
        b.isRestored = false;
    }

    private void removeRemovedBridge(Bridge b) {
        if (b == null) return;
        removedHorList.remove(b);
        removedVerList.remove(b);
        b.isRemoved = false;
        b.isRestored = false;
    }

    private void enterBridgeRemovalMode() {
        bridgeRemovalMode = true;
        for (Bridge br : bridges) {
            br.setEnabled(br.isVisible());
            if (br.isVisible()) br.setActionCommand("Remove Bridge");
        }
    }

    private void showRemovedBridgesForPlacement() {
        if (removedHorList.isEmpty() && removedVerList.isEmpty()) return;

        for (Bridge b : removedHorList) {
            b.setVisible(true);
            b.setEnabled(true);
            b.setIcon(new ImageIcon(""));           
            b.setActionCommand("Place Hor");
        }
        for (Bridge b : removedVerList) {
            b.setVisible(true);
            b.setEnabled(true);
            b.setIcon(new ImageIcon(""));         
            b.setActionCommand("Place Ver");
        }
    }

    private boolean restoreRemovedBridge(Bridge b) {
        if (b == null) return false;
   
        if (isHorizontal(b)) {
            b.placeHorBridge();
            b.setIcon(new ImageIcon("./Assets/bridge-hor.jpg"));
        } else {
            b.placeVerBridge();
            b.setIcon(new ImageIcon("./Assets/bridge-Vertical.jpg"));
        }
        b.setEnabled(false);
        b.isRestored = true;
        b.isRemoved = false;

        removeRemovedBridge(b);

        bridgesPlaced++;

        if (bridgesPlaced == 1) {
            finalizeRestorePlacement();
            return true;
        }
        return false;
    }

    private void finalizeRestorePlacement() {
        bridgesPlaced = 0;

        for (Bridge br : removedHorList) {
            br.setVisible(false);
            br.setEnabled(false);
            br.setIcon(new ImageIcon(""));
            br.isRestored = false; 
        }
        for (Bridge br : removedVerList) {
            br.setVisible(false);
            br.setEnabled(false);
           br.setIcon(new ImageIcon(""));
            br.isRestored = false;
        }
    } 

    private void removeBridgeBetween(Leaf from, Leaf to) {
        if (from == null || to == null) return;
        int fx = cx(from), fy = cy(from);
        int tx = cx(to), ty = cy(to);
        int midX = (fx + tx) / 2;
        int midY = (fy + ty) / 2;
        final int TOL = 50;

        for (Bridge b : bridges) {
            int bx = cx(b);
            int by = cy(b);

            if (isHorizontal(b)) {
                if (Math.abs(by - midY) <= TOL && Math.abs(bx - midX) <= TOL) {
                    b.setVisible(false);
                    b.setEnabled(false);
                    addRemovedBridge(b);
                    return;
                }
            } else {
                if (Math.abs(bx - midX) <= TOL && Math.abs(by - midY) <= TOL) {
                    b.setVisible(false);
                    b.setEnabled(false);
                    addRemovedBridge(b);
                    return;
                }
            }
        }
    }

    private void placeBridgeBetween(Leaf a, Leaf b) {
        if (a == null || b == null) return;
        int midX = (cx(a) + cx(b)) / 2;
        int midY = (cy(a) + cy(b)) / 2;
        final int TOL = 50;
        for (Bridge br : bridges) {
            int bx = cx(br), by = cy(br);
            if (Math.abs(bx - midX) < TOL && Math.abs(by - midY) < TOL) {
                br.setVisible(true);
                br.setEnabled(true);
                if (isHorizontal(br)) br.placeHorBridge();
                else br.placeVerBridge();
                return;
            }
        }
    }

    private boolean handleBridgeCommand(Object src, String command) {
        if (command == null) return false;

        switch (command) {
            case "Remove Bridge":
                if (!(src instanceof Bridge)) return false;
                Bridge b = (Bridge) src;
                if (!b.isVisible()) return false;
                b.setVisible(false);
                b.setEnabled(false);
                b.setIcon(new ImageIcon(""));
                addRemovedBridge(b);
  
                for (Bridge br : bridges) br.setEnabled(false);
                return true;

            case "Place Hor":
            case "Place Ver":
                if (!(src instanceof Bridge)) return false;
                Bridge rb = (Bridge) src;
                boolean finished = restoreRemovedBridge(rb);
                return finished;

            case "Place Removed":
                showRemovedBridgesForPlacement();
                return false;

            default:
                return false;
        }
    }

    private boolean hasBridgeBetween(Leaf from, Leaf to) {
        int fx = cx(from), fy = cy(from);
        int tx = cx(to),   ty = cy(to);
        int midX = (fx + tx) / 2;
        int midY = (fy + ty) / 2;
        final int TOL = 60;
        for (Bridge b : bridges) {
            if (!b.isVisible()) continue;
            int bx = cx(b);
            int by = cy(b);
            if (isHorizontal(b)) {
                if (Math.abs(fy - ty) < 60 && Math.abs(bx - midX) < TOL && Math.abs(by - midY) < TOL)
                return true;
            }
            else {
                if (Math.abs(fx - tx) < 60 && Math.abs(bx - midX) < TOL && Math.abs(by - midY) < TOL)
                return true;
            }
        }
        return false;  
    }


    private boolean isSmallestGapInRow(int y, int gap) {
        int smallest = Integer.MAX_VALUE;
        for (Leaf leaf : leaves) {
            int ly = cy(leaf);
            if (Math.abs(ly - y) < 20) {
                for (Leaf other : leaves) {
                    if (leaf == other) continue;
                    if (Math.abs(cy(other) - y) < 20) {
                        int dx = Math.abs(cx(leaf) - cx(other));
                    if (dx > 0 && dx < smallest)
                        smallest = dx;
                    }
                }
            }
        }
        return Math.abs(gap - smallest) < 20;
    }

    private boolean isSmallestGapInColumn(int x, int gap) {
        int smallest = Integer.MAX_VALUE;
        for (Leaf leaf : leaves) {
            int lx = cx(leaf);
            if (Math.abs(lx - x) < 20) {
                for (Leaf other : leaves) {
                    if (leaf == other) continue;
                    if (Math.abs(cx(other) - x) < 20) {
                        int dy = Math.abs(cy(leaf) - cy(other));
                    if (dy > 0 && dy < smallest)
                        smallest = dy;
                    }
                }
            }
        }

        return Math.abs(gap - smallest) < 20;
    }

    private boolean isAdjacent(Leaf from, Leaf to) {
        int fx = cx(from), fy = cy(from);
        int tx = cx(to),   ty = cy(to);
        final int ALIGN_TOL = 20;
        if (Math.abs(fy - ty) < ALIGN_TOL) {              
            int dx = Math.abs(fx - tx);
            if (dx > 0) {
                return isSmallestGapInRow(fy, dx);
            }
        }
        if (Math.abs(fx - tx) < ALIGN_TOL) {              
            int dy = Math.abs(fy - ty);
            if (dy > 0) {
                return isSmallestGapInColumn(fx, dy);
            }
        }
        return false;
    }

    private void firstMovement(Object src, Leaf targetLeaf){
        selectedFrog.moveFrog(targetLeaf.getX() + targetLeaf.getWidth() / 2,targetLeaf.getY() + targetLeaf.getHeight() / 2);
        leafFrogMap.put(targetLeaf, selectedFrog);
        targetLeaf.setOccupied();
        selectedFrog.unHighlightFrog();
        selectedFrog.setOnLeaf();
        selectedFrog.setSpecificLeaf(targetLeaf);
        selectedFrog = null;
        parachuteMode = false;
        moved = true;
    }

    private void movement(Object src, Leaf targetLeaf){
        Leaf currentLeaf = null;
        for (Leaf leaf : leaves) {
            int frogCenterX = selectedFrog.getX() + selectedFrog.getWidth() / 2;
            int frogCenterY = selectedFrog.getY() + selectedFrog.getHeight() / 2;
            if (Math.abs(frogCenterX - (leaf.getX() + leaf.getWidth() / 2)) < 40 &&
                Math.abs(frogCenterY - (leaf.getY() + leaf.getHeight() / 2)) < 40) {
                currentLeaf = leaf;
                leafFrogMap.put(currentLeaf, selectedFrog);
                 break;
            }
        }
        if (isAdjacent(currentLeaf, targetLeaf) && (hasBridgeBetween(currentLeaf, targetLeaf))){
            selectedFrog.moveFrog(
            targetLeaf.getX() + targetLeaf.getWidth() / 2,targetLeaf.getY() + targetLeaf.getHeight() / 2);
            leafFrogMap.put(targetLeaf, selectedFrog);
            targetLeaf.setOccupied();
            selectedFrog.unHighlightFrog();
            if (leafFrogMap.containsKey(currentLeaf)) {
                leafFrogMap.keySet().remove(currentLeaf);
                removeBridgeBetween(currentLeaf, targetLeaf);
            }
            selectedFrog.setSpecificLeaf(targetLeaf);
            selectedFrog = null;
            moved = true;
        }
        else{
            System.out.println("Invalid move! No bridge between leaves.");
            moved = false;
        }
    }
    private void ParachuteMovement(Object src, Leaf targetLeaf){
        Leaf currentLeaf = null;
        for (Leaf leaf : leaves) {
            int frogCenterX = selectedFrog.getX() + selectedFrog.getWidth() / 2;
            int frogCenterY = selectedFrog.getY() + selectedFrog.getHeight() / 2;
            if (Math.abs(frogCenterX - (leaf.getX() + leaf.getWidth() / 2)) < 40 &&
                Math.abs(frogCenterY - (leaf.getY() + leaf.getHeight() / 2)) < 40) {
                currentLeaf = leaf;
                leafFrogMap.put(currentLeaf, selectedFrog);
                 break;
            }
        }
        if ((hasBridgeBetween(currentLeaf, targetLeaf) && parachuteMode == true) ){
            System.out.println("Cannot use parachute on a bridge!");
            hasBridge = true;
        }
        else if (isAdjacent(currentLeaf, targetLeaf) && hasBridge == false && parachuteMode == true){
            selectedFrog.moveFrog(
            targetLeaf.getX() + targetLeaf.getWidth() / 2,targetLeaf.getY() + targetLeaf.getHeight() / 2);
            leafFrogMap.put(targetLeaf, selectedFrog);
            targetLeaf.setOccupied();
            selectedFrog.unHighlightFrog();
            if (leafFrogMap.containsKey(currentLeaf)) {
                leafFrogMap.keySet().remove(currentLeaf);
                removeBridgeBetween(currentLeaf, targetLeaf);
            }
            selectedCard.doneCard();
            hasBridge = false;
            selectedFrog = null;
            parachuteMode = false;

        }
    }
    Leaf previousLeaf = null;
    private void movePushed(Object src, Leaf targetLeaf, Leaf currentLeaf){
        if (isAdjacent(currentLeaf, targetLeaf) && (hasBridgeBetween(currentLeaf, targetLeaf) || parachuteMode == true)){
            selectedFrog.moveFrog(
            targetLeaf.getX() + targetLeaf.getWidth() / 2,targetLeaf.getY() + targetLeaf.getHeight() / 2);
            leafFrogMap.put(targetLeaf, selectedFrog);
            removeBridgeBetween(currentLeaf, targetLeaf);
            turnSwitch();
            targetLeaf.setOccupied();
            selectedFrog.unHighlightFrog();
            selectedFrog = null;
            parachuteMode = false;
        }

    }
    private void pushFrog(Frog f){
        f.setLocation(200,200);
        pushedFrog =f;
        f.setEnabled(true);
        f.addActionListener(this);
    }
    
    private void enableBases(String color){

        if(color.equals("Blue") ){
            if (selectedFrog.getCurrentLeaf().equals("leaf 3")){
                yellowHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 15")){
                redHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 11")){
                purpleHome.setEnabled(true);
            }
        }
        if(color.equals("Yellow")){
            if (selectedFrog.getCurrentLeaf().equals("leaf 23")){
                blueHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 15")){
                redHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 11")){
                purpleHome.setEnabled(true);
            }
        }
        if(color.equals("Red")){
            if (selectedFrog.getCurrentLeaf().equals("leaf 3")){
                yellowHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 23")){
                blueHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 11")){
                purpleHome.setEnabled(true);
            }
        }
        if(color.equals("Purple")){
            if (selectedFrog.getCurrentLeaf().equals("leaf 3")){
                yellowHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 23")){
                blueHome.setEnabled(true);
            }
            else if (selectedFrog.getCurrentLeaf().equals("leaf 15")){
                redHome.setEnabled(true);
            }
        }

    
    }
    private void disableBases(){
        blueHome.setEnabled(false);
        yellowHome.setEnabled(false);
        if (playerCount ==4){
            redHome.setEnabled(false);
            purpleHome.setEnabled(false);
        }
    }


    private void enableCards(){
        if (playerCount ==2){
            for (ActionCard specificCard: player1Cards) {
                if (specificCard.getCardName().equals("Parachute") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Extra Jump") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Extra Bridge") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Bridge Removal") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
            }
            for (ActionCard specificCard: player2Cards) {
                if (specificCard.getCardName().equals("Parachute") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Extra Jump") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Extra Bridge") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Bridge Removal") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
            }
        }
        else{
            for (ActionCard specificCard: player1Cards) {
                if (specificCard.getCardName().equals("Parachute") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Extra Jump") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Extra Bridge") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Bridge Removal") && currentIndex == 0 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
            }
            for (ActionCard specificCard: player2Cards) {
                if (specificCard.getCardName().equals("Parachute") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Extra Jump") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Extra Bridge") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Bridge Removal") && currentIndex == 1 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
            }
            for (ActionCard specificCard: player3Cards) {
                if (specificCard.getCardName().equals("Parachute") && currentIndex == 2 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Extra Jump") && currentIndex == 2 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Extra Bridge") && currentIndex == 2 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
                if (specificCard.getCardName().equals("Bridge Removal") && currentIndex == 2 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
            }
            for (ActionCard specificCard: player4Cards) {
                if (specificCard.getCardName().equals("Parachute") && currentIndex == 3 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Extra Jump") && currentIndex == 3 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Extra Bridge") && currentIndex == 3 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }  
                if (specificCard.getCardName().equals("Bridge Removal") && currentIndex == 3 && !specificCard.isDone()) {
                    specificCard.setEnabled(true);
                }   
            }
        }
    }

    private void enableFrogs(){
        if (playerCount ==2){
            if (turn.equals(playerNames[0])){
                for (int i = 0; i < 3; i++)blueFrogs[i].setEnabled(true);
            }
            else if (turn.equals(playerNames[1])){
                for (int i = 0; i < 3; i++) yellowFrogs[i].setEnabled(true);
            }
        }
        else{
            if (turn.equals(playerNames[0])){
                for (int i = 0; i < 3; i++)blueFrogs[i].setEnabled(true);
            }
            else if (turn.equals(playerNames[1])){
                for (int i = 0; i < 3; i++) yellowFrogs[i].setEnabled(true);
            }
            else if (turn.equals(playerNames[2])){
                for (int i = 0; i < 3; i++) redFrogs[i].setEnabled(true);
            }
            else if (turn.equals(playerNames[3])){
                for (int i = 0; i < 3; i++) purpleFrogs[i].setEnabled(true);
            }
        }
    }
    public void saveToFile(File file) throws IOException {
        GameState state = toGameState();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(state);
        }
    }

    public static Board loadFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            GameState state = (GameState) in.readObject();
            return new Board(state);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        String command = e.getActionCommand();

        boolean bridgeFinished = handleBridgeCommand(src, command);
        if (bridgeFinished) {
            if (selectedCard != null){
                selectedCard.doneCard();
            }
            turnSwitch();
            return;
        }

        if(src.equals(MoveAFrog)){
            System.out.println("err");
            enableCards();
            enableFrogs();
            hidePopup();
            moveFrog= true;
            parachuteMode = false;
            moved = false;
        }
        if(src.equals(PlaceABridge) && (!removedHorList.isEmpty() || !removedVerList.isEmpty())){
            hidePopup();
            showRemovedBridgesForPlacement();
            return;
        }
        if (src.equals(PlaceABridge) && (removedHorList.isEmpty() && removedVerList.isEmpty())){
                System.out.println("There's no bridges to be placed so you will have to move a frog");
                moveFrog = true;
                hidePopup();
                return;
        }
        if (src.equals(pushedFrog)){
            selectedFrog = pushedFrog;
            selectedFrog.setEnabled(true);
        }
        if (src instanceof Leaf && selectedFrog == pushedFrog){
            Leaf targetLeaf = (Leaf) src;
            movePushed(src, targetLeaf, previousLeaf);
            pushedFrog = null;
        }

        if (src instanceof Frog && moveFrog) {
            if (selectedFrog == null) {
                selectedFrog = (Frog) src;
                selectedFrog.highlightFrog();
            }
            else {
                selectedFrog.unHighlightFrog();
                selectedFrog = (Frog) src;
                selectedFrog.highlightFrog();
            }
            
            if (selectedFrog!= null && selectedFrog.getPlayerColor().equals("Blue") && (selectedFrog.getCurrentLeaf().equals("leaf 3")
            || selectedFrog.getCurrentLeaf().equals("leaf 15") || selectedFrog.getCurrentLeaf().equals("leaf 11"))) {
                enableBases("Blue");
            }
            if (selectedFrog!= null && selectedFrog.getPlayerColor().equals("Yellow") && (selectedFrog.getCurrentLeaf().equals("leaf 23")
            || selectedFrog.getCurrentLeaf().equals("leaf 15") || selectedFrog.getCurrentLeaf().equals("leaf 11"))) {
                enableBases("Yellow");
            }
            if (playerCount ==4){
                if (selectedFrog!= null && selectedFrog.getPlayerColor().equals("Red") && (selectedFrog.getCurrentLeaf().equals("leaf 3")
                || selectedFrog.getCurrentLeaf().equals("leaf 11") || selectedFrog.getCurrentLeaf().equals("leaf 23"))) {
                    enableBases("Red");
                }
                if (selectedFrog!= null && selectedFrog.getPlayerColor().equals("Purple") && (selectedFrog.getCurrentLeaf().equals("leaf 3")
                || selectedFrog.getCurrentLeaf().equals("leaf 15") || selectedFrog.getCurrentLeaf().equals("leaf 23"))) {
                    enableBases("Purple");
                }
            }
            
            return;
        }
        if (src.equals(SaveGame)) {
            
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                try {
                saveToFile(f);
                System.out.println("Game saved to " + f.getAbsolutePath());}
                catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed to save game", "Error",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (src instanceof Leaf && selectedFrog != null && selectedFrog.getPlayerColor().equals(playerColor) && moveFrog && parachuteMode) {
            Leaf targetLeaf = (Leaf) src;
            if (selectedFrog.isOnLeaf()){
                if (!leafFrogMap.containsKey(targetLeaf)) {
                    ParachuteMovement(src, targetLeaf);
                    if (hasBridge == false){
                        turnSwitch();
                    }
                    else{
                        showPopUp();
                        selectedFrog.unHighlightFrog();
                        hasBridge = false;
                    }
                }
                else{
                    Frog f = leafFrogMap.get(targetLeaf);
                    pushFrog(f);
                    selectedFrog.setEnabled(false);
                    previousLeaf = targetLeaf;
                    movement(src, targetLeaf);
                }
            }
        }

        if (src instanceof Leaf && selectedFrog != null && selectedFrog.getPlayerColor().equals(playerColor) && moveFrog && parachuteMode == false) {
            Leaf targetLeaf = (Leaf) src;
            if (selectedFrog.isOnLeaf()){
                if (!leafFrogMap.containsKey(targetLeaf)) {
                    movement(src, targetLeaf);
                    if (moved){
                        turnSwitch();
                    }
                    else{
                        showPopUp();
                        selectedFrog.unHighlightFrog();
                    }
                }
                else{
                    Frog f = leafFrogMap.get(targetLeaf);
                    pushFrog(f);
                    selectedFrog.setEnabled(false);
                    previousLeaf = targetLeaf;
                    movement(src, targetLeaf);
                }
            }

            else{
                if(playerCount ==4){
                    if (!leafFrogMap.containsKey(targetLeaf) && (command.equals(baseLeaves[currentIndex])) && moveFrog) {
                        firstMovement(src, targetLeaf);
                        if (moved){
                            turnSwitch();
                        }
                        else{
                            showPopUp();
                            selectedFrog.unHighlightFrog();
                        }
                    }
                    if (leafFrogMap.containsKey(targetLeaf) && (command.equals(baseLeaves[currentIndex])) && moveFrog) {
                        Frog f = leafFrogMap.get(targetLeaf);
                        pushFrog(f);
                        selectedFrog.setEnabled(false);
                        previousLeaf = targetLeaf;
                        firstMovement(src, targetLeaf);
                    }
                }
                else{
                    if (!leafFrogMap.containsKey(targetLeaf) && (command.equals(baseLeaves2[currentIndex]))&& moveFrog) {
                        firstMovement(src, targetLeaf);
                        if (moved){
                            turnSwitch();
                        }
                        else{
                            showPopUp();
                            selectedFrog.unHighlightFrog();
                        }
                    }
                    else{
                        Frog f = leafFrogMap.get(targetLeaf);
                        pushFrog(f);
                        selectedFrog.setEnabled(false);
                        previousLeaf = targetLeaf;
                        firstMovement(src, targetLeaf);
                    }
                }
            }
        }
        if (selectedFrog != null&& selectedFrog.getPlayerColor().equals(playerColor) && moveFrog) {
            System.out.println(selectedFrog.getCurrentLeaf());
        }
        

        /*
        frog.setBounds(1041+100*i, 983,72, 77);
        frog.setBounds(1041+100*i, 26,72, 77);
        frog.setBounds(1412, 200+100*i,72, 77);
        frog.setBounds(437, 200+100*i,72, 77);
         */
        //Home button implementation
        if (src.equals(yellowHome)&& selectedFrog != null && selectedFrog.getCurrentLeaf().equals("leaf 3") && moveFrog){
            int locationCounter =0;
            if (selectedFrog.getPlayerColor().equals("Blue")){
                blueScore+=1;
                if (blueScore ==1)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-1.png"));
                else if (blueScore ==2)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-2.png"));
                else if (blueScore ==3)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Red")){
                redScore+=1;
                if (redScore ==1)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-1.png"));
                else if (redScore ==2)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-2.png"));
                else if (redScore ==3)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Purple")){
                purpleScore+=1;
                if (purpleScore ==1)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-1.png"));
                else if (purpleScore ==2)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-2.png"));
                else if (purpleScore ==3)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-3.png"));
            }
            selectedFrog.setLocation(1412+100*locationCounter, 26);
            selectedFrog.unHighlightFrog();
            blueText.setText(playerNames[0] + "'s Score: " + blueScore);
            yellowText.setText(playerNames[1] + "'s Score: " + yellowScore);
            redText.setText(playerNames[2] + "'s Score: " + redScore);
            purpleText.setText(playerNames[3] + "'s Score: " + purpleScore);
            leafFrogMap.remove(selectedFrog.getLeaf());
            selectedFrog.setEnabled(false);
            locationCounter++;
            disableBases();
            turnSwitch();
        }
        if (src.equals(redHome)&& selectedFrog != null && selectedFrog.getCurrentLeaf().equals("leaf 15") && moveFrog){
            int locationCounter =0;
            if (selectedFrog.getPlayerColor().equals("Blue")){
                blueScore+=1;
                if(blueScore ==1)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-1.png"));
                else if (blueScore ==2)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-2.png"));
                else if (blueScore ==3)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Yellow")){
                yellowScore+=1;
                if(yellowScore ==1)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-1.png"));
                else if (yellowScore ==2)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-2.png"));
                else if (yellowScore ==3)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Purple")){
                purpleScore+=1;
                if (purpleScore ==1)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-1.png"));
                else if (purpleScore ==2)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-2.png"));
                else if (purpleScore ==3)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-3.png"));
            }
            selectedFrog.setLocation(1550,200+100*locationCounter);
            selectedFrog.unHighlightFrog();
            blueText.setText(playerNames[0] + "'s Score: " + blueScore);
            yellowText.setText(playerNames[1] + "'s Score: " + yellowScore);
            redText.setText(playerNames[2] + "'s Score: " + redScore);
            purpleText.setText(playerNames[3] + "'s Score: " + purpleScore);
            leafFrogMap.remove(selectedFrog.getLeaf());
            selectedFrog.setEnabled(false);
            locationCounter++;
            disableBases();
            turnSwitch();
        }
        if (src.equals(purpleHome)&& selectedFrog != null && selectedFrog.getCurrentLeaf().equals("leaf 11") && moveFrog){
            int locationCounter =0;
            if (selectedFrog.getPlayerColor().equals("Blue")){
                blueScore+=1;
                if (blueScore ==1)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-1.png"));
                else if (blueScore ==2)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-2.png"));
                else if (blueScore ==3)
                    blueScoreLabel.setIcon(new ImageIcon("./Assets/blue-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Red")){
                redScore+=1;
                if(redScore ==1)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-1.png"));
                else if (redScore ==2)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-2.png"));
                else if (redScore ==3)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Yellow")){
                yellowScore+=1;
                if (yellowScore ==1)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-1.png"));
                else if (yellowScore ==2)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-2.png"));
                else if (yellowScore ==3)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-3.png"));
            }
            selectedFrog.setLocation(299,200+100*locationCounter);
            selectedFrog.unHighlightFrog();
            blueText.setText(playerNames[0] + "'s Score: " + blueScore);
            yellowText.setText(playerNames[1] + "'s Score: " + yellowScore);
            redText.setText(playerNames[2] + "'s Score: " + redScore);
            purpleText.setText(playerNames[3] + "'s Score: " + purpleScore);
            leafFrogMap.remove(selectedFrog.getLeaf());
            selectedFrog.setEnabled(false);
            locationCounter++;
            disableBases();
            turnSwitch();
        }
        if (src.equals(blueHome)&& selectedFrog != null && selectedFrog.getCurrentLeaf().equals("leaf 23") && moveFrog){
            int locationCounter =0;
            if (selectedFrog.getPlayerColor().equals("Yellow")){
                yellowScore+=1;
                if (yellowScore ==1)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-1.png"));
                else if (yellowScore ==2)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-2.png"));
                else if (yellowScore ==3)
                    yellowScoreLabel.setIcon(new ImageIcon("./Assets/yellow-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Red")){
                redScore+=1;
                if(redScore ==1)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-1.png"));
                else if (redScore ==2)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-2.png"));
                else if (redScore ==3)
                    redScoreLabel.setIcon(new ImageIcon("./Assets/red-3.png"));
            }
            else if (selectedFrog.getPlayerColor().equals("Purple")){
                purpleScore+=1;
                if (purpleScore ==1)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-1.png"));
                else if (purpleScore ==2)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-2.png"));
                else if (purpleScore ==3)
                    purpleScoreLabel.setIcon(new ImageIcon("./Assets/purple-3.png"));
            }
            selectedFrog.setLocation(1412+ 100*locationCounter,983);
            leafFrogMap.remove(selectedFrog.getLeaf());
            selectedFrog.unHighlightFrog();
            blueText.setText(playerNames[0] + "'s Score: " + blueScore);
            yellowText.setText(playerNames[1] + "'s Score: " + yellowScore);
            redText.setText(playerNames[2] + "'s Score: " + redScore);
            purpleText.setText(playerNames[3] + "'s Score: " + purpleScore);
            selectedFrog.setEnabled(false);
            locationCounter++;
            disableBases();
            turnSwitch();
        }

        if (src instanceof ActionCard ){
            selectedCard = (ActionCard) src;

            if (selectedCard != null && selectedCard.getCardName().equals("Parachute") && selectedCard.getPlayerName().equals(turn) && selectedCard.isDone() != true){
                parachuteMode = true;
            }
            
            if (selectedCard != null && selectedCard.getCardName().equals("Bridge Removal") && selectedCard.getPlayerName().equals(turn) && selectedCard.isDone() != true){
                enterBridgeRemovalMode();
                return;
            }

            if (selectedCard!= null && selectedCard.getCardName().equals("Extra Bridge") && selectedCard.getPlayerName().equals(turn) && selectedCard.isDone() != true){
                if((removedHorList.isEmpty() && removedVerList.isEmpty())){
                    System.out.println("There's not enough bridges to place");
                    selectedCard = null;
                    return;
                }
                else{     
                    extraBridgeMode = true;
                    showRemovedBridgesForPlacement();   
                    return;       
                }
            }

            if (selectedCard!= null && selectedCard.getCardName().equals("Extra Jump") && selectedCard.getPlayerName().equals(turn)
            && selectedCard.isDone() != true){
                if (placeBridge == true) {
                    return;
                }
                else {
                    extraJumpActivated = true;
                    hidePopup();
                    selectedCard.doneCard();
                }
            }     
        }
    }
}