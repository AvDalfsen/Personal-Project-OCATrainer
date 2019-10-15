package nl.sogyo.ocatrainer;

import java.sql.*;

class DatabaseRequests {
    String queryDatabase(String statement) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oca_trainer", "henk", "henk");
            System.out.println("Database connection successful!");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String returnValue = "";
            while (resultSet.next()) {
                returnValue = resultSet.getString(1);
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
            connection.close();
            return returnValue;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    void updateDatabase(String statement) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oca_trainer", "henk", "henk");
        System.out.println("Database connection successful!");
        PreparedStatement ps = connection.prepareStatement(statement);
        ps.executeUpdate();
        connection.close();
    }
}