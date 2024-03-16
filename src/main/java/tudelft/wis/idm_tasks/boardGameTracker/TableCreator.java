package tudelft.wis.idm_tasks.boardGameTracker;

import java.sql.*;

public class TableCreator {
    private static Connection connection;

    public Connection getConnection()  {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/boardGames", "postgres", "Sarah1809");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return connection;
    }

    public void createDatabase() {
        createPlayer();
        createBoardGame();
        createPlayerOwnsBoardGame();
    }

    public void createPlayer() {
        String setup = "CREATE TABLE IF NOT EXISTS players (name VARCHAR(64) PRIMARY KEY NOT NULL, nick_name VARCHAR(64) NOT NULL)";
        PreparedStatement myStmt = null;
        try {
            getConnection();
            myStmt = connection.prepareStatement(setup);
            myStmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createBoardGame() {
        String setup = "CREATE TABLE IF NOT EXISTS board_games (name VARCHAR(64) NOT NULL, url VARCHAR(64) PRIMARY KEY NOT NULL)";
        PreparedStatement myStmt = null;
        try {
            getConnection();
            myStmt = connection.prepareStatement(setup);
            myStmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createPlayerOwnsBoardGame() {
        String setup = "CREATE TABLE IF NOT EXISTS player_owns_game ( player_name VARCHAR(64) NOT NULL, board_game_url VARCHAR(64) NOT NULL, "
        + "PRIMARY KEY (player_name, board_game_url), FOREIGN KEY (player_name) REFERENCES players(name) ON DELETE CASCADE, "
        + "FOREIGN KEY (board_game_url) REFERENCES board_games(url) ON DELETE CASCADE)";
        PreparedStatement myStmt = null;
        try {
            getConnection();
            myStmt = connection.prepareStatement(setup);
            myStmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
