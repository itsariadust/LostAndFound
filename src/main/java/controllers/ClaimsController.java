package controllers;

import io.github.cdimascio.dotenv.Dotenv;
import models.ClaimWithItemName;
import models.Claims;
import models.LostItems;
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

    public static void addClaim(Map<String, String> claimData) {
        System.out.println("Adding new claim");
        claimData.forEach((field, value) ->
                System.out.println(field + ": " + value));
    }

    public static void editClaim(Map<String, String> newClaimData) {
        System.out.println("Editing claim");
        newClaimData.forEach((field, value) ->
                System.out.println(field + ": " + value));
    }

    public static void deleteClaim() {
        System.out.println("Editing claim");
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
        recordList.add(String.valueOf(recordObj.getClaimDate()));
        recordList.add(recordObj.getDescriptionOfProof());
        recordList.add(recordObj.getStatus());
        recordList.add(recordObj.getApproverName());
        recordList.add(String.valueOf(recordObj.getApprovalDate()));
        return recordList;
    }
}
