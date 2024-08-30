import java.io.*;
import java.util.Map;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private Player player;
    private int timeUnits;

    public GameState(Player player, int timeUnits) {
        this.player = player;
        this.timeUnits = timeUnits;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTimeUnits() {
        return timeUnits;
    }

    public static void saveGame(GameState gameState, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static GameState loadGame(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            GameState gameState = (GameState) ois.readObject();
            System.out.println("Game loaded successfully.");
            return gameState;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
