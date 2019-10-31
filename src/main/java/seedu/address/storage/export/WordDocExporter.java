package seedu.address.storage.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Allows exporting files generated from nJoyAssistant into a word document.
 */
public class WordDocExporter extends Exporter {

    /**
     * Creates a word document with the specified file name and content provided.
     */
    public static void saveExport(String fileName, String toExport) {
        try {
            File tempDir = new File(EXPORT_DIRECTORY_PATH);
            if (!tempDir.exists()) {
                new File(EXPORT_DIRECTORY_PATH).mkdir();
            }
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(new File(EXPORT_DIRECTORY_PATH + fileName + ".docx"));
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            if (toExport.contains("\n")) {
                String[] lines = toExport.split("\n");
                run.setText(lines[0], 0); // set first line into XWPFRun
                for (int i = 1; i < lines.length; i++) {
                    // add break and insert new text
                    run.addBreak();
                    run.setText(lines[i]);
                }
            } else {
                run.setText(toExport, 0);
            }
            document.write(out);
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
