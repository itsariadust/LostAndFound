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
            VALUES(
                   :itemName,
                   :itemDescription,
                   :itemCategory,
                   :locationFound,
                   :dateFound,
                   foundBy,
                   :status,
                   :imageURL)
            """)
    boolean insert(@Bind("itemName") String itemName,
                   @Bind("itemDescription") String itemDescription,
                   @Bind("itemCategory") String itemCategory,
                   @Bind("locationFound") String locationFound,
                   @Bind("dateFound") String dateFound,
                   @Bind("foundBy") String foundBy,
                   @Bind("status") String status,
                   @Bind("imageURL") String imageURL);

    // Update the record
    @SqlUpdate("""
            UPDATE LostItems
            SET
                ItemName = :itemName,
                ItemDescription = :itemDescription,
                ItemCategory = :itemCategory,
                LocationFound = :locationFound,
                DateFound = :dateFound,
                FoundBy = :foundBy,
                Status = :status,
                ImageURL = :imageURL
            WHERE ItemId = :itemId
            """)
    boolean update(@Bind("itemId") String itemId,
                   @Bind("itemName") String itemName,
                   @Bind("itemDescription") String itemDescription,
                   @Bind("itemCategory") String itemCategory,
                   @Bind("locationFound") String locationFound,
                   @Bind("dateFound") String dateFound,
                   @Bind("foundBy") String foundBy,
                   @Bind("status") String status,
                   @Bind("imageURL") String imageURL)
            throws SQLServerException;

    // Delete the record
    @SqlUpdate("""
            DELETE FROM LostItems WHERE ItemId = :itemId
            """)
    boolean delete(@Bind("itemId") String itemId);

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
