package controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        if (!lostItemsDao.insert(
                itemDataMap.get("Item Name"),
                itemDataMap.get("Description"),
                itemDataMap.get("Category"),
                itemDataMap.get("Location"),
                Timestamp.valueOf(LocalDateTime.now()),
                itemDataMap.get("Found By"),
                itemDataMap.get("Status"),
                itemDataMap.get("ImagePath"))) {
            System.out.println("Insert failed");
            return false;
        }
        return true;
    }

    public static void editLostItem(Map<String, String> newItemData) {
        System.out.println("Editing lost item");
        newItemData.forEach((field, value) ->
                System.out.println(field + ": " + value));
    }

    public static void deleteLostItem() {
        System.out.println("Editing lost item");
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
}
