package Database;

import java.sql.*;

// The Update class extends DBConn and provides functionality to execute SQL UPDATE statements.
public class Update extends DBConn{
    public int result; // Stores the number of rows affected by the UPDATE statement.
    private Connection connection; // Represents the connection to the database.

    // Executes an SQL UPDATE query.
    // @param query The SQL UPDATE statement to be executed.
    public void Update(String query){
        connection = getConnection(); // Establish a connection to the database using the getConnection() method from the parent class (DBConn).

        // Check if the connection was successful.
        if(connection != null){
            System.out.println("Connected successfully(Update)");

            try{
                result = statement.executeUpdate(query); // Execute the UPDATE query and store the number of affected rows in the 'result' variable.
            } catch (SQLException e) {
                throw new RuntimeException(e); // If an SQL exception occurs during query execution, wrap it in a RuntimeException and rethrow it.
            }
        } else {
            System.out.println("Connected unsuccessfully(Update)");
        }
    }
}