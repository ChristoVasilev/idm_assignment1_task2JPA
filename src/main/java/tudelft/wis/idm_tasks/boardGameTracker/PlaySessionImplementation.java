package tudelft.wis.idm_tasks.boardGameTracker;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "play_sessions")
public class PlaySessionImplementation implements PlaySession{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date date;
    @ManyToOne
    private PlayerImplementation host;
    @ManyToOne
    private BoardGameImplementation game;
    @Column(name = "play_time")
    private int playTime;
    @ManyToOne
    private PlayerImplementation winner;
    @ManyToMany
    @JoinTable(
            name = "player_in_session",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "player_name")
    )
    @Column(name = "player_in_session")
    private Collection<PlayerImplementation> players;

    public PlaySessionImplementation() {
    }

    public int getId() {
        return id;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public PlayerImplementation getHost() {
        return host;
    }

    @Override
    public BoardGameImplementation getGame() {
        return game;
    }

    @Override
    public Collection<PlayerImplementation> getAllPlayers() {
        return players;
    }

    @Override
    public PlayerImplementation getWinner() {
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
