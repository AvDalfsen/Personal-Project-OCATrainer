package nl.sogyo.ocatrainer;

import static org.junit.Assert.*;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseRequestsTest {
    private DatabaseRequests dbReq = new DatabaseRequests();

    @Test
    public void databaseConnectionTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oca_trainer", "henk", "henk");
        assertFalse("When run, the method should open a connection to the database.",connection.isClosed());
        connection.close();
    }

    @Test
    public void databaseConnectionCloseTest() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oca_trainer", "henk", "henk");
        connection.close();
        assertTrue("Once closed, the connection to the database should be closed.",connection.isClosed());
    }

    @Test
    public void dynamicDatabaseConnectAndRequest() {
        String queryResult = dbReq.queryDatabase("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='oca_trainer'");
        assertTrue("When called with an SQL statement in the form of a string, the method should return the results of that query.\n" +
                "In this specific case it should return the names of the tables contained in the database.", queryResult.contains("users"));
    }
}