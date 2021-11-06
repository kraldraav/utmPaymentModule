package Core.Classes;

import Core.Config;
import Core.Interfaces.DBConnector;

import java.sql.*;

public class MysqlConnector implements DBConnector {

    private static final String url = "jdbc:mysql://" + Config.Instance.getSetting("dbhost") + "/" + Config.Instance.getSetting("dbname");
    private static final String user = Config.Instance.getSetting("dbuser");
    private static final String password = Config.Instance.getSetting("dbpassword");

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet resultSet;

    public MysqlConnector() {
        OpenConnection();
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public void OpenConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public PreparedStatement prepareExecuting(String query) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }

        return stmt;
    }

    @Override
    public void execQuery(String query) {
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
    }

    @Override
    public void CloseConnection() {
        try {
            conn.close();
        } catch (SQLException se) {
            System.out.println(se.getSQLState());
        }
        try {
            stmt.close();
        } catch (SQLException se) {
            System.out.println(se.getSQLState());
        }
        try {
            resultSet.close();
        } catch (SQLException se) {
            System.out.println(se.getSQLState());
        }
    }
}
