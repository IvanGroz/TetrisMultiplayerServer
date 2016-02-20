package main.java.com.tetrismultiplayer.server.database.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Class representing player in databse.
 */
@Entity
@Table(name = "player")
public class UserDTO implements Serializable
{
    @Id
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<ScoreDTO> score;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<ScoreDTO> getScores()
    {
        return score;
    }

    public void setScores(List<ScoreDTO> score)
    {
        this.score = score;
    }
}
