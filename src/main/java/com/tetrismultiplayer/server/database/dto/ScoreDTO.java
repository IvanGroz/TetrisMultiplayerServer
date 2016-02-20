package main.java.com.tetrismultiplayer.server.database.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Class representing score in database.
 */
@Entity
@Table(name = "score")
public class ScoreDTO implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "score")
    private Integer score;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_name")
    private List<UserDTO> player;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameDTO game;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public GameDTO getGame()
    {
        return game;
    }

    public void setGame(GameDTO game)
    {
        this.game = game;
    }

    public List<UserDTO> getPlayer()
    {
        return player;
    }

    public void setPlayer(List<UserDTO> player)
    {
        this.player = player;
    }
}
