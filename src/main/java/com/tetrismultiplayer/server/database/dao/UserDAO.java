package main.java.com.tetrismultiplayer.server.database.dao;

import main.java.com.tetrismultiplayer.server.database.dto.UserDTO;

import javax.persistence.EntityManagerFactory;

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

}

