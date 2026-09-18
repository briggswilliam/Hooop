import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

    public String[] playerNames;
    public int playerCount;
    public int currentIndex;
    public String playerColor;
    public String turn;

    public int removedHorBridgesCounter;
    public int removedVerBridgesCounter;
    public int removedBridges;
    public int bridgesPlaced;

    public boolean parachuteMode;
    public boolean extraJumpActivated;

    public static class FrogState implements Serializable {
        public String color;
        public int x, y;
        public boolean onLeaf;
        public boolean enabled;
    }

    public List<FrogState> frogs = new ArrayList<>();

    public static class BridgeState implements Serializable {
        public int x, y;
        public boolean visible;
        public boolean enabled;
        public boolean horizontal;
    }

    public List<BridgeState> bridges = new ArrayList<>();
    public static class LeafState implements Serializable {
        public int x, y;
        public boolean occupied;
    }

    public List<LeafState> leaves = new ArrayList<>();
}
