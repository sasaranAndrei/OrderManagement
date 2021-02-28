package connection;

import java.sql.*;
import java.util.logging.Logger;

public class MyConnection {
    private static final Logger LOGGER = Logger.getLogger(MyConnection.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost/ordermanagement";
    private static final String USER = "root";
    private static final String PASS = "123456";

    private static Connection connection;

    public MyConnection(){
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = createConnection();
    }

    private Connection createConnection(){
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void close (Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Can't close the connection.");
            }
        }
    }

    public static void close (Statement statement){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Can't close the statement.");
            }
        }
    }

    public static void close (ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Can't close the result set.");
            }
        }
    }



}
