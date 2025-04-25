package Account;

public class User {
    private static String userID;
    private static String username;
    private static String password;
    private static int lowStockThreshold;

    public static void setUserID(String userid){userID = userid;}

    public static void setUsername(String user){
        username = user;
    }

    public static String getUserID(){
        return userID;
    }

    public static String getUsername(){
        return username;
    }

    public static int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public static void setLowStockThreshold(int lowStockThreshold) {
        User.lowStockThreshold = lowStockThreshold;
    }

    public static String getPassword() {return password;}

    public static void setPassword(String password) {User.password = password;}
}
