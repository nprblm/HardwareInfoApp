package ua.vitalik.hardwareinfoapp;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DB {
    private static Connection connection;
    private static ArrayList<String> gpu_list = new ArrayList<>();
    private static ArrayList<String> cpu_list = new ArrayList<>();
    private static final String database = "src/main/resources/db/sqlite.db";

    //record all info from db to -> ArrayLists
    public void record() throws IOException, SQLException {

        ResultSet cpu_results = selectDBTable("cpu_info");
        ResultSet gpu_results = selectDBTable("gpu_info");

        while (cpu_results.next()) {
            cpu_list.add(cpu_results.getString(2).trim());
        }
        while (gpu_results.next()) {
            gpu_list.add(gpu_results.getString(2).trim());
        }
    }

    //get GPU list
    public static ArrayList<String> getGPUList()
    {
        return gpu_list;
    }

    //get CPU list
    public static ArrayList<String> getCPUList()
    {
        return cpu_list;
    }

    //select data from tables
    public static ResultSet selectDBTable(String str) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM " + str);
        return result;
    }

}
