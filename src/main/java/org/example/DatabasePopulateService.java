package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabasePopulateService {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        Path myPath = Paths.get("sql/populate_db.sql");
        try{
            String sql = String.join("/n", Files.readAllLines(myPath));
            database.executeUpdate(sql);
        } catch(Exception e){
            e.printStackTrace();
        }


    }
}
