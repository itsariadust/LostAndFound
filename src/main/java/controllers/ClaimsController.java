package controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import io.github.cdimascio.dotenv.Dotenv;
import models.ClaimWithItemName;
import models.Claims;
import models.dao.ClaimsDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClaimsController {
    static ClaimsDao claimsDao;
    static Dotenv dotenv = Dotenv.configure()
            .directory("src/main/resources/.env")
            .load();
    static String dbUrl = dotenv.get("DB_URL");
    static String dbUser = dotenv.get("DB_USERNAME");
    static String dbPass = dotenv.get("DB_PASSWORD");

    static {
        Jdbi jdbi = Jdbi.create(dbUrl, dbUser, dbPass);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(BeanMapper.factory(Claims.class));
        jdbi.registerRowMapper(BeanMapper.factory(ClaimWithItemName.class));
        claimsDao = jdbi.onDemand(ClaimsDao.class);
    }

    public static boolean addClaim(Map<String, String> claimData) {
        System.out.println("Adding new claim");
        return claimsDao.insert(
                claimData.get("Item Claimed"),
                claimData.get("Claimant Name"),
                claimData.get("Claimant Contact"),
                claimData.get("Claim Date"),
                claimData.get("Ownership Proof"),
                claimData.get("Status"));
    }

    public static boolean editClaim(Map<String, String> newClaimData, String selectedClaimRecordId) {
        System.out.println("Editing claim");
        try {
            if (!claimsDao.update(
                    selectedClaimRecordId,
                    newClaimData.get("Item Claimed"),
                    newClaimData.get("Claimant Name"),
                    newClaimData.get("Claimant Contact"),
                    newClaimData.get("Claim Date"),
                    newClaimData.get("Ownership Proof"),
                    newClaimData.get("Status"),
                    newClaimData.get("Approved By"),
                    newClaimData.get("Approval Date"))) {
                return false;
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean deleteClaim(String selectedClaimRecordId) {
        System.out.println("Editing claim");
        return claimsDao.delete(selectedClaimRecordId);
    }

    public static List<ClaimWithItemName> filterClaims(String searchText, String filter) {
        System.out.println("Filtering Claims by: " + searchText + " and " + filter);
        if (searchText.isEmpty()) {
            searchText = null;
        } if (filter.equals("All")) {
            filter = null;
        }
        return claimsDao.findByFilter(searchText, filter);
    }

    public static List<ClaimWithItemName> getAllClaimsWithItemName() {
        return claimsDao.getAllClaimsWithItemNames();
    }

    public static ArrayList<String> getClaimById(String id) {
        Optional<ClaimWithItemName> record = claimsDao.findById(id);
        ArrayList<String> recordList = new ArrayList<>();
        ClaimWithItemName recordObj = record.get();
        recordList.add(recordObj.getClaimantName());
        recordList.add(recordObj.getItemName());
        recordList.add(recordObj.getClaimantContact());
        recordList.add(String.valueOf(recordObj.getClaimDate()));
        recordList.add(recordObj.getDescriptionOfProof());
        recordList.add(recordObj.getStatus());
        recordList.add(recordObj.getApproverName());
        recordList.add(String.valueOf(recordObj.getApprovalDate()));
        return recordList;
    }
}
