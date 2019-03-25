package com.securitybox.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DataStoreDatabase {

    // Open JDBC connection.
    Connection conn;;

    public Connection getConn(){
        return conn;
    }
    DataStoreDatabase() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/f486cLdauJ?" +
                    "user=f486cLdauJ&password=l5u4SLi22H");
            System.out.println("db connection succss");
        } catch (SQLException e) {
           System.out.println(e.getCause());
        }

    }
}
