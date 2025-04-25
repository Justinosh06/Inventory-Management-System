package Constant;

// The SQLConstant class defines constants related to the MySQL database connection.
// It stores the database URL, username, and password for easy access and modification.
public class SQLConstant {
    // The JDBC URL for connecting to the MySQL database named "inventory" running on localhost at port 3306.
    public static final String Database = "jdbc:mysql://localhost:3306/inventory";
    // The username used to authenticate with the MySQL database. In this case, it's "root".
    public static final String user = "root";
    // The password associated with the specified MySQL user. In this case, it's an empty string (no password).
    public static final String password = "";
}