package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseOperation {

    public static String getDetails(String columnName, String tableName,String referenceColumnName, int employeeId) {
        try (Connection connection = DataBaseConnection.getConnection()) {
            String sqlQuery = "SELECT" + " " + columnName + " " + "FROM" + " " + tableName + " " + "WHERE"+ " " +  referenceColumnName+ " = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, employeeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(columnName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
