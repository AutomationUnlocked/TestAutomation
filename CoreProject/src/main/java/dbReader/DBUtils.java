package dbReader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.HashMap;

public class DBUtils {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static Connection connection = null;

    public static synchronized Connection connectToDB(String url, String userName, String password) {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            if (connection != null)
                logger.info("Connected to DB");
        } catch (Exception e) {
            logger.warn("RDB connection FAILED!!!!!");
            Assert.fail("Please check the Connection parameters");
            e.printStackTrace();
        }
        return connection;
    }

    public static synchronized void closeDBConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            logger.warn("Exception in closing a connection");
        }
    }

    public static synchronized void insertDataToDB(Connection connection, String insertQuery) {
        Statement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement(insertQuery);
            ((PreparedStatement) insertStatement).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Exception in data insertion");
        }
    }

    public static HashMap<String, String> getMapFromResultSet(ResultSet resultSet) {
        ResultSetMetaData md = null;
        HashMap columnValueMap = null;
        try {
            md = resultSet.getMetaData();
            int columns = md.getColumnCount();
            while (resultSet.next()) {
                columnValueMap = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    columnValueMap.put(md.getColumnName(i), resultSet.getString(i));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return columnValueMap;
    }

}
