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
    private Integer id;
    @Column(name = "game_date")
    private Date gameDate;
    @Column(name = "game_type")
    private String gameType;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_name")
    private List<UserDTO> player;
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

    public Date getGameDate()
    {
	return gameDate;
    }

    public void setGameDate(Date gameDate)
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

    public List<UserDTO> getPlayer()
    {
	return player;
    }

    public void setPlayer(List<UserDTO> player)
    {
	this.player = player;
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
