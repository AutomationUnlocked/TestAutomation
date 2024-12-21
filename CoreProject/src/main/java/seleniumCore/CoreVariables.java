package seleniumCore;

import java.util.List;

public class CoreVariables {


    private static String browser;

    private static boolean imageTest;

    private static String imagePath;

    private static List<String> imagePaths;

    public static String getBrowser() {
        return browser;
    }

    public static void setBrowser(String browser) {
        CoreVariables.browser = browser;
    }


    public static boolean isImageTest() {
        return imageTest;
    }

    public static void setImageTest(boolean imageTest) {
        CoreVariables.imageTest = imageTest;
    }

    public static String getImagePath() {
        return imagePath;
    }

    public static void setImagePath(String imagePath) {
        CoreVariables.imagePath = imagePath;
    }

    public static List<String> getImagePaths() {
        return imagePaths;
    }

    public static void setImagePaths(List<String> imagePaths) {
        CoreVariables.imagePaths = imagePaths;
    }
}
