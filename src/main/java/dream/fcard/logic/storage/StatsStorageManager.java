//@@author nattanyz
package dream.fcard.logic.storage;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//import dream.fcard.logic.stats.SessionList;
//import dream.fcard.logic.stats.Stats;
//
/**
 * Class to handle storage of Stats objects.
 */
public class StatsStorageManager {
//
//    private static String statsSubDir = "./stats";
//    private static String statsPath = "./stats/stats.txt";
//
//    /** Returns true if the stats directory exists. */
//    private static boolean hasDirectory() {
//        // check if parent directory exists. if not, create it
//        File file = new File(statsSubDir);
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    /** Returns true if the stats.txt file exists. */
//    private static boolean hasFile() {
//        File file = new File(statsPath);
//
//        // check if file exists
//        if (!file.exists()) {
//            System.out.println("The stats.txt file does not exist.");
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     * Load the stored Stats object. Else, create a new one.
//     * @return Stats object representing user's overall statistics
//     */
//    //public static Stats getStats() {
//    //    // todo: this method is unused as of now, to be used when storage for Stats is implemented.
//    //    resolveRoot();
//    //    String path = FileReadWrite.resolve(root, statsPath);
//    //
//    //    if (!FileReadWrite.fileExists(path)) {
//    //        return new Stats();
//    //    }
//    //
//    //    try {
//    //        String fileText = FileReadWrite.read(path);
//    //        return Stats.parseStats(fileText);
//    //    } catch (FileNotFoundException e) {
//    //        System.out.println("FILE DOES NOT EXIST");
//    //    }
//    //    return null;
//    //}
//
//    /** Save loginSessions in userStats to disk. */
//    public static void saveLoginSessions() {
//
//        // check that the stats directory exists
//        hasDirectory();
//
//        // check that the stats file exists, else create it
//        hasFile();
//
//        try {
//            FileOutputStream fos = new FileOutputStream(statsPath);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(Stats.getLoginSessions());
//
//            oos.close();
//            fos.close();
//
//            System.out.println("Statistics saved to: " + statsPath);
//        } catch (Exception e) {
//            // temporary haxx
//            e.printStackTrace();
//        }
//    }
//
//    /** Load loginSessions from disk. */
//    public static void loadLoginSessions() {
//        Stats.getUserStats(); // create new Stats object
//
//        // if directory does not exist, or file does not exist, no need to load from file
//        if (!hasDirectory() || !hasFile()) {
//            return;
//        }
//
//        try {
//            SessionList sessionList;
//            FileInputStream fis = new FileInputStream(statsPath);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//
//            sessionList = (SessionList) ois.readObject();
//
//            ois.close();
//            fis.close();
//
//            System.out.println("Statistics imported from: " + statsPath);
//            Stats.setSessionList(sessionList);
//        } catch (Exception e) {
//            // temporary haxx
//            e.printStackTrace();
//        }
//    }
}
