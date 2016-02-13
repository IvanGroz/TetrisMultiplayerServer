package com.muskalanawrot.tetrismultiplayer.server.database.dao;

import javax.persistence.EntityManagerFactory;

import com.muskalanawrot.tetrismultiplayer.server.database.dto.UserDTO;

public class UserDAO extends DAOParentClass<UserDTO>
{

    protected UserDAO(EntityManagerFactory entityManagerFactory)
    {
	super(UserDTO.class, "user", entityManagerFactory);
    }

}
