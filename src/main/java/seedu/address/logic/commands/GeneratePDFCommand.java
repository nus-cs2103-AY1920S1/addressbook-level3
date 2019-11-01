package seedu.address.logic.commands;

/**
 * returns pdf of inventories.
 */
/*public class GeneratePDFCommand extends Command {
    public static final String COMMAND_WORD = "pdf";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_SUCCESS = "Generated PDF";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        createDoc(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static void createDoc(Model model) {
        try {
            //Creating Doc
            Rectangle layout = new Rectangle(PageSize.A4);
            layout.setBackgroundColor(new BaseColor(51, 255, 189));
            Document document = new Document(layout);
            //Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            PdfWriter.getInstance(document, new FileOutputStream("Result1.pdf"));

            //Editing Doc
            document.open();
            designDoc(model,document);
            document.close();

            //Opening Doc
            File myFile = new File("C:\\Users\\ArunKumarr\\Documents\\main\\Result1.pdf");
            Desktop.getDesktop().open(myFile);

        } catch (Exception e) {
            Logger logger = LogsCenter.getLogger(GeneratePDFCommand.class);
            logger.info("Document not created");
        }
    }

    private static void designDoc(Model model, Document document) throws DocumentException {
        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);

        Font font30pt = new Font(FontFamily.HELVETICA, 30);
        font30pt.setColor(BaseColor.WHITE);
        Paragraph p1 = new Paragraph("Inventories", font30pt);

        PdfPCell cell = new PdfPCell(p1);
        cell.setFixedHeight(50);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(255, 87, 51));
        cell.setBorderWidth(0);
        header.addCell(cell);
        header.setSpacingAfter(10);

        PdfPTable table = tableCreator(model);

        document.add(header);
        document.add(table);
    }

    private static PdfPTable tableCreator(Model model) {
        ObservableList<Inventory>list = model.getFilteredInventoriesList();
        int listSize = list.size();
        double totalPrice = totalPrice(list);

        Font font10pt = new Font(FontFamily.HELVETICA, 14);

        PdfPTable table = new PdfPTable(new float[]{1,5,5});
        Paragraph p1 = new Paragraph("no.", font10pt);
        Paragraph p2 = new Paragraph("Inventory", font10pt);
        Paragraph p3 = new Paragraph("Price($)", font10pt);
        table.addCell(p1);
        table.addCell(p2);
        table.addCell(p3);
        for(int i=1; i<=listSize; i++) {
            PdfPCell cell1 = new PdfPCell(Phrase.getInstance(Integer.toString(i)));
            cell1.setBorderWidthLeft(0);
            cell1.setBorderWidthRight(0);
            table.addCell(cell1);
            //table.addCell(Integer.toString(i));
            PdfPCell cell2 = new PdfPCell(Phrase.getInstance(list.get(i-1).getName().toString()));
            cell2.setBorderWidthLeft(0);
            cell2.setBorderWidthRight(0);
            table.addCell(cell2);
            //table.addCell(list.get(i-1).getName().toString());
            PdfPCell cell3 = new PdfPCell(Phrase.getInstance(list.get(i-1).getPrice().toString()));
            cell3.setBorderWidthLeft(0);
            cell3.setBorderWidthRight(0);
            table.addCell(cell3);
            //table.addCell(list.get(i-1).getPrice().toString());
        }
        PdfPCell cell = new PdfPCell(new Phrase("Total"));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        //cell.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(cell);
        table.addCell(Double.toString(totalPrice));
        /*PdfPCell cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        table.addCell("Cell 1.1");
        cell = new PdfPCell();
        cell.addElement(new Phrase("Cell 1.2"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell 2.1"));
        cell.setPadding(5);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setPadding(5);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        Paragraph p = new Paragraph("Cell 2.2");
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        table.addCell(cell);*/
        /*return table;
    }

    private static double totalPrice(ObservableList<Inventory>list) {
        double totalPrice = 0;
        for(Inventory inv: list) {
            totalPrice += inv.getPrice().getPrice();
        }
        return totalPrice;
    }
}*/
