package main.java.com.tetrismultiplayer.server.database.dao;

import main.java.com.tetrismultiplayer.server.database.dto.GameDTO;

import javax.persistence.EntityManagerFactory;

public class GameDAO extends DAOParentClass<GameDTO>
{
    public GameDAO(EntityManagerFactory entityManagerFactory)
    {
	super(GameDTO.class, "game", entityManagerFactory);
    }
}
