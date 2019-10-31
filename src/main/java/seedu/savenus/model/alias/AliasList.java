package seedu.savenus.model.alias;

import java.util.List;

/**
 * Represents a list of AliasPairs.
 */
public class AliasList {
    private AliasList aliasList;

    private List<AliasPair> aliasPairList;

    /**
     * Constructs an empty AliasList.
     */
    public AliasList() {
        this.aliasPairList = new CommandWordAdder().addCommandWords();
    }

    /**
     * Sets the list of AliasPairs to a new list.
     * @param aliasPairList the new list of AliasPairs.
     */
    public void setAliasPairList(List<AliasPair> aliasPairList) {
        this.aliasPairList = aliasPairList;
    }

    /**
     * Gets the inner list of AliasPairs.
     * @return the inner list of AliasPairs.
     */
    public List<AliasPair> getList() {
        return this.aliasPairList;
    }

    /**
     * Gets the command word based on the alias word. If the alias word does not exist in the AliasList,
     * just return the alias word.
     * @param aliasWord the alias word supposedly inside the AliasList.
     * @return the command word. Otherwise the alias word if the alias word does not exist in the AliasList.
     */
    public String getCommandWord(String aliasWord) {
        for (int i = 0; i < aliasPairList.size(); i++) {
            AliasPair aliasPair = aliasPairList.get(i);
            if (aliasPair.getAliasWord().equals(aliasWord)) {
                return aliasPair.getCommandWord();
            }
        }

        return aliasWord;
    }

    /**
     * Checks whether the alias word already exists in the AliasList or not.
     * @param commandWord the command word.
     * @param keyword the alias word to be checked.
     * @return true if the alias word already exists. False if otherwise.
     */
    public boolean hasAliasWord(String commandWord, String keyword) {
        return AliasChecker.isThereDuplicateAliasWord(aliasPairList, commandWord, keyword);
    }

    /**
     * Checks if the keyword is a command word or not.
     * @param keyword the keyword to be checked.
     * @return true if the keyword is a command word. False if otherwise.
     */
    public boolean isCommandWord(String keyword) {
        return AliasChecker.isCommandWord(aliasPairList, keyword);
    }

    /**
     * Checks if the command word exists in the AliasList or not.
     * @param commandWord the command word to be checked.
     * @return true if the command word exists in the AliasList. False if otherwise.
     */
    public boolean doesCommandWordExist(String commandWord) {
        return AliasChecker.doesCommandWordExist(aliasPairList, commandWord);
    }

    /**
     * Changes the AliasPair's alias word based on the command word given.
     * @param comamndWord the command word.
     * @param keyword the alias word.
     */
    public void changeAliasWord(String comamndWord, String keyword) {
        for (int i = 0; i < aliasPairList.size(); i++) {
            AliasPair aliasPair = aliasPairList.get(i);
            if (aliasPair.getCommandWord().equals(comamndWord)) {
                aliasPair.setAliasWord(keyword);
                return;
            }
        }

        aliasPairList.add(new AliasPair(comamndWord, keyword));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (this instanceof AliasList) {
            AliasList other = (AliasList) object;
            return this.getList().equals(other.getList());
        } else {
            return false;
        }
    }
}
