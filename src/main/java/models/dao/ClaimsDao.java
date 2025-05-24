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
            SELECT * FROM Claims
            WHERE (Status LIKE '%' + :status + '%' OR :status IS NULL)
              AND (ClaimantName LIKE '%' + :claimantName + '%' OR :claimantName IS NULL);
            """)
    List<Claims> findByFilter(@Bind("claimantName") String claimantName, @Bind("status") String status);

    @SqlQuery("""
            SELECT c.ClaimID, c.ClaimantName, i.ItemName,
                   c.ClaimantContact, c.ClaimDate, c.Status
            FROM dbo.Claims c
            JOIN dbo.LostItems i ON c.ItemID = i.ItemID
            ORDER BY c.ClaimDate DESC
            """)
    List<ClaimWithItemName> getAllClaimsWithItemNames();
}
