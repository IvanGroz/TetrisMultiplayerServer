package main.java.com.tetrismultiplayer.server.database.dao;

import main.java.com.tetrismultiplayer.server.database.dto.ScoreDTO;
import main.java.com.tetrismultiplayer.server.database.dto.UserDTO;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAOParentClass<UserDTO>
{

    public UserDAO(EntityManagerFactory entityManagerFactory)
    {
        super(UserDTO.class, "player", entityManagerFactory);
    }

    public UserDTO getUserByNickname(String nickname)
    {
	UserDTO user = getSingleEntity("name = '" + nickname + "'");
	return user;
    }

    public ArrayList<Integer> getUsersInvScores(String nickname)
    {
	UserDTO user;
	user = getSingleEntity("name = '" + nickname + "'");
	List<ScoreDTO> scores = user.getScores();
	ArrayList<Integer> scoreValues = new ArrayList<Integer>();
	for(int i = 0; i < scores.size(); i++)
	{
	    if(scores.get(0).getGame().getGameType().equals("INDIVIDUAL"))
	    {
		scoreValues.add(scores.get(i).getScore());
	    }
	}
	return scoreValues;
    }

    public ArrayList<Integer> getUsersCoopScores(String nickname)
    {
	UserDTO user;
	user = getSingleEntity("name = '" + nickname + "'");
	List<ScoreDTO> scores = user.getScores();
	ArrayList<Integer> scoreValues = new ArrayList<Integer>();
	for(int i = 0; i < scores.size(); i++)
	{
	    if(scores.get(0).getGame().getGameType().equals("COOPERATION"))
	    {
		scoreValues.add(scores.get(i).getScore());
	    }
	}
	return scoreValues;
    }

    public ArrayList<Integer> getUsersConcScores(String nickname)
    {
	UserDTO user;
	user = getSingleEntity("name = '" + nickname + "'");
	List<ScoreDTO> scores = user.getScores();
	ArrayList<Integer> scoreValues = new ArrayList<Integer>();
	for(int i = 0; i < scores.size(); i++)
	{
	    if(scores.get(0).getGame().getGameType().equals("CONCURRENT"))
	    {
		scoreValues.add(scores.get(i).getScore());
	    }
	}
	return scoreValues;
    }
}

