package seedu.address.storage.export;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Allows exporting files generated from nJoyAssistant into a word document.
 */
public class ExportWordDoc implements Export {
    private String toExport;
    private String filename;

    /**
     * Sets the filename and the content of the exported word document.
     *
     * @param filename Name of file.
     * @param toExport Content of file.
     */
    public ExportWordDoc(String filename, String toExport) {
        this.toExport = toExport;
        this.filename = filename;
    }

    /**
     * Creates a word document with the specified file name and content provided.
     */
    public void saveExport() {
        try {
            File tempDir = new File(EXPORT_DIRECTORY_PATH);
            if (!tempDir.exists()) {
                new File(EXPORT_DIRECTORY_PATH).mkdir();
            }
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(new File(EXPORT_DIRECTORY_PATH + filename + ".docx"));
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
