package Database;

import java.sql.*;

// The Insert class extends DBConn and provides functionality to execute SQL INSERT statements.
// It stores the number of rows affected by the INSERT operation.
public class Insert extends DBConn{
    public int result; // Stores the number of rows affected by the INSERT statement.
    private Connection connection; // Represents the connection to the database.

    // Executes an SQL INSERT statement.
    // @param query The SQL INSERT statement to be executed.
    public void Insert(String query){
        connection = getConnection(); // Establish a connection to the database using the getConnection() method from the parent class (DBConn).

        // Check if the connection was successful.
        if(connection != null){
            System.out.println("Connected successfully(Insert)");

            try{
                // Execute the INSERT statement using the executeUpdate() method of the Statement object.
                // This method returns the number of rows that were inserted into the table.
                result = statement.executeUpdate(query);
            } catch (SQLException e) {
                // If an SQL exception occurs during the execution of the query, wrap it in a RuntimeException and throw it.
                throw new RuntimeException(e);
            }
        } else {
            // If the connection was not successful, print an error message.
            System.out.println("Connected unsuccessfully(Insert)");
        }
    }
}