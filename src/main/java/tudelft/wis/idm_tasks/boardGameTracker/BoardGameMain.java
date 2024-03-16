package tudelft.wis.idm_tasks.boardGameTracker;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class BoardGameMain {
    public static void main(String[] args) throws BgtException {
        BgtDataManagerImplementation dataManager = new BgtDataManagerImplementation();
        dataManager.createNewPlayer("itso", "chuka");
        System.out.println(dataManager.findPlayersByName("its").iterator().next().getPlayerName());



    }


}
