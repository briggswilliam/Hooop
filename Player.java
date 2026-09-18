public class Player{

    private String playerName;
    private static int playerCount = 0;
    public static String players[] = new String[4];
    private int playerID;

    public Player(String name){
        if (name.isEmpty()){
            playerName = "Player " + playerCount;
        }
        else {
            playerName = name;
        }
        players[playerCount] = playerName;
        playerCount++;
    }
    public String getPlayerName(){
        return playerName;
    }
    public int getPlayerCount(){
        return playerCount;
    }

    public String[] getPlayers(){

        return players;
    }
}
