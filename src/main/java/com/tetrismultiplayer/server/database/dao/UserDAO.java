package main.java.com.tetrismultiplayer.server.database.dao;

import javax.persistence.EntityManagerFactory;

import main.java.com.tetrismultiplayer.server.database.dto.UserDTO;

public class UserDAO extends DAOParentClass<UserDTO>
{

    protected UserDAO(EntityManagerFactory entityManagerFactory)
    {
	super(UserDTO.class, "user", entityManagerFactory);
    }

}
