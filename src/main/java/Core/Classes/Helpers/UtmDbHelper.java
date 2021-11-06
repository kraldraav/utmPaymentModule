package Core.Classes.Helpers;

import Core.Classes.MysqlConnector;
import Core.Interfaces.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtmDbHelper {
    private DBConnector connector;

    public UtmDbHelper(DBConnector connector) {
        this.connector = connector;
    }

    public String getLastTimestamp() {
        String result = "";
        try {
            connector.execQuery("SELECT * FROM checkpaymenttimestamp WHERE id = 1");
            ResultSet checkPaymentTimestamp = ((MysqlConnector) connector).getResultSet();
            while (checkPaymentTimestamp.next())
                result = checkPaymentTimestamp.getString(2);
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return result;
    }

    public void setLastTimestamp(String sDateTime) {
        try {
            PreparedStatement stmt = ((MysqlConnector) connector).prepareExecuting("UPDATE CheckPaymentTimestamp SET last_timestamp = ? WHERE id = ?");
            stmt.setString(1, sDateTime);
            stmt.setInt(2,1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
    }

    public String getClientAccountIdByTaxNumber(String taxNumber) {
        String result = "";
        try {
            PreparedStatement stmt = ((MysqlConnector) connector).prepareExecuting("SELECT accounts.id AS lic_schet FROM users LEFT JOIN accounts ON accounts.id = users.basic_account WHERE accounts.is_deleted = 0 AND users.tax_number = ?");
            stmt.setString(1, taxNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result = rs.getString("lic_schet");
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return result;
    }
}
