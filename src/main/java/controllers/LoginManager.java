package controllers;

import models.dao.LoginDao;
import models.SystemUsers;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.mindrot.jbcrypt.BCrypt;
import io.github.cdimascio.dotenv.Dotenv;

public class LoginManager {
    private static LoginDao loginDao;
    static Dotenv dotenv = Dotenv.configure()
            .directory("src/main/resources/.env")
            .load();

    static String dbUrl = dotenv.get("DB_URL");
    static String dbUser = dotenv.get("DB_USERNAME");
    static String dbPass = dotenv.get("DB_PASSWORD");
    public LoginManager() {

        Jdbi jdbi = Jdbi.create(dbUrl, dbUser, dbPass);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(BeanMapper.factory(SystemUsers.class));
        loginDao = jdbi.onDemand(LoginDao.class);
    }

    public static Boolean login(String username, String password) {
        if (!loginDao.findByUsername(username)) {
            return false;
        }
        String hashedPassword = loginDao.getPassword(username);
        return BCrypt.checkpw(password, hashedPassword);
    }
}