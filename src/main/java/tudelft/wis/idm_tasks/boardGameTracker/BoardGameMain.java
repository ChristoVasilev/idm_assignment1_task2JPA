package tudelft.wis.idm_tasks.boardGameTracker;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class BoardGameMain {
    public static void main(String[] args) throws BgtException {
        TableCreator tableCreator = new TableCreator();
        tableCreator.createDatabase();
        BgtDataManagerImplementation bgtManager = new BgtDataManagerImplementation();
        bgtManager.createNewPlayer("toni", "magareto");
        ArrayList<Player> players = new ArrayList<>(bgtManager.findPlayersByName("toni"));
        for(Player player : players) {
            System.out.println(player.getPlayerName());
            System.out.println(player.getPlayerNickName());
        }

        bgtManager.persistPlayer(new PlayerImplementation("toni", "maimunata"));
        players = new ArrayList<>(bgtManager.findPlayersByName("toni"));
        for(Player player : players) {
            System.out.println(player.getPlayerName());
            System.out.println(player.getPlayerNickName());
        }

        bgtManager.eraseDatabase();

    }


}
