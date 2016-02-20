package main.java.com.tetrismultiplayer.server.database.dao;

import main.java.com.tetrismultiplayer.server.database.dto.GameDTO;

import javax.persistence.EntityManagerFactory;

/**
 * Data access object to access game details.
 */
public class GameDAO extends DAOParentClass<GameDTO>
{
    public GameDAO(EntityManagerFactory entityManagerFactory)
    {
        super(GameDTO.class, "game", entityManagerFactory);
    }

    public GameDTO getGameByDate(Long date)
    {
        GameDTO game = getSingleEntity("game_date = '" + date + "'");
        return game;
    }
}
