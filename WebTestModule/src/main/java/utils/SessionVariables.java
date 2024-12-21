package utils;


public class SessionVariables {

    private static String username;

    private static String password;


    public static void setUsername(String username) {
        SessionVariables.username = username;
    }

    public static void setPassword(String password) {
        SessionVariables.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUsername() {
        return username;
    }
}
