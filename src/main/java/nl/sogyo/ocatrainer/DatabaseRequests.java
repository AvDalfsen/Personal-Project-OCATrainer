package nl.sogyo.ocatrainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DatabaseRequests {
    void createDatabaseConnection(String statement) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oca_trainer", "henk", "henk");
            System.out.println("Database connection successful!");
            Statement stmt = connection.createStatement();
            stmt.execute(statement);
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}