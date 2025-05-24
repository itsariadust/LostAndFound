package models.dao;

import models.ClaimWithItemName;
import models.Claims;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(ClaimWithItemName.class)
public interface ClaimsDao {
    // Get all records
    @SqlQuery("""
            SELECT * FROM Claims
            ORDER BY ClaimID DESC
            """)
    List<Claims> findAll();

    // Get by filter (status or item name)
    @SqlQuery("""
            SELECT ClaimID, ClaimantName, LostItems.ItemName, ClaimantContact, ClaimDate, Claims.Status FROM Claims
            JOIN LostItems ON Claims.ItemID = LostItems.ItemID
            WHERE (Claims.Status LIKE '%' + :status + '%' OR :status IS NULL)
              AND (LostItems.ItemName LIKE '%' + :itemName + '%' OR :itemName IS NULL);
            """)
    List<ClaimWithItemName> findByFilter(@Bind("itemName") String itemName, @Bind("status") String status);

    @SqlQuery("""
            SELECT c.ClaimID, c.ClaimantName, i.ItemName,
                   c.ClaimantContact, c.ClaimDate, c.Status
            FROM dbo.Claims c
            JOIN dbo.LostItems i ON c.ItemID = i.ItemID
            ORDER BY c.ClaimDate DESC
            """)
    List<ClaimWithItemName> getAllClaimsWithItemNames();
}
