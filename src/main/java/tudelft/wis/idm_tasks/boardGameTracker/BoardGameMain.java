package tudelft.wis.idm_tasks.boardGameTracker;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;

public class BoardGameMain {
    public static void main(String[] args) throws BgtException {
        BgtDataManagerImplementation dataManager = new BgtDataManagerImplementation();
        dataManager.createNewPlayer("itso", "chuka");
        System.out.println(dataManager.findPlayersByName("its").iterator().next().getPlayerName());
        dataManager.createNewBoardgame("name", "url");
        System.out.println(dataManager.findGamesByName("name"));
        dataManager.createNewPlaySession(new Date(1, 2, 3),null, null, 0, null, null);
        System.out.println(dataManager.findSessionByDate(new Date(1, 2, 3)));
    }


}
