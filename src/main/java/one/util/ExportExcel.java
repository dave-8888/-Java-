package one.util;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;

public class ExportExcel {
    private JTable table;
    private FileOutputStream fos;
    JFileChooser fileChooser = new JFileChooser();

    //传参数 Jtable的一个对象实例 另一个是保存的文件路径
    public ExportExcel(JTable table,String fileName) {
        this.table = table;
        fileChooser.setSelectedFile(new File(fileName));
        fileChooser.showSaveDialog(null);
        String filePath = fileChooser.getSelectedFile().toString();
        File file = new File(filePath);
        try {
            this.fos = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void export() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet hs = wb.createSheet();

        TableModel tm = table.getModel();
        int row = tm.getRowCount();
        int cloumn = tm.getColumnCount();
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setFillForegroundColor(HSSFColor.ORANGE.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 15);
        font1.setBoldweight((short) 700);
        style1.setFont(font);

        for (int i = 0; i < row + 1; i++) {
            HSSFRow hr = hs.createRow(i);
            for (int j = 0; j < cloumn; j++) {
                if (i == 0) {
                    String value = tm.getColumnName(j);
                    int len = value.length();
                    hs.setColumnWidth((short) j, (short) (len * 400));
                    HSSFRichTextString srts = new HSSFRichTextString(value);
                    HSSFCell hc = hr.createCell((short) j);
                    hc.setCellStyle(style1);
                    hc.setCellValue(srts);
                } else {
                    if (tm.getValueAt(i - 1, j) != null) {
                        String value = tm.getValueAt(i - 1, j).toString();
                        HSSFRichTextString srts = new HSSFRichTextString(value);
                        HSSFCell hc = hr.createCell((short) j);
                        hc.setCellStyle(style);

                        if (value.equals("") || value == null) {
                            hc.setCellValue(new HSSFRichTextString(""));
                        } else {
                            hc.setCellValue(srts);

                        }
                    }
                }
            }
        }

        try {
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

