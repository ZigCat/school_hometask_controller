package com.github.zigcat.ormlite.services;

import com.github.zigcat.ormlite.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class Security {
    public static String badRequestMessage = "Stopped by client's bad request(400)";
    public static String serverErrorMessage = "Stopped by server error(500)";
    public static String queryParamMessage = "Stopped by wrong query param(400)";
    public static String forbiddenMessage = "Stopped by unauthorized/forbidden request";

    public static User authorize(String login, String password) throws SQLException {
        for(User u: User.userControl.getService().listAll(User.userControl.getDao())){
            if(u.getEmail().equals(login) && BCrypt.checkpw(password, u.getPassword())){
                return u;
            }
        }
        return null;
    }
}
