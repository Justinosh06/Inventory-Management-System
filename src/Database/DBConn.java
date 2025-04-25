package Database;

import Constant.SQLConstant;
import Validation.Alert;

import java.sql.*;

// The DBConn class establishes a connection to the MySQL database using the credentials defined in SQLConstant.
// It provides a method to retrieve the established database connection.
public class DBConn {
    Connection con; // Represents the connection to the database.
    Statement statement; // Represents the SQL statement object used to execute queries.

    // Constructor for the DBConn class. It attempts to establish a database connection upon object creation.
    DBConn() { // Declare that the constructor
        try {
            // Establish a connection to the database using the DriverManager.getConnection() method.
            // The database URL, username, and password are retrieved from the SQLConstant class.
            con = DriverManager.getConnection(SQLConstant.Database, SQLConstant.user, SQLConstant.password);
            // Create a Statement object associated with the established connection.
            statement = con.createStatement();
            System.out.println("Database connection successful.");
        } catch (Exception e){
            // If any exception occurs during the connection process (e.g., incorrect credentials, database not running),
            // print an error message to the console and display an alert message to the user.
            System.out.println("Database connection unsuccessful." + e);
            new Alert("Alert  Database Connection Error", "DATABASE CONNECTION ERROR!\nEXITED SYSTEM", "Exit");
        }
    }

    // A getter method to access the established database connection.
    // @return The Connection object representing the database connection.
    public Connection getConnection() { // A getter method to access the connection
        return con; // Return the database connection.
    }
}