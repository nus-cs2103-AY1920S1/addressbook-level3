package seedu.savenus.model.alias;

import java.util.List;

/**
 * Represents a Simple Checker to check the validity of the command and alias words.
 */
public class AliasChecker {

    /**
     * Checks whether the alias word already exists in the AliasList or not.
     * @param aliasPairList  the list of AliasPairs.
     * @param commandWord the command word.
     * @param aliasWord the alias word to be checked.
     * @return true if the alias word already exists. False if otherwise.
     */
    public static boolean isThereDuplicateAliasWord(List<AliasPair> aliasPairList,
                                                    String commandWord, String aliasWord) {
        if (aliasWord.equals("")) {
            return false;
        }

        for (int i = 0; i < aliasPairList.size(); i++) {
            AliasPair aliasPair = aliasPairList.get(i);
            if (aliasPair.getAliasWord().equals(aliasWord)) {
                return !aliasPair.getCommandWord().equals(commandWord);
            }
        }

        return false;
    }

    /**
     * Checks if the keyword is a command word or not.
     * @param aliasPairList  the list of AliasPairs.
     * @param keyword the keyword to be checked.
     * @return true if the keyword is a command word. False if otherwise.
     */
    public static boolean isCommandWord(List<AliasPair> aliasPairList, String keyword) {
        for (int i = 0; i < aliasPairList.size(); i++) {
            AliasPair aliasPair = aliasPairList.get(i);
            if (aliasPair.getCommandWord().equals(keyword)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the command word exists in the AliasList or not.
     * @param aliasPairList  the list of AliasPairs.
     * @param commandWord the command word to be checked.
     * @return true if the command word exists in the AliasList. False if otherwise.
     */
    public static boolean doesCommandWordExist(List<AliasPair> aliasPairList, String commandWord) {
        for (int i = 0; i < aliasPairList.size(); i++) {
            AliasPair aliasPair = aliasPairList.get(i);
            if (aliasPair.getCommandWord().equals(commandWord)) {
                return true;
            }
        }

        return false;
    }

}
