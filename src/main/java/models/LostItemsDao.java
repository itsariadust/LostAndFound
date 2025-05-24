package models;

import org.jdbi.v3.sqlobject.statement.*;

import java.sql.Timestamp;

public interface LostItemsDao {
    @SqlUpdate("""
            INSERT INTO LostItems
            VALUES(?, ?, ?, ?, ?, ?, ?, ?)
            """)
    boolean insert(String itemName,
                   String itemDescription,
                   String itemCategory,
                   String locationFound,
                   Timestamp dateFound,
                   String foundBy,
                   String status,
                   String imageURL);
}
