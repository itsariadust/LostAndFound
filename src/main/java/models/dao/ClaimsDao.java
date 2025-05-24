package models.dao;

import models.Claims;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface ClaimsDao {
    // Get all records
    @SqlQuery("""
            SELECT * FROM Claims
            ORDER BY ClaimID DESC
            """)
    List<Claims> findAll();

    // Get by filter (status and item name
    @SqlQuery("""
            SELECT * FROM Claims
            WHERE
                (:status IS NULL OR Status = :status)
                AND
                (:claimantName IS NULL OR claimantName = :claimantName)
            """)
    List<Claims> findByFilter(@Bind("status") String status, @Bind("claimantName") String claimantName);
}
