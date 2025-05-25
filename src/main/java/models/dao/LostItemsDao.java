package models.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import models.LostItems;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.Optional;

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
            WHERE (Status = :status OR :status IS NULL)
              AND (ItemName LIKE '%' + :itemName + '%' OR :itemName IS NULL);
            """)
    List<LostItems> findByFilter(@Bind("itemName") String itemName, @Bind("status") String status);

    // Get specific record by ID
    @SqlQuery("""
            SELECT * FROM LostItems
            WHERE ItemID = :itemID
            """)
    Optional<LostItems> findById(@Bind("itemID") String itemID);
}
