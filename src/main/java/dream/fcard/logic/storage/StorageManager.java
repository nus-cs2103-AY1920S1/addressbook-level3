package dream.fcard.util;

import dream.fcard.model.Deck;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Interface to managing storage for the program.
 */
public class StorageManager {

    public static String FRONT_BACK_TYPE = "front-back";

    private static boolean isRootResolved = false;

    private static String root;
    private static String decksSubDir = "./decks";

    /**
     * Determine root directory of the application, main for project, directory containing jar
     * for jar files.
     */
    private static void resolveRoot() {
        if (isRootResolved) {
            return;
        }
        URL thisClassUrl = StorageManager.class.getResource("StorageManager.class");

        switch (thisClassUrl.getProtocol()) {
        case "file":
            root = FileReadWrite.resolve(thisClassUrl.getPath(), "../../../../../../../../");
            break;
        case "jar":
            try {
                root = FileReadWrite.resolve(new File(StorageManager.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                        .getPath(), "../");
            } catch (URISyntaxException e) {
                System.out.println("jar is broken as unable to resolve path");
                System.exit(-1);
            }
            break;
        default:
            root = System.getProperty("user.dir");
        }
        root = FileReadWrite.resolve(root, "./data");
        isRootResolved = true;
    }

    /**
     * User provide directory to use for storage
     * @param path
     */
    public static void provideRoot(String path) {
        root = path;
        isRootResolved = true;
    }

    public static void writeDeck(Deck deck) {
        resolveRoot();
        String path = FileReadWrite.resolve(root, decksSubDir + "/" + deck.getName() + ".json");
        FileReadWrite.write(path, deck.toJson().toString());
    }

    public static ArrayList<Deck> loadDecks() {
        return null;
    }

    private static Deck parseDeckJsonFile(String input) {
        return null;
    }
}
