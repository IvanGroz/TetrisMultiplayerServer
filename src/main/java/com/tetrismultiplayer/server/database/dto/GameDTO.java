package main.java.com.tetrismultiplayer.server.database.dto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "game")
public class GameDTO
{
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "game_date")
    private Long gameDate;
    @Column(name = "game_type")
    private String gameType;
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<ScoreDTO> score;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Long getGameDate()
    {
	return gameDate;
    }

    public void setGameDate(Long gameDate)
    {
	this.gameDate = gameDate;
    }

    public String getGameType()
    {
	return gameType;
    }

    public void setGameType(String gameType)
    {
	this.gameType = gameType;
    }

    public List<ScoreDTO> getScore()
    {
	return score;
    }

    public void setScore(List<ScoreDTO> score)
    {
	this.score = score;
    }
}
