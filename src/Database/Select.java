package Database;

import java.sql.*;

// The Select class extends DBConn and provides functionality to execute SQL SELECT queries.
// It stores the result of the query in a ResultSet object.
public class Select extends DBConn{
    public ResultSet result; // Stores the ResultSet object containing the results of the SELECT query.
    private Connection connection; // Represents the connection to the database.

    // Executes an SQL SELECT query.
    // @param query The SQL SELECT query to be executed.
    public void Select(String query){
        connection = getConnection(); // Establish a connection to the database using the getConnection() method from the parent class (DBConn).

        // Check if the connection was successful.
        if(connection != null){
            System.out.println("Connected successfully(Select)");

            try{
                // Execute the SELECT query using the executeQuery() method of the Statement object.
                // The result of the query is stored in the 'result' ResultSet.
                result = statement.executeQuery(query);
            } catch (SQLException e) {
                // If an SQL exception occurs during the execution of the query, wrap it in a RuntimeException and throw it.
                throw new RuntimeException(e);
            }
        } else {
            // If the connection was not successful, print an error message.
            System.out.println("Connected unsuccessfully(Select)");
        }
    }
}