//package dream.fcard.logic.respond;
//
//import dream.fcard.core.commons.core.LogsCenter;
//
//import dream.fcard.logic.exam.Exam;
//import dream.fcard.logic.exam.ExamRunner;
//import dream.fcard.logic.respond.commands.CreateCommand;
//import dream.fcard.logic.respond.commands.EditCommand;
//import dream.fcard.logic.storage.StorageManager;
//import dream.fcard.model.Deck;
//import dream.fcard.model.State;
//import dream.fcard.model.StateEnum;
//import dream.fcard.model.cards.FlashCard;
//import dream.fcard.model.exceptions.DeckNotFoundException;
//import dream.fcard.model.exceptions.IndexNotFoundException;
//import dream.fcard.util.FileReadWrite;
//
///**
// * Enum of regex and response function pairs used by Responder to evaluate input.
// */
//enum Responses {
//    NEXT(
//            "(?i)^(next)(\\s)?",
//            new ResponseGroup[]{},
//            (i, s) -> {
//        LogsCenter.getLogger(Responses.class).info("Current command is NEXT");
//        /*if (programState.getCurrentState() == StateEnum.TEST_ONGOING_WAITING_NEXT) {
//            Exam exam = ExamRunner.getCurrentExam();
//            exam.upIndex();
//            try {
//                FlashCard newCard = exam.getCurrentCard();
//                String question = newCard.getFront();
//                LogsCenter.getLogger(Responses.class).info(question);
//                programState.setCurrentState(StateEnum.TEST_ONGOING_WAITING_ANS);
//            } catch (IndexOutOfBoundsException e) {
//                LogsCenter.getLogger(Responses.class).info("You have reached the end of the test!");
//                LogsCenter.getLogger(Responses.class).info(exam.getResult());
//                programState.setCurrentState(StateEnum.DEFAULT);
//            }
//        } else {
//            //eventually make into exception
//            LogsCenter.getLogger(Responses.class).info("There is no active test right now!");
//        }*/
//        return true;
//    }),
//    HELP(
//            "(?i)^(help)?(\\s)*(command/[\\w\\p{Punct}]+)?(\\s)*",
//            new ResponseGroup[] {},
//            (i, s) -> {
//        System.out.println("Current command is HELP");
//        /*Print out "Available commands are:\n" +
//                "Help [Command]\n" +
//                "Import FILEPATH\n" +
//                "Root DIRECTORY_PATH" +
//                "Export deck/ DECK_NAME path/ DIRECTORY_PATH\n" +
//                "Stats [deck/DECK_NAME]\n" +
//                "View [deck/DECK_NAME]\n" +
//                "Create deck/DECK_NAME\n" +
//                "Test [duration/TIME_LIMIT_ALLOWED] deck/DECK_NAME\n" +
//                "Exit\n" +
//                "Edit deck/DECK_NAME action/DESIRED_ACTION [index/CARD_INDEX] [front/NEW_FRONT_TEXT]" +
//                "[back/NEW_BACK_TEXT]\n";
//         */
//        return true; // capture is valid, end checking other commands
//    }),
//    ROOT("(?i)^r(oot)?(\\s)+.+",
//            new ResponseGroup[] {},
//            (i, s) -> {
//        String path = FileReadWrite.normalizePath(i.split(" ")[1].trim());
//        if (FileReadWrite.pathValidDirectory(path)) {
//            StorageManager.provideRoot(path);
//            s.reloadAllDecks(StorageManager.loadDecks());
//            System.out.println("Successfully changed root");
//        } else {
//            System.out.println("argument is not a valid directory");
//        }
//        return true;
//    }),
//    ROOT_NO_PATH("(?i)^r(oot)?(\\s)*",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        System.out.println("No directory specified, e.g. root ~/Desktop");
//        return true;
//    }),
//    IMPORT("(?i)^i(mport)?(\\s)+.+",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        System.out.println("Current command is IMPORT");
//
//        String path = commandInput.split(" ")[1].trim();
//
//        Deck deck = StorageManager.loadDeck(path);
//        if (deck != null) {
//            StorageManager.writeDeck(deck);
//            programState.addDeck(deck);
//            System.out.println("Successfully added " + path);
//        } else {
//            System.out.println("File does not exist, or file does not match schema for a deck");
//        }
//        return true;
//    }),
//    IMPORT_NO_PATH("(?i)^i(mport)?(\\s)*",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        System.out.println("No path specified, e.g. import ~/Desktop/file.json");
//        return true;
//    }),
//    EXPORT("(?i)^exp(ort)?(\\s)+deck/(\\s)*.+(\\s)+path/(\\s)*.+",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        System.out.println("Current command is EXPORT");
//
//        String[] parts = commandInput.split("deck/")[1].split("path/");
//        String deckName = parts[0].trim();
//        String pathName = parts[1].trim();
//
//        try {
//            Deck d = programState.getDeck(deckName);
//            FileReadWrite.write(FileReadWrite.resolve(pathName, "./" + d.getName() + ".json"), d.toJson().toString());
//        } catch (DeckNotFoundException e) {
//            System.out.println("Deck does not exist");
//        }
//
//        return true;
//    }),
//    EXPORT_NO_PATH("(?i)^exp(ort)?(\\s)+deck/(\\s)*.+",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        System.out.println("No path specified, e.g. export deck/ deckName path/ ~/Desktop");
//        return true;
//    }),
//    EXPORT_NO_DECK("(?i)^exp(ort)?.*",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        System.out.println("No deck specified, e.g. export deck/ deckName path/ ~/Desktop");
//        return true;
//    }),
//    STATS("(?i)^(stats)?(\\s)*(deck/[\\w\\p{Punct}]+)?(\\s)*",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        LogsCenter.getLogger(Responses.class).info("Current command is STATS");
//        /*String deckName = commandInput.replaceFirst("(?i)^(stats)?(\\s)*deck/", "");
//        if (deckName.strip().equals("")) {
//            Gui.renderStats(programState.getStatistics());
//        } else {
//            Gui.renderStats(programState.getDeck(deckName).getStatistics());
//        }*/
//
//        return true; // capture is valid, end checking other commands
//    }),
//
//    VIEW("(?i)^(view)?(\\s)*(deck/[\\S\\p{Punct}]+){1}?(\\s)*",
//            new ResponseGroup[] {}, (commandInput, programState) -> {
//        /*LogsCenter.getLogger(Responses.class).info("Current command is VIEW");
//        String deckName = commandInput.replaceFirst("(?i)^(view)?(\\s)*deck/", "");
//        //System.out.println(test.trim());
//        Deck d = programState.getDeck(deckName);
//        Gui.renderDeck(d);*/
//
//        return true; // capture is valid, end checking other commands
//    }),
//
//    CREATE("(?i)^(create)?(\\s)+(deck/[\\S]+){1}[\\s]*",
//            new ResponseGroup[] {}, (commandInput, programState) -> {
//        //System.out.println("Current command is CREATE_DECK");
//        LogsCenter.getLogger(Responses.class).info("Current command is CREATE_DECK");
//        /*
//        if (programState.getCurrentState() != StateEnum.DEFAULT) {
//            System.out.println("Create not allowed here");
//            return false;
//        }
//         */
//
//        /*String deckName = commandInput.split("deck/")[1].trim();
//        if (programState.hasDeck(deckName)) {
//            // REPORT DECK EXISTS
//            LogsCenter.getLogger(Responses.class).warning("CREATE_DECK: Deck with same name exist - " + deckName);
//            Gui.showError("Error: Deck with same name exists - " + deckName);
//            return true;
//            //System.out.println("Error: Deck with same name exist - " + deckName);
//        } else {
//            //programState.setCurrentState(StateEnum.CREATE_STATE_FRONT);
//            programState.addDeck(deckName);
//            LogsCenter.getLogger(Responses.class).info("CREATED_DECK: Deck added - " + deckName);
//            // PRINT INSTRUCTIONS TO USER HOW TO CREATE DECK
//        }
//
//        Gui.showStatus("Deck created - " + deckName);*/
//        LogsCenter.getLogger(Responses.class).info("CREATE_DECK: command execution successful");
//        return true;
//    }),
//
//    DECK_CREATE_MCQ_CARD("(?i)^(create)?(\\s)+"
//            + "(deck/[\\S]+){1}(\\s)*"
//            + "(front/[\\S\\s]+){1}(\\s)*"
//            + "(back/[\\S\\s]+){1}(\\s)*"
//            + "((choice/[\\S\\s]+)(\\s)*){1,}" ,
//            new ResponseGroup[] {},(commandInput, programState) -> {
//
//        /*System.out.println("Current command is DECK_CREATE_MCQ_CARD");
//        LogsCenter.getLogger(Responses.class).info("Current command is DECK_CREATE_MCQ_CARD");
//
//        CreateCommand command = new CreateCommand();
//        command.funcCall(commandInput, programState);
//
//        Gui.showStatus("MCQ Card created");*/
//        LogsCenter.getLogger(Responses.class).info("DECK_CREATE_MCQ_CARD: command execution successful");
//        return true; // capture is valid, end checking other commands
//    }),
//
//    // create frontbackcard
//    DECK_CREATE_REG_CARD("(?i)^(create)?(\\s)+"
//            + "(deck/[\\S\\s]+){1}(\\s)*"
//            + "(front/[\\S\\s]+){1}(\\s)*"
//            + "(back/[\\S\\s]+){1}(\\s)*",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//
//        /*System.out.println("Current command is DECK_CREATE_REG_CARD");
//        LogsCenter.getLogger(Responses.class).info("Current command is DECK_CREATE_REG_CARD");
//
//        CreateCommand command = new CreateCommand();
//        command.funcCall(commandInput, programState);
//
//        Gui.showStatus("Front Back card created.");
//        */
//        LogsCenter.getLogger(Responses.class).info("DECK_CREATE_REG_CARD: command execution successful");
//        return true; // capture is valid, end checking other commands
//    }),
//    TEST("(?i)^(test)?(\\s)+(duration/[\\w\\p{Punct}]+)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)*",
//            new ResponseGroup[] {},(
//            commandInput, programState) -> {
//        /*System.out.println("Current command is TEST");
//        String inputName = commandInput.split("deck/")[1];
//        try {
//            Deck retrievedDeck = programState.getDeck(inputName);
//            ExamRunner.createExam(retrievedDeck);
//            Exam exam = ExamRunner.getCurrentExam();
//            FlashCard currentCard = exam.getCurrentCard();
//            String question = currentCard.getFront();
//            programState.setCurrentState(StateEnum.TEST_ONGOING_WAITING_ANS);
//            LogsCenter.getLogger(Responses.class).info(question);
//        } catch (DeckNotFoundException e) {
//            e.printStackTrace();
//        }*/
//
//        // *Initiate test with Test Deck*
//        return true; // capture is valid, end checking other commands
//    }),
//    EXIT("(?i)^(exit)?",
//            new ResponseGroup[] {},(commandInput, programState) -> {
//        //System.out.println("Current command is EXIT");
//        LogsCenter.getLogger(Responses.class).info("Current command is EXIT");
//
//        // Exit from application
//        // Added exit for convenience
//        System.exit(0);
//
//        return true;
//    }),
//    EDIT_DECK_EDIT_CARD("(?i)^(edit)?(\\s)+(deck/[\\S}]+){1}(\\s)+(action/[edit]+){1}((\\s)+"
//            + "(index/[\\d]+){1}(\\s)*){1}((\\s)+(front/[\\S\\s]+){1}(\\s)*)?((\\s)?"
//            + "(back/[\\S\\s]+))?(\\s)?",
//            new ResponseGroup[] {},(
//            commandInput, programState) -> {
//        //System.out.println("Current command is EDIT, edit card in deck");
//        LogsCenter.getLogger(Responses.class).info("Current command is EDIT_DECK_EDIT_CARD, edit card in deck");
//        /*
//        try {
//            EditCommand command = new EditCommand();
//            command.funcCall(commandInput, programState);
//
//        } catch (DeckNotFoundException d) {
//
//            System.out.println("Error: " + d.getMessage());
//            LogsCenter.getLogger(Responses.class).info("EDIT_DECK_EDIT_CARD: Deck not found");
//            Gui.showStatus(d.getMessage());
//
//        } catch (NumberFormatException n) {
//            System.out.println("Error: " + n.getMessage());
//            LogsCenter.getLogger(Responses.class).info("EDIT_DECK_EDIT_CARD: Index not valid");
//            Gui.showStatus(n.getMessage());
//
//        } catch (IndexNotFoundException i) {
//            System.out.println("Error: " + i.getMessage());
//            LogsCenter.getLogger(Responses.class).info("EDIT_DECK_EDIT_CARD: Index not correct");
//            Gui.showStatus(i.getMessage());
//        }*/
//
//        return true;
//    }),
//    EDIT_DECK_REMOVE_CARD("(?i)^(edit)?(\\s)+(deck/[\\S}]+){1}(\\s)+(action/[remove]+){1}((\\s)+"
//            + "(index/[\\d]+){1}(\\s)*){1}((\\s)+(front/[\\S\\s]+){1}(\\s)*)?((\\s)*"
//            + "(back/[\\S\\s]+))?(\\s)*",
//            new ResponseGroup[] {},
//            (i, s) -> {
//        //System.out.println("Current command is EDIT, removing deck");
//        LogsCenter.getLogger(Responses.class).info("Current command is EDIT_DECK_REMOVE_CARD, removing deck");
//
//        /*
//        try {
//            EditCommand command = new EditCommand();
//            command.funcCall(commandInput, programState);
//        } catch (DeckNotFoundException d) {
//
//            System.out.println("Error: " + d.getMessage());
//            LogsCenter.getLogger(Responses.class).info("EDIT_DECK_REMOVE_CARD: Deck not found");
//            Gui.showStatus(d.getMessage());
//
//        } catch (NumberFormatException n) {
//            System.out.println("Error: " + n.getMessage());
//            LogsCenter.getLogger(Responses.class).info("EDIT_DECK_REMOVE_CARD: Index not valid");
//            Gui.showStatus(n.getMessage());
//
//        } catch (IndexNotFoundException i) {
//            System.out.println("Error: " + i.getMessage());
//            LogsCenter.getLogger(Responses.class).info("EDIT_DECK_REMOVE_CARD: Index not correct");
//            Gui.showStatus(i.getMessage());
//        }
//        */
//
//        LogsCenter.getLogger(Responses.class).info("EDIT_DECK_REMOVE_CARD: command execution successful");
//        return true;
//    }),
//    UNKNOWN(
//            ".*",
//            new ResponseGroup[]{ResponseGroup.MATCH_ALL},
//            (i, s) -> {
//        System.out.println("Sorry, I don't know what is this command.");
//        LogsCenter.getLogger(Responses.class).warning("Unknown command entered.");
//        //Gui.showError("Sorry, I don't know what is this command.");
//        return false;
//    });
//
//    private String regex;
//    private ResponseFunc func;
//    private ResponseGroup[] group;
//
//    Responses(String r, ResponseGroup[] grp, ResponseFunc f) {
//        regex = r;
//        func = f;
//        group = grp;
//    }
//
//    /**
//     * Given a string and program state, if string matches regex this enum will call its response
//     * function.
//     *
//     * @param i input string
//     * @param s state object
//     * @return boolean if the string has matched
//     */
//    public boolean call(String i, State s) {
//        if (i.matches(regex)) {
//            return func.funcCall(i, s);
//        }
//        return false;
//    }
//}
