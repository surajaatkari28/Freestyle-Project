package utillities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Excel_File_Reader {

    private String path;

    public Excel_File_Reader(String path) {
        this.path = path;
        ensureSampleExcel();
    }

    private void ensureSampleExcel() {
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                try (XSSFWorkbook wb = new XSSFWorkbook()) {
                    XSSFSheet sheet = wb.createSheet("Sheet1");
                    Row header = sheet.createRow(0);
                    header.createCell(0).setCellValue("username");
                    header.createCell(1).setCellValue("password");

                    Row r1 = sheet.createRow(1);
                    r1.createCell(0).setCellValue("standard_user");
                    r1.createCell(1).setCellValue("secret_sauce");

                    try (FileOutputStream fos = new FileOutputStream(path)) {
                        wb.write(fos);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[][] getTestData() {
        List<Object[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rows; i++) {
                Row r = sheet.getRow(i);
                if (r == null) continue;

                String user = r.getCell(0).getStringCellValue();
                String pass = r.getCell(1).getStringCellValue();
                data.add(new Object[]{user, pass});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] arr = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i);
        }
        return arr;
    }
}

