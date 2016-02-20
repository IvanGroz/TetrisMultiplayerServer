package main.java.com.tetrismultiplayer.server.database.dao;

import main.java.com.tetrismultiplayer.server.database.dto.ScoreDTO;

import javax.persistence.EntityManagerFactory;

/**
 * Data access object to access score details.
 */
public class ScoreDAO extends DAOParentClass<ScoreDTO>
{
    public ScoreDAO(EntityManagerFactory entityManagerFactory)
    {
	super(ScoreDTO.class, "score", entityManagerFactory);
    }


}
