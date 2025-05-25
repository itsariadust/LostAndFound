package models.dao;

import models.ClaimWithItemName;
import models.Claims;
import models.LostItems;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.Optional;

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

    // Get specific record by ID
    @SqlQuery("""
            SELECT c.ClaimantName, i.ItemName, c.ClaimDate,
                   c.DescriptionOfProof, c.Status,
                   u.username AS approverName, c.ApprovalDate
            FROM Claims c
                INNER JOIN LostItems i ON i.ItemID = c.ItemID
                LEFT JOIN System_Users u ON c.ApprovedBy = u.user_id
            WHERE ClaimID = :claimID
            """)
    Optional<ClaimWithItemName> findById(@Bind("claimID") String claimID);
}
