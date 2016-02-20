package main.java.com.tetrismultiplayer.server.database.dao;

import main.java.com.tetrismultiplayer.server.database.dto.ScoreDTO;

import javax.persistence.EntityManagerFactory;

public class ScoreDAO extends DAOParentClass<ScoreDTO>
{
    public ScoreDAO(EntityManagerFactory entityManagerFactory)
    {
	super(ScoreDTO.class, "score", entityManagerFactory);
    }


}
