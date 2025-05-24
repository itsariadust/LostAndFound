package models.dao;

import models.LostItems;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.*;

import java.sql.Timestamp;
import java.util.List;

public interface LostItemsDao {
    // Insert record
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

    // Get all records
    @SqlQuery("""
            SELECT * FROM LostItems
            ORDER BY ItemID DESC
            """)
    List<LostItems> findAll();

    // Get by filter (status or item name)
    @SqlQuery("""
            SELECT * FROM LostItems
            WHERE
                (:status IS NULL OR Status = :status)
                AND
                (:itemName IS NULL OR ItemName = :itemName)
            """)
    List<LostItems> findByFilter(@Bind("status") String status, @Bind("itemName") String itemName);
}
