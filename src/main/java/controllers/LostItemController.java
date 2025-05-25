package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import io.github.cdimascio.dotenv.Dotenv;
import models.LostItems;
import models.dao.LostItemsDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class LostItemController {
    static LostItemsDao lostItemsDao;
    static Dotenv dotenv = Dotenv.configure()
            .directory("src/main/resources/.env")
            .load();
    static String dbUrl = dotenv.get("DB_URL");
    static String dbUser = dotenv.get("DB_USERNAME");
    static String dbPass = dotenv.get("DB_PASSWORD");

    static {
        Jdbi jdbi = Jdbi.create(dbUrl, dbUser, dbPass);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(BeanMapper.factory(LostItems.class));
        lostItemsDao = jdbi.onDemand(LostItemsDao.class);
    }

    // Lost Items tab actions
    public static boolean addLostItem(Map<String, String> itemDataMap) {
        System.out.println("Adding new lost item");
        return lostItemsDao.insert(
                itemDataMap.get("Item Name"),
                itemDataMap.get("Description"),
                itemDataMap.get("Category"),
                itemDataMap.get("Location"),
                itemDataMap.get("Date Found"),
                itemDataMap.get("Found By"),
                itemDataMap.get("Status"),
                itemDataMap.get("ImagePath"));
    }

    public static boolean editLostItem(Map<String, String> newItemData, String selectedItemRecordId) {
        try {
            if (!lostItemsDao.update(selectedItemRecordId,
                    newItemData.get("Item Name"),
                    newItemData.get("Description"),
                    newItemData.get("Category"),
                    newItemData.get("Location"),
                    newItemData.get("Date Found"),
                    newItemData.get("Found By"),
                    newItemData.get("Status"),
                    newItemData.get("ImagePath"))) {
                System.out.println("Update failed");
                return false;
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean deleteLostItem(String selectedItemRecordId) {
        System.out.println("Editing lost item");
        return lostItemsDao.delete(selectedItemRecordId);
    }

    public static List<LostItems> filterLostItems(String searchText, String filter) {
        System.out.println("Filtering Lost Items by: " + searchText + " and " + filter);
        if (searchText.isEmpty()) {
            searchText = null;
        } if (filter.equals("All")) {
            filter = null;
        }
        return lostItemsDao.findByFilter(searchText, filter);
    }

    public static List<LostItems> getAllLostItems() {
        return lostItemsDao.findAll();
    }

    public static ArrayList<String> getItemById(String id) {
        Optional<LostItems> record = lostItemsDao.findById(id);
        ArrayList<String> recordList = new ArrayList<>();
        LostItems recordObj = record.get();
        recordList.add(recordObj.getItemName());
        recordList.add(recordObj.getItemDescription());
        recordList.add(recordObj.getItemCategory());
        recordList.add(recordObj.getLocationFound());
        recordList.add(String.valueOf(recordObj.getDateFound()));
        recordList.add(recordObj.getFoundBy());
        recordList.add(recordObj.getStatus());
        recordList.add(recordObj.getImageUrl());
        return recordList;
    }
}
