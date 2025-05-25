package models.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import models.ClaimWithItemName;
import models.Claims;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface ClaimsDao {
    // Insert entry
    @SqlUpdate("""
            DECLARE @ItemID int;
            SELECT @ItemID = ItemID FROM dbo.LostItems WHERE ItemName = :itemName;

            INSERT INTO Claims (ItemID, ClaimantName, ClaimantContact, ClaimDate, DescriptionOfProof, Status)
            VALUES(
                   @ItemID,
                   :claimantName,
                   :claimantContact,
                   :claimDate,
                   :description,
                   :status
            )
            """)
    boolean insert(@Bind("itemName") String itemName,
                   @Bind("claimantName") String claimantName,
                   @Bind("claimantContact") String claimantContact,
                   @Bind("claimDate") String claimDate,
                   @Bind("description") String description,
                   @Bind("status") String status);

    // Edit entry
    @SqlUpdate("""
            DECLARE @UserID int;
            DECLARE @ItemID int;
            SELECT @UserID = user_id FROM dbo.System_Users WHERE username = :username;
            SELECT @ItemID = ItemID FROM dbo.LostItems WHERE ItemName = :itemName;
            
            UPDATE Claims
            SET
                ItemID = @ItemID,
                ClaimantName = :claimantName,
                ClaimantContact = :claimantContact,
                ClaimDate = :claimDate,
                DescriptionOfProof = :description,
                Status = :status,
                ApprovedBy = @UserID,
                ApprovalDate = :approvalDate
            WHERE ClaimID = :claimId
            """)
    boolean update(@Bind("claimId") String claimId,
                   @Bind("itemName") String itemName,
                   @Bind("claimantName") String claimantName,
                   @Bind("claimantContact") String claimantContact,
                   @Bind("claimDate") String claimDate,
                   @Bind("description") String description,
                   @Bind("status") String status,
                   @Bind("username") String username,
                   @Bind("approvalDate") String approvalDate)
            throws SQLServerException;

    // Delete record
    @SqlUpdate("""
            DELETE FROM Claims WHERE ClaimID = :claimId
            """)
    boolean delete(@Bind("claimId") String claimId);

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
            WHERE (Claims.Status = :status OR :status IS NULL)
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
            SELECT c.ClaimantName, i.ItemName, c.ClaimantContact, c.ClaimDate,
                   c.DescriptionOfProof, c.Status,
                   u.username AS approverName, c.ApprovalDate
            FROM Claims c
                INNER JOIN LostItems i ON i.ItemID = c.ItemID
                LEFT JOIN System_Users u ON c.ApprovedBy = u.user_id
            WHERE ClaimID = :claimID
            """)
    Optional<ClaimWithItemName> findById(@Bind("claimID") String claimID);
}
