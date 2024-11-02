package one.util;

public class PathUtils {

    private static final String P_PATH="src\\images\\";

    public static String getRealPath(String relativePath){

        return P_PATH+relativePath;
    }

}
