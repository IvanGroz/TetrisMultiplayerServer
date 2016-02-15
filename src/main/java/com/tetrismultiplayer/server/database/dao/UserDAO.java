package main.java.com.tetrismultiplayer.server.database.dao;

import main.java.com.tetrismultiplayer.server.database.dto.UserDTO;

import javax.persistence.EntityManagerFactory;

public class UserDAO extends DAOParentClass<UserDTO>
{

    protected UserDAO(EntityManagerFactory entityManagerFactory)
    {
        super(UserDTO.class, "user", entityManagerFactory);
    }

}

