package tudelft.wis.idm_tasks.boardGameTracker;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;


import java.util.Collection;
import java.util.LinkedList;


@Entity
@Table(name = "players")
public class PlayerImplementation implements Player {
    @Id
    private String name;
    @Column(name = "nick_name")
    private String nickName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "player_owns_game",
            joinColumns = @JoinColumn(name = "player_name"),
            inverseJoinColumns = @JoinColumn(name = "board_game_url")
    )
    @Column(name = "player_own_game")
    private Collection<BoardGameImplementation> gameCollection = new LinkedList<>();

    /**
     * Instantiates a new Player POJO.
     *
     * @param name     name
     * @param nickName nickname
     */
    public PlayerImplementation(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }

    public PlayerImplementation() {
    }

    @Override
    public String getPlayerName() {
        return name;
    }

    @Override
    public String getPlayerNickName() {
        return nickName;
    }

    @Override
    public Collection<BoardGameImplementation> getGameCollection() {
        return gameCollection;
    }


    @Override
    public String toVerboseString() {
        String result = name;
        if (nickName != null) {
            result = result + " (" + nickName + ")";
        }
        return result;
    }
}
