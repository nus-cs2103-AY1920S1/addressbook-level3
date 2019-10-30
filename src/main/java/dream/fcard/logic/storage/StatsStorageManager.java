//@@author nattanyz
package dream.fcard.logic.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import dream.fcard.logic.stats.Stats;

/**
 * Class to handle storage of Stats objects.
 */
public class StatsStorageManager {

    private static String statsSubDir = "./stats";
    private static String statsPath = "./stats/stats.txt";

    /** Returns true if the stats directory exists. */
    private static boolean hasDirectory() {
        // check if parent directory exists. if not, create it
        File file = new File(statsSubDir);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            return false;
        } else {
            return true;
        }
    }

    /** Returns true if the stats.txt file exists. */
    private static boolean hasFile() {
        File file = new File(statsPath);

        // check if file exists
        if (!file.exists()) {
            System.out.println("The stats.txt file does not exist.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        return true;
    }

    /**
     * Load the stored Stats object. Else, create a new one.
     * @return Stats object representing user's overall statistics
     */
    //public static Stats getStats() {
    //    // todo: this method is unused as of now, to be used when storage for Stats is implemented.
    //    resolveRoot();
    //    String path = FileReadWrite.resolve(root, statsPath);
    //
    //    if (!FileReadWrite.fileExists(path)) {
    //        return new Stats();
    //    }
    //
    //    try {
    //        String fileText = FileReadWrite.read(path);
    //        return Stats.parseStats(fileText);
    //    } catch (FileNotFoundException e) {
    //        System.out.println("FILE DOES NOT EXIST");
    //    }
    //    return null;
    //}

    /** Save Stats file to disk. */
    public static void saveStats() {

        // check that the stats directory exists
        hasDirectory();

        // check that the stats file exists, else create it
        hasFile();

        try {
            FileOutputStream fos = new FileOutputStream(statsPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(Stats.getUserStats());

            oos.close();
            fos.close();

            System.out.println("Statistics saved to: " + statsPath);
        } catch (Exception e) {
            // temporary haxx
            e.printStackTrace();
        }
    }
}
