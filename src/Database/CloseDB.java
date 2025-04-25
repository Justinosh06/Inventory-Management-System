package Database;

import java.sql.*;

// The CloseDB class extends DBConn and provides a utility method for closing database connections.
// It ensures that connections are properly closed to release resources.
public class CloseDB{
    // A static method to close a database connection.
    // @param con The Connection object to be closed.
    public static void closeConnection(Connection con) {  // Static method, takes Connection as argument
        // Check if the provided Connection object is not null (i.e., a valid connection exists).
        if (con != null) {
            try {
                // Attempt to close the database connection.
                con.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                // If an SQL exception occurs during the closing process, print an error message to the standard error stream.
                System.err.println("Error closing database connection: " + e.getMessage()); // Use System.err for errors
            }
        }
        // If the Connection object is null, there's no connection to close, so no action is needed.
    }
}