package dream.fcard.logic.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.stats.StatsHolder;
import dream.fcard.model.Deck;
import dream.fcard.model.TestCase;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.util.FileReadWrite;
import dream.fcard.util.json.JsonParser;
import dream.fcard.util.json.exceptions.JsonFormatException;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.stats.DateTimeUtil;

/**
 * Interface to managing storage for the program.
 */
public class StorageManager {

    private static boolean isRootResolved = false;

    private static String root;
    private static String decksSubDir = "./decks";
    private static String statsSubDir = "./stats";
    private static String userStatsFileName = "stats.json";
    private static String userStatsFileFullPath;
    private static String deckStatsFileName = "deckstats.json";
    private static String deckStatsFileFullPath;
    private static String codeSubDir = "./code";

    private Logger logger = LogsCenter.getLogger(StorageManager.class);

    /**
     * Determine root directory of the application:
     *  main for intellij project,
     *  jar directory when running as jar,
     *  current executed directory for default
     * following resolution root is resolved to `root/data/`
     */
    public static void resolveRoot() {
        if (isRootResolved) {
            return;
        }
        URL thisClassUrl = StorageManager.class.getResource("StorageManager.class");

        switch (thisClassUrl.getProtocol()) {
        case "file":
            try {
                String platformIndependentPath = Paths.get(StorageManager.class
                        .getResource("StorageManager.class").toURI()).toString();
                root = FileReadWrite.resolve(platformIndependentPath, "../../../../../../../../../");
            } catch (URISyntaxException i) {
                System.out.println("error");
                System.exit(-1);
            }
            break;
        case "jar":
            try {
                root = FileReadWrite.resolve(
                        new File(StorageManager.class.getProtectionDomain().getCodeSource().getLocation().toURI())
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
        resolveStatsFilesPath();
        isRootResolved = true;
    }

    /**
     * User provide directory to use for storage
     *
     * @param path path to new directory for storage
     */
    public static void provideRoot(String path) {
        root = path;
        resolveStatsFilesPath();
        isRootResolved = true;
    }

    /**
     * Returns value of current root.
     *
     * @return root directory
     */
    public static String getRoot() {
        return root;
    }

    // ROOT CODE --------------------------------------------------------------

    /**
     * Write a deck into decks storage.
     *
     * @param deck deck object to write
     */
    public static void writeDeck(Deck deck) {
        resolveRoot();
        String path = FileReadWrite.resolve(root, decksSubDir + "/" + deck.getDeckName() + ".json");
        FileReadWrite.write(path, deck.toJson().toString());
    }

    /**
     * Write multiple decks into decks storage.
     *
     * @param decks ArrayList of decks object to write
     */
    public static void writeDecks(ArrayList<Deck> decks) {
        resolveRoot();
        for (Deck deck : decks) {
            String path = FileReadWrite.resolve(root, decksSubDir + "/" + deck.getDeckName() + ".json");
            FileReadWrite.write(path, deck.toJson().toString());
        }
    }

    /**
     * Delete a deck.
     * @param deck the deck to remove.
     */
    public static void deleteDeck(Deck deck) {
        resolveRoot();
        String path = FileReadWrite.resolve(root, decksSubDir + "/" + deck.getDeckName() + ".json");
        FileReadWrite.delete(path);
    }

    /**
     * Load all decks in storage.
     *
     * @return ArrayList of decks in storage
     */
    public static ArrayList<Deck> loadDecks() {
        resolveRoot();
        String path = FileReadWrite.resolve(root, decksSubDir);

        if (!FileReadWrite.fileExists(path)) {
            return new ArrayList<>();
        }

        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            return walk.filter(Files::isRegularFile)
                    .map(x -> loadDeck(x.toString()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads a single deck.
     *
     * @param filePath Must be valid existing filepath to a deck json file.
     * @return deck object
     */
    public static Deck loadDeck(String filePath) {
        try {
            return parseDeckJsonFile(FileReadWrite.read(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("FILE DOES NOT EXIST");
        }
        return null;
    }

    /**
     * Parse input as a json deck string.
     *
     * @param input json deck string
     * @return deck object
     */
    private static Deck parseDeckJsonFile(String input) {
        try {
            try {
                ArrayList<FlashCard> cards = new ArrayList<>();
                JsonObject deckJson = JsonParser.parseJsonInput(input).getObject();
                for (JsonValue x : deckJson.get(Schema.DECK_CARDS).getArray()) {
                    JsonObject cardJson = x.getObject();
                    FlashCard card = null;
                    switch (cardJson.get(Schema.TYPE_FIELD).getString()) {
                    case Schema.FRONT_BACK_TYPE:
                        card = new FrontBackCard(
                                cardJson.get(Schema.FRONT_FIELD).getString(),
                                cardJson.get(Schema.BACK_FIELD).getString());
                        break;
                    case Schema.JAVASCRIPT_TYPE:
                        card = new JavascriptCard(
                                cardJson.get(Schema.FRONT_FIELD).getString(),
                                cardJson.get(Schema.BACK_FIELD).getString());
                        break;
                    case Schema.MULTIPLE_CHOICE_TYPE:
                        ArrayList<String> choices = new ArrayList<>();
                        for (JsonValue choiceJson : cardJson.get(Schema.CHOICES_FIELD).getArray()) {
                            choices.add(choiceJson.getString());
                        }
                        card = new MultipleChoiceCard(
                                cardJson.get(Schema.FRONT_FIELD).getString(),
                                cardJson.get(Schema.BACK_FIELD).getString(),
                                choices);
                        break;
                    case Schema.JAVA_TYPE:
                        ArrayList<TestCase> testcases = new ArrayList<>();
                        for (JsonValue caseJson : cardJson.get(Schema.TEST_CASES_FIELD).getArray()) {
                            testcases.add(new TestCase(
                                    caseJson.getObject().get(Schema.TEST_CASE_INPUT).getString(),
                                    caseJson.getObject().get(Schema.TEST_CASE_OUTPUT).getString()));
                        }
                        card = new JavaCard(cardJson.get(Schema.FRONT_FIELD).getString(), testcases);
                        break;
                    default:
                        System.out.println("Unexpected card type, but silently continues");
                        continue;
                    }
                    cards.add(card);
                }
                return new Deck(cards, deckJson.get(Schema.DECK_NAME).getString());
            } catch (JsonWrongValueException e1) {
                System.out.println("JSON file wrong schema");
            }
        } catch (JsonFormatException e2) {
            System.out.println("JSON file has errors\n" + e2.getMessage());
        }
        return null;
    }

    /**
     * Overwrite all files in the subdirectory with the given set of decks.
     *
     * @param decks an array list of decks
     */
    public static void saveAll(ArrayList<Deck> decks) {
        resolveRoot();
        for (Deck d : decks) {
            writeDeck(d);
        }
    }

    // DECK CODE --------------------------------------------------------------

    /**
     * Resolve path to user stats file.
     */
    public static void resolveStatsFilesPath() {
        userStatsFileFullPath = FileReadWrite.resolve(root, statsSubDir + "/" + userStatsFileName);
        deckStatsFileFullPath = FileReadWrite.resolve(root, statsSubDir + "/" + deckStatsFileName);
    }

    /**
     * Save user stats data.
     */
    public static void saveUserStats() {
        resolveRoot();
        FileReadWrite.write(userStatsFileFullPath, StatsHolder.getUserStats().getSessionList().toJson().toString());
    }

    /**
     * Initialize and load user stats data if any.
     */
    public static void loadUserStats() {
        resolveRoot();

        try {
            ArrayList<Session> arr = new ArrayList<>();
            JsonValue statsJson = JsonParser.parseJsonInput(FileReadWrite.read(
                userStatsFileFullPath));
            for (JsonValue sessionJson : statsJson.getArray()) {
                JsonObject sessionJsonObj = sessionJson.getObject();
                Session session = new Session(
                        DateTimeUtil.getDateTimeFromJson(sessionJsonObj.get(Schema.SESSION_START).getObject()),
                        DateTimeUtil.getDateTimeFromJson(sessionJsonObj.get(Schema.SESSION_END).getObject()));
                arr.add(session);
            }
            StatsHolder.getUserStats().setSessionList(new SessionList(arr));
        } catch (FileNotFoundException e) {
            System.out.println("STATS FILE DOES NOT EXIST");
        } catch (JsonFormatException | NullPointerException e) {
            System.out.println("STATS JSON IS ILL FORMED\n" + e.getMessage());
        } catch (JsonWrongValueException e) {
            System.out.println("UNEXPECTED JSON FORMAT FOR STATS\n" + e.getMessage());
        }
    }

    /**
     * Save deck stats data.
     */
    public static void saveDeckStats() {
        resolveRoot();
        FileReadWrite.write(deckStatsFileFullPath, StatsHolder.getDeckStats().toJson().toString());
    }

    /**
     * Initialise and load deck stats data, if any.
     */
    public static void loadDeckStats() {
        resolveRoot();

        try {
            JsonValue deckStatsJson = JsonParser.parseJsonInput(FileReadWrite.read(
                    deckStatsFileFullPath));
            JsonArray keys = deckStatsJson.getObject().get(Schema.STATS_DECK_STRINGS).getArray();
            JsonArray sessions = deckStatsJson.getObject().get(Schema.STATS_DECK_SESSIONS).getArray();
            for (int i = 0; i < keys.size(); i++) {
                String deckName = keys.get(i).getString();
                StatsHolder.getDeckStats().createNewDeck(deckName);
                for (JsonValue sessionJson : sessions.get(i).getArray()) {
                    JsonObject sessionJsonObj = sessionJson.getObject();
                    Session session = new Session(
                            DateTimeUtil.getDateTimeFromJson(sessionJsonObj.get(Schema.SESSION_START).getObject()),
                            DateTimeUtil.getDateTimeFromJson(sessionJsonObj.get(Schema.SESSION_END).getObject()));
                    session.setScore(sessionJsonObj.get(Schema.SESSION_SCORE).getString());
                    StatsHolder.getDeckStats().addSessionToDeckSessionList(session, deckName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("DECK STATS FILE DOES NOT EXIST");
        } catch (JsonFormatException | NullPointerException e) {
            System.out.println("DECK STATS JSON IS ILL FORMED\n" + e.getMessage());
        } catch (JsonWrongValueException e) {
            System.out.println("UNEXPECTED JSON FORMAT FOR DECK STATS\n" + e.getMessage());
        }
    }
    // STATS CODE -------------------------------------------------------------

    /**
     * Write a code file to the code sub directory in root.
     * @param fileName  name of file with extensions
     * @param code      string content to be written
     */
    public static void writeCode(String fileName, String code) {
        resolveRoot();
        String path = FileReadWrite.resolve(root, codeSubDir + "/" + fileName);
        FileReadWrite.write(path, code);
    }

    /**
     * Get the path to the code file assuming its in root code subdirectory.
     * @param fileName  name of file
     * @return          full path to file
     */
    public static String getCodePath(String fileName) {
        resolveRoot();
        return FileReadWrite.resolve(root, codeSubDir + "/" + fileName);
    }

    // CODE EXECUTION CODE ----------------------------------------------------
}
