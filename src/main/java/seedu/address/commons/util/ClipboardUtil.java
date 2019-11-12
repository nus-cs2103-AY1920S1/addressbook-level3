package seedu.address.commons.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * A class for handling user clipboard.
 */
public class ClipboardUtil {
    /**
     * Copies generated password to user clipboard
     * @param textToCopy the password to copy
     * @param user the user local
     */
    public static void copyToClipboard(String textToCopy, ClipboardOwner user) {
        //Create & get the clipboard from the computer
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //Make the text selected
        Transferable selectedText = new StringSelection(textToCopy);

        //Copy & Write the selected text to the user's clipboard
        clipboard.setContents(selectedText, user);
    }
}
