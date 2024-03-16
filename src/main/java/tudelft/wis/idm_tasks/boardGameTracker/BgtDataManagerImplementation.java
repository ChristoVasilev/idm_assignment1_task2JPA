package tudelft.wis.idm_tasks.boardGameTracker;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class BgtDataManagerImplementation implements tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("boardGamesJPA");

    /**
     * Creates a new player and stores it in the DB.
     *
     * @param name the player name
     * @param nickname the player nickname
     * @return the new player
     * @throws java.sql.SQLException DB trouble
     */
    public Player createNewPlayer(String name, String nickname) throws BgtException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Player player = new PlayerImplementation(name, nickname);

        try {
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return player;
    }

    /**
     * Searches for player in the database by a substring of their name.
     *
     * @param name the name substring to use, e.g., searching for "hris" will find "Christoph"
     * @return collection of all players containing the param substring in their names
     * @throws BgtException the bgt exception
     */
    public Collection<Player> findPlayersByName(String name) throws BgtException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Player> query = entityManager.createQuery(
                    "SELECT p FROM PlayerImplementation p WHERE p.name LIKE CONCAT('%', :playerName, '%')", Player.class);

            query.setParameter("playerName", name);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }


    /**
     * Creates a new board game and stores it in the DB.
     * <p>
     * Note: These "create" methods are somewhat unnecessary. However, I put
     * them here to make the task test a bit easier. You can call an appropriate
     * persist method for this.
     *
     * @param name the name of the game
     * @param bggURL the URL of the game at BoardGameGeek.com
     * @return the new game
     * @throws SQLException DB trouble
     */
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        BoardGame boardGame = new BoardGameImplementation(name, bggURL);

        try {
            transaction.begin();
            entityManager.persist(boardGame);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return boardGame;
    }

    /**
     * Searches for game in the database by a substring of their name.
     *
     * @param name the name substring to use, e.g., searching for "clips" will
     * find "Eclipse: Second Dawn of the Galaxy""
     * @return collection of all boardgames containing the param substring in their names
     */
    public Collection<BoardGame> findGamesByName(String name) throws BgtException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<BoardGame> query = entityManager.createQuery(
                    "SELECT bg FROM BoardGameImplementation bg WHERE bg.name LIKE CONCAT('%', :gameName, '%')", BoardGame.class);

            query.setParameter("gameName", name);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Creates a new play session and stores it in the DB.
     *
     * @param date the date of the session
     * @param host the session host
     * @param game the game which was played
     * @param playtime the approximate playtime in minutes
     * @param players all players
     * @param winner the one player who won (NULL in case of no winner; multiple
     * winners not supported)
     * @return the new play session
     */
    public PlaySession createNewPlaySession(Date date, PlayerImplementation host, BoardGameImplementation game, int playtime, Collection<PlayerImplementation> players, PlayerImplementation winner) throws BgtException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        PlaySessionImplementation playSession = new PlaySessionImplementation(date, host, game, playtime, players, winner);

        try {
            transaction.begin();
            entityManager.persist(playSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return playSession;
    }

    /**
     * Finds all play sessions from a specific date
     *
     * @param date the date to search from
     * @return collection of all play sessions from the param date
     * @throws BgtException the bgt exception
     */
    public Collection<PlaySessionImplementation> findSessionByDate(Date date) throws BgtException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<PlaySessionImplementation> query = entityManager.createQuery(
                    "SELECT ps FROM PlaySessionImplementation ps WHERE ps.date = :date", PlaySessionImplementation.class);

            query.setParameter("date", date);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Persists a given player to the DB. Note that this player might already exist and only needs an update :-)
     * @param player the player
     */
    public void persistPlayer(Player player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<PlayerImplementation> query = entityManager.createQuery(
                    "SELECT p FROM PlayerImplementation p WHERE p.name = :name", PlayerImplementation.class);

            query.setParameter("name", player.getPlayerName());
            if(query.getResultList() == null) {
                createNewPlayer(player.getPlayerName(), player.getPlayerNickName());
            }
        } catch (BgtException e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Persists a given session to the DB. Note that this session might already exist and only needs an update :-)
     * @param session the session
     */
    public void persistPlaySession(PlaySessionImplementation session) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<PlaySessionImplementation> query = entityManager.createQuery(
                    "SELECT ps FROM PlaySessionImplementation ps WHERE ps.id = :id", PlaySessionImplementation.class);

            query.setParameter("id", session.getId());
            if(query.getResultList() == null) {
                createNewPlaySession(session.getDate(), session.getHost(), session.getGame(), session.getPlaytime(), session.getAllPlayers(), session.getWinner());
            }
        } catch (BgtException e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Persists a given game to the DB. Note that this game might already exist and only needs an update :-)
     * @param game the game
     */
    public void persistBoardGame(BoardGame game) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<BoardGame> query = entityManager.createQuery(
                    "SELECT bg FROM BoardGameImplementation bg WHERE bg.bggURL = :gameName", BoardGame.class);

            query.setParameter("gameName", game.getBGG_URL());
            if(query.getResultList() == null) {
                createNewBoardgame(game.getName(), game.getBGG_URL());
            }
        } catch (BgtException e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }
}
