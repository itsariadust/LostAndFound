package models;

import org.jdbi.v3.sqlobject.statement.*;
import org.jdbi.v3.sqlobject.customizer.*;

public interface LoginDao {
    @SqlQuery("""
                SELECT * FROM System_Users WHERE username = :username
            """)
    boolean findByUsername(@Bind("username") String username);

    @SqlQuery("""
                SELECT password FROM System_Users WHERE username = :username
            """)
    String getPassword(@Bind("username") String username);
}
