/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.Test;
import org.tinylog.Logger;
import tudelft.wis.idm_tasks.boardGameTracker.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type POJO test.
 *
 * @author Christoph Lofi, Alexandra Neagu
 */
public class BgtDataManagerImplementationTest extends tudelft.wis.idm_solutions.BoardGameTracker.AbstractBGTDemo {

    /**
     * Instantiates a new POJO test.
     */
    public BgtDataManagerImplementationTest() {
    }

    private BgtDataManagerImplementation dataManager = new BgtDataManagerImplementation();

    @Override
    public BgtDataManager getBgtDataManager() {
        return dataManager;
    }

    /**
     * Just runs the application with some simple queries and assertions. It's
     * not very comprehensive, essentially, just a single session is retrieved
     * and the hist and the game is being checked.
     */
    @Test
    public void basicTest() throws BgtException {
        TableCreator tableCreator = new TableCreator();
        tableCreator.createDatabase();

        Player player = new PlayerImplementation("gosho","magareto");
        BoardGame game = new BoardGameImplementation("Cthulhu Wars", "https://boardgamegeek.com/boardgame/139976/cthulhu-warsf");
        player.getGameCollection().add(game);

        dataManager.createNewPlayer(player.getPlayerName(), player.getPlayerNickName());
        dataManager.createNewBoardgame(game.getName(), game.getBGG_URL());
        dataManager.addGameToPlayerCollection(player, game);

        // Retrieve a player from the database and check if it returns correctly
        Player retrievedPlayer = this.getBgtDataManager().findPlayersByName(player.getPlayerName()).iterator().next();
        assertEquals(retrievedPlayer.getPlayerNickName(), player.getPlayerNickName());
        assertEquals(retrievedPlayer.getGameCollection().size(), player.getGameCollection().size());
        Logger.info("Player check passed: " + retrievedPlayer.getPlayerName() + "; collectionSize: " + retrievedPlayer.getGameCollection().size());

        // Retrieve a game from the database and check if it returns correctly
        BoardGame retrievedGame = this.getBgtDataManager().findGamesByName(game.getName()).iterator().next();
        assertEquals(retrievedGame.getBGG_URL(), game.getBGG_URL());

        // Remove a game from the host's collection, add  it again
        BoardGame firstGame = player.getGameCollection().iterator().next();
        int numOfGames = player.getGameCollection().size();
        player.getGameCollection().remove(firstGame);
        dataManager.removeGameFromPlayerCollection(player, game);
        dataManager.persistPlayer(player);

        // Load a player again from DB
        Player playerFromDB = dataManager.findPlayersByName(player.getPlayerName()).iterator().next();
        assertEquals(numOfGames - 1, playerFromDB.getGameCollection().size());

        // Add the game again
        playerFromDB.getGameCollection().add(firstGame);
        dataManager.persistPlayer(playerFromDB);
        dataManager.addGameToPlayerCollection(playerFromDB, firstGame);

        // Load the host again from DB
        Player playerFromDB2 = this.getBgtDataManager().findPlayersByName(player.getPlayerName()).iterator().next();
        assertEquals(numOfGames, playerFromDB2.getGameCollection().size());

    }
}