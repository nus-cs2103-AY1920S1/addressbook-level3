package seedu.address.commons.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import seedu.address.model.ReadOnlyProjectList;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Spending;
import seedu.address.model.project.Project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ExcelUtil {

    public static void writeBudgetsToFile(Path file, ReadOnlyProjectList projectList) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        FileOutputStream out = new FileOutputStream(file.toString());

        for (Project project : projectList.getProjectList()) {
            List<Budget> budgets = project.getFinance().getBudgets();
            HSSFSheet sheet = wb.createSheet(project.getTitle().title);

            createHeaders(wb, sheet, budgets);
            fillInnerParts(wb, sheet, budgets);
            for (int i = 0; i < budgets.size() * 3; i++) {
                sheet.autoSizeColumn(i, true);
            }
        }
        wb.write(out);
        out.close();
    }

    public static void createHeaders(HSSFWorkbook wb, HSSFSheet sheet, List<Budget> budgets) {
        // create 3 cell styles
        HSSFCellStyle title = wb.createCellStyle();
        HSSFCellStyle upperBorder = wb.createCellStyle();

        // create 2 fonts objects
        HSSFFont titleFont = wb.createFont();
        HSSFFont normalFont = wb.createFont();

        //set font 1 to 12 point type
        titleFont.setFontHeightInPoints((short) 14);
        // make it bold
        //arial is the default font
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //set cell style
        title.setFont(titleFont);
        //set a thin border
        title.setBorderBottom(title.BORDER_THIN);
        //set the cell format to text see DataFormat for a full list
        title.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
        //align center
        title.setAlignment(CellStyle.ALIGN_CENTER);
        title.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //create upper border
        upperBorder.setBorderTop(upperBorder.BORDER_THIN);

        HSSFRow budgetName = sheet.createRow(0);
        HSSFRow expenseHeader = sheet.createRow(1);
        budgetName.setHeight((short) 0x249);
        int counter = 0;

        for (Budget budget : budgets) {
            // create budget header
            HSSFCell cell = budgetName.createCell(counter);
            cell.setCellStyle(title);
            cell.setCellValue(budget.getName() + String.format(" ($%s)", budget.getMoney().toString()));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, counter, counter + 2));

            // create spending header
            HSSFCell expense = expenseHeader.createCell(counter);
            expense.setCellStyle(upperBorder);
            expense.setCellValue("Expense");
            HSSFCell time = expenseHeader.createCell(counter + 1);
            time.setCellStyle(upperBorder);
            time.setCellValue("Date");
            HSSFCell amount = expenseHeader.createCell(counter + 2);
            amount.setCellStyle(upperBorder);
            amount.setCellValue("Amount");
            counter += 3;
        }
    }

    public static void fillInnerParts(HSSFWorkbook wb, HSSFSheet sheet, List<Budget> budgets) {
        int max = 0;
        for (Budget budget : budgets) {
            if (budget.getSpendings().size() > max) {
                max = budget.getSpendings().size();
            }
        }
        int counter = 0;
        for (int i = 0; i < max + 1; i++) {
            HSSFRow row = sheet.createRow(i + 2);
            for (Budget budget : budgets) {
                if (budget.getSpendings().size() >= i + 1) {
                    Spending spending = budget.getSpendings().get(i);
                    // create name of expense
                    HSSFCell expense = row.createCell(counter);
                    expense.setCellValue(spending.getDescription());

                    //create date of expense
                    HSSFCell time = row.createCell(counter + 1);
                    time.setCellValue(spending.getTime().toString());

                    //create amount of expense
                    HSSFCell amount = row.createCell(counter + 2);
                    amount.setCellValue(spending.getMoney().getAmount().doubleValue());
                    counter += 3;
                } else if (budget.getSpendings().size() == i) {
                    HSSFCell cell = row.createCell(counter);
                    cell.setCellValue(String.format("Total remaining: ($%s)", budget.getRemainingMoney().toString()));
                    HSSFCellStyle alignRight = wb.createCellStyle();
                    alignRight.setAlignment(CellStyle.ALIGN_RIGHT);
                    cell.setCellStyle(alignRight);
                    sheet.addMergedRegion(new CellRangeAddress(i + 2, i + 2, counter, counter + 2));
                    counter += 3;
                } else {
                    counter += 3;
                }
            }
            counter = 0;
        }
    }
}

