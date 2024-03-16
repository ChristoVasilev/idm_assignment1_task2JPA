package tudelft.wis.idm_tasks.boardGameTracker;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.*;
import java.util.Collection;
import java.util.Date;

@Entity(name = "play_session")
public class PlaySessionImplementation implements PlaySession{
    @Id
    private int id;

    private Date date;
    @ManyToOne
    private PlayerImplementation host;
    @ManyToOne
    private BoardGameImplementation game;
    @Column(name = "play_time")
    private int playTime;
    @ManyToMany
    private Collection<PlayerImplementation> players;
    @ManyToOne
    private PlayerImplementation winner;

    public PlaySessionImplementation() {
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Player getHost() {
        return host;
    }

    @Override
    public BoardGame getGame() {
        return game;
    }

    @Override
    public Collection<PlayerImplementation> getAllPlayers() {
        return players;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public int getPlaytime() {
        return playTime;
    }

    @Override
    public String toVerboseString() {
        String result = game.toVerboseString() + " {";
        result = result + "\n  Date: " + date.toString();
        result = result + "\n  Playtime: " + playTime;
        result = result + "\n  Host: " + host.toVerboseString();
        result = result + "\n  Players: ";
        for (Player player : players) {
            result = result + player.toVerboseString() + "; ";
        }
        result = result + "\n}\n";
        return result;
    }

    /**
     * Instantiates a new Play session POJO.
     *
     * @param date     the date
     * @param host     the host
     * @param game     the game
     * @param playTime the play time
     * @param players  the players
     * @param winner   the winner
     */
    public PlaySessionImplementation(Date date, PlayerImplementation host, BoardGameImplementation game, int playTime, Collection<PlayerImplementation> players, PlayerImplementation winner) {
        this.date = date;
        this.host = host;
        this.game = game;
        this.playTime = playTime;
        this.players = players;
        this.winner = winner;
    }
}
