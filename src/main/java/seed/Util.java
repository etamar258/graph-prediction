package seed;

public class Util {
    public static String escapeTitle(String title) {
        String titleContent = title;
        String newContent = titleContent;
        if (titleContent.indexOf("\"") != -1) {
            newContent = titleContent.replace('"', '\'');
        }
        if (!titleContent.equals(newContent)) {
            return title.replaceFirst(titleContent, newContent);
        } else {
            return title;
        }
    }
}
