package controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import models.LostItems;
import models.LostItemsDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class LostItemController {
    private static LostItemsDao lostItemsDao;
    static Dotenv dotenv = Dotenv.configure()
            .directory("src/main/resources/.env")
            .load();
    static String dbUrl = dotenv.get("DB_URL");
    static String dbUser = dotenv.get("DB_USERNAME");
    static String dbPass = dotenv.get("DB_PASSWORD");

    public LostItemController() {
        Jdbi jdbi = Jdbi.create(dbUrl, dbUser, dbPass);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(BeanMapper.factory(LostItems.class));
        lostItemsDao = jdbi.onDemand(LostItemsDao.class);
    }

    // Lost Items tab actions
    public static boolean addLostItem(Map<String, String> itemDataMap) {
        System.out.println("Adding new lost item");
        System.out.println(itemDataMap.values());
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

    public static void filterLostItems(String filter) {
        System.out.println("Filtering Lost Items by: " + filter);
        // Todo: filter out lost items
    }
}
