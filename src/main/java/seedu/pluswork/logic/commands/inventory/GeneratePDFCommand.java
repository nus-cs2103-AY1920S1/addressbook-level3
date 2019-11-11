package seedu.pluswork.logic.commands.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_PDFTYPE;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.InvTasMapping;

/**
 * returns pdf of inventories.
 */
public class GeneratePDFCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(GeneratePDFCommand.class);

    public static final String COMMAND_WORD = "pdf";
    public static final String PREFIX_USAGE = PREFIX_INVENTORY_PDFTYPE.getPrefix();

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a inventory to the project Dashboard. "
            + "Parameters: "
            + PREFIX_INVENTORY_PDFTYPE + "TYPE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INVENTORY_PDFTYPE + "members";

    public static final String MESSAGE_SUCCESS = "Generated PDF";

    private final String type;

    /**
     * Creates an GeneratePDFCommand to create PDF
     */
    public GeneratePDFCommand(String type) throws ParseException {
        this.type = type;
        if (!(type.equals("members") || type.equals("tasks"))) {
            throw new ParseException("Invalid Command");
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        createDoc(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public void createDoc(Model model) {
        try {
            //Creating Doc
            Rectangle layout = new Rectangle(PageSize.A4);
            layout.setBackgroundColor(new BaseColor(51, 255, 189));
            Document document = new Document(layout);
            document.setMargins(0, 0, 0, 0);
            PdfWriter.getInstance(document, new FileOutputStream("Result1.pdf"));

            logger.info("Document created");

            //Editing Doc
            document.open();
            designDoc(model, document);
            logger.info("Document edited");
            document.close();


            //Opening Doc
            File myFile = new File("Result1.pdf");
            //File jarFile = new File("build\\jar\\Result1.pdf");
            //if(myFile.exists()) {
            Desktop.getDesktop().open(myFile);
            //} else {
            //    Desktop.getDesktop().open(jarFile);
            //}

            logger.info("document opened");

        } catch (Exception e) {
            logger.info("Document not created: " + e.getLocalizedMessage());
        }
    }

    private void designDoc(Model model, Document document) throws DocumentException {
        PdfPTable header = headerCreator(model);
        document.add(header);

        ObservableList<ObservableList<InvMemMapping>> mapListMem = ((ModelManager) model).getInvMemPDFList();
        ObservableList<ObservableList<InvTasMapping>> mapListTas = ((ModelManager) model).getInvTasPDFList();

        ArrayList<Integer> lonelyMemList = ((ModelManager) model).getInvMemLonelyList();
        ArrayList<Integer> lonelyTasList = ((ModelManager) model).getInvTasLonelyList();

        if (type.equals("members")) {
            for (ObservableList<InvMemMapping> x : mapListMem) {
                if (!x.isEmpty()) {
                    PdfPTable table = tableCreatorMem(model, x);
                    document.add(table);
                }
            }
            if (!lonelyMemList.isEmpty()) {
                PdfPTable lonelyTable = lonelyTableCreator(model, lonelyMemList);
                document.add(lonelyTable);
            }
        } else if (type.equals("tasks")) {
            for (ObservableList<InvTasMapping> x : mapListTas) {
                if (!x.isEmpty()) {
                    PdfPTable table = tableCreatorTas(model, x);
                    document.add(table);
                }
            }
            if (!lonelyTasList.isEmpty()) {
                PdfPTable lonelyTable = lonelyTableCreator(model, lonelyTasList);
                document.add(lonelyTable);
            }
            logger.info("Size of lonely InvTask List: " + lonelyTasList.size());
        }

        PdfPTable footer = footerCreator(model);
        document.add(footer);
    }

    private PdfPTable headerCreator(Model model) {
        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);

        Font font30pt = new Font(FontFamily.HELVETICA, 30);
        font30pt.setColor(BaseColor.WHITE);
        Paragraph p1 = new Paragraph("Inventories (by " + type + ")", font30pt);

        PdfPCell cell = new PdfPCell(p1);
        cell.setFixedHeight(50);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(255, 87, 51));
        cell.setBorderWidth(0);
        header.addCell(cell);
        header.setSpacingAfter(10);

        return header;
    }

    private PdfPTable tableCreatorMem(Model model, ObservableList<InvMemMapping> x) {
        //Retrieving details required
        ObservableList<Inventory> rawList = model.getFilteredInventoriesList();
        ObservableList<Inventory> list = FXCollections.observableArrayList();
        for (InvMemMapping y : x) {
            list.add(rawList.get(y.getInventoryIndex()));
        }
        String MemberName = model.getFilteredMembersList().get(x.get(0).getMemberIndex()).getName().toString();
        String nameToPrint = MemberName.concat("'s inv");
        int listSize = list.size();
        double totalPrice = totalPrice(list);

        //Defining parameters
        Font font10pt = new Font(FontFamily.HELVETICA, 12);
        PdfPTable table = new PdfPTable(new float[]{1, 5, 5});

        //Creating the table
        //First line
        PdfPCell cellS = new PdfPCell(new Phrase("no.", font10pt));
        cellS.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS);

        PdfPCell cellS2 = new PdfPCell(new Phrase(nameToPrint, font10pt));
        cellS2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS2.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS2);

        PdfPCell cellS3 = new PdfPCell(new Phrase("Price($)", font10pt));
        cellS3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS3.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS3);

        //Content of table
        try {
            for (int i = 1; i <= listSize; i++) {
                //Cell 1
                PdfPCell cell1 = new PdfPCell(Phrase.getInstance(Integer.toString(i)));
                cell1.setBorderWidthLeft(0);
                cell1.setBorderWidthRight(0);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell1.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell1);
                //Cell 2
                PdfPCell cell2 = new PdfPCell(Phrase.getInstance(list.get(i - 1).getName().toString()));
                cell2.setBorderWidthLeft(0);
                cell2.setBorderWidthRight(0);
                cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell2.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell2);
                //Cell 3
                PdfPCell cell3 = new PdfPCell(Phrase.getInstance(list.get(i - 1).getPrice().toString()));
                cell3.setBorderWidthLeft(0);
                cell3.setBorderWidthRight(0);
                cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell3.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell3);
                //table.addCell(list.get(i-1).getPrice().toString());
            }
        } catch (Exception e) {
            logger.info("during creation of table content: " + e.getLocalizedMessage());
        }

        //End of table
        PdfPCell cell = new PdfPCell(new Phrase("Total"));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorderColor(new BaseColor(51, 255, 189));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        String totalValue = String.format("%.2f", totalPrice);
        PdfPCell cellTotal = new PdfPCell(new Phrase(totalValue));
        cellTotal.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellTotal.setBorderColor(new BaseColor(51, 255, 189));
        cellTotal.setBackgroundColor(new BaseColor(160, 160, 160));
        table.addCell(cellTotal);

        table.setSpacingAfter(10);
        return table;
    }

    private PdfPTable tableCreatorTas(Model model, ObservableList<InvTasMapping> x) {
        //Retrieving details required
        ObservableList<Inventory> rawList = model.getFilteredInventoriesList();
        ObservableList<Inventory> list = FXCollections.observableArrayList();
        for (InvTasMapping y : x) {
            list.add(rawList.get(y.getInventoryIndex()));
        }
        String TaskName = model.getFilteredTasksList().get(x.get(0).getTaskIndex()).getName().toString();
        int listSize = list.size();
        double totalPrice = totalPrice(list);

        //Defining parameters
        Font font10pt = new Font(FontFamily.HELVETICA, 12);
        PdfPTable table = new PdfPTable(new float[]{1, 5, 5});

        //Creating the table
        //First line
        PdfPCell cellS = new PdfPCell(new Phrase("no.", font10pt));
        cellS.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS);

        PdfPCell cellS2 = new PdfPCell(new Phrase(TaskName, font10pt));
        cellS2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS2.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS2);

        PdfPCell cellS3 = new PdfPCell(new Phrase("Price($)", font10pt));
        cellS3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS3.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS3);

        //Content of table
        try {
            for (int i = 1; i <= listSize; i++) {
                //Cell 1
                PdfPCell cell1 = new PdfPCell(Phrase.getInstance(Integer.toString(i)));
                cell1.setBorderWidthLeft(0);
                cell1.setBorderWidthRight(0);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell1.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell1);
                //Cell 2
                PdfPCell cell2 = new PdfPCell(Phrase.getInstance(list.get(i - 1).getName().toString()));
                cell2.setBorderWidthLeft(0);
                cell2.setBorderWidthRight(0);
                cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell2.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell2);
                //Cell 3
                PdfPCell cell3 = new PdfPCell(Phrase.getInstance(list.get(i - 1).getPrice().toString()));
                cell3.setBorderWidthLeft(0);
                cell3.setBorderWidthRight(0);
                cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell3.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell3);
                //table.addCell(list.get(i-1).getPrice().toString());
            }
        } catch (Exception e) {
            logger.info("during creation of table content: " + e.getLocalizedMessage());
        }

        //End of table
        PdfPCell cell = new PdfPCell(new Phrase("Total"));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorderColor(new BaseColor(51, 255, 189));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        String totalValue = String.format("%.2f", totalPrice);
        PdfPCell cellTotal = new PdfPCell(new Phrase(totalValue));
        cellTotal.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellTotal.setBorderColor(new BaseColor(51, 255, 189));
        cellTotal.setBackgroundColor(new BaseColor(160, 160, 160));
        table.addCell(cellTotal);

        table.setSpacingAfter(10);
        return table;
    }

    private PdfPTable lonelyTableCreator(Model model, ArrayList<Integer> x) {
        ObservableList<Inventory> list = FXCollections.observableArrayList();
        for (int i : x) {
            list.add(model.getFilteredInventoriesList().get(i));
        }

        int listSize = list.size();
        double totalPrice = totalPrice(list);

        //Defining parameters
        Font font10pt = new Font(FontFamily.HELVETICA, 12);
        PdfPTable table = new PdfPTable(new float[]{1, 5, 5});

        //Creating the table
        //First line
        PdfPCell cellS = new PdfPCell(new Phrase("no.", font10pt));
        cellS.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS);

        PdfPCell cellS2 = new PdfPCell(new Phrase("Lonely Inventories", font10pt));
        cellS2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS2.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS2);

        PdfPCell cellS3 = new PdfPCell(new Phrase("Price($)", font10pt));
        cellS3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellS3.setBorderColor(new BaseColor(51, 255, 189));
        table.addCell(cellS3);

        //Content of table
        try {
            for (int i = 1; i <= listSize; i++) {
                //Cell 1
                PdfPCell cell1 = new PdfPCell(Phrase.getInstance(Integer.toString(i)));
                cell1.setBorderWidthLeft(0);
                cell1.setBorderWidthRight(0);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell1.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell1);
                //Cell 2
                PdfPCell cell2 = new PdfPCell(Phrase.getInstance(list.get(i - 1).getName().toString()));
                cell2.setBorderWidthLeft(0);
                cell2.setBorderWidthRight(0);
                cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell2.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell2);
                //Cell 3
                PdfPCell cell3 = new PdfPCell(Phrase.getInstance(list.get(i - 1).getPrice().toString()));
                cell3.setBorderWidthLeft(0);
                cell3.setBorderWidthRight(0);
                cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell3.setBorderColor(new BaseColor(51, 255, 189));
                table.addCell(cell3);
                //table.addCell(list.get(i-1).getPrice().toString());
            }
        } catch (Exception e) {
            logger.info("during creation of table content: " + e.getLocalizedMessage());
        }

        //End of table
        PdfPCell cell = new PdfPCell(new Phrase("Total"));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorderColor(new BaseColor(51, 255, 189));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        String totalValue = String.format("%.2f", totalPrice);
        PdfPCell cellTotal = new PdfPCell(new Phrase(totalValue));
        cellTotal.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellTotal.setBorderColor(new BaseColor(51, 255, 189));
        cellTotal.setBackgroundColor(new BaseColor(160, 160, 160));
        table.addCell(cellTotal);

        table.setSpacingAfter(10);
        return table;
    }

    private PdfPTable footerCreator(Model model) {
        PdfPTable footer = new PdfPTable(new float[]{6, 5});
        ObservableList<Inventory> list = model.getFilteredInventoriesList();
        double totalPrice = totalPrice(list);

        PdfPCell cell = new PdfPCell(new Phrase("Total Price:"));
        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorderColor(new BaseColor(51, 255, 189));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        footer.addCell(cell);

        String totalValue = String.format("%.2f", totalPrice);
        PdfPCell cellTotal = new PdfPCell(new Phrase(totalValue));
        cellTotal.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellTotal.setBorderColor(new BaseColor(51, 255, 189));
        cellTotal.setBackgroundColor(new BaseColor(160, 160, 160));
        footer.addCell(cellTotal);

        return footer;

    }

    private double totalPrice(ObservableList<Inventory> list) {
        double totalPrice = 0;
        for (Inventory inv : list) {
            totalPrice += inv.getPrice().getPrice();
        }
        return totalPrice;
    }
}
