package Database;

import java.sql.*;

// The Delete class extends DBConn and provides functionality to execute SQL DELETE statements.
public class Delete extends DBConn{
    public int result; // Stores the number of rows affected by the DELETE statement.
    private Connection connection; // Represents the connection to the database.

    // Executes an SQL DELETE statement.
    // @param query The SQL DELETE statement to be executed.
    public void Delete(String query){
        connection = getConnection(); // Establish a connection to the database using the getConnection() method from the parent class (DBConn).

        // Check if the connection was successful.
        if(connection != null){
            System.out.println("Connected successfully(Delete)");

            try{
                // Execute the DELETE statement. The executeUpdate() method returns the number of rows affected.
                result = statement.executeUpdate(query);
            } catch (SQLException e) {
                // If an SQL exception occurs during the execution of the query, wrap it in a RuntimeException and throw it.
                throw new RuntimeException(e);
            }
        } else {
            // If the connection was not successful, print an error message.
            System.out.println("Connected unsuccessfully(Delete)");
        }
    }
}