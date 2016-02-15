package main.java.com.tetrismultiplayer.server.database.dto;


import java.io.Serializable;

/**
 * Created by Marcin on 2016-02-15.
 */

public class UserDTO implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
