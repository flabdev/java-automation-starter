package dataproviders.excel;


import exceptions.InvalidPathForExcelException;
import utilities.logger.Log;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class ExcelReader {
	public static String filename = System.getProperty("user.dir");
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	public static String sActionKeyword = null;

	/**
	 * 
	 * @param path
	 */
	public ExcelReader(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// returns the row count in a sheet
	/**
	 * To get the Row count and returns the same integer.
	 * 
	 * @param sheetName
	 * @return row count no.
	 */
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	// returns number of columns in a sheet
	/**
	 * Returns the No of Coulums in a sheet.
	 * 
	 * @param sheetName
	 * @return NoOfrows count.
	 */
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();
	}

	// returns the data from a cell
	/**
	 * To read a data from a cell based on column name and returns the same value.
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @return cellText - data value from a excel cell.
	 * @exception Exception
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			// System.out.println(cell.getCellType());
			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());

				return cellText;
			} else if (cell.getCellType() == CellType.BOOLEAN)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	// returns true if data is set successfully else false
	/**
	 * To write the data in a excel result sheet cell, using ColumnName. Return
	 * true/False.
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param data
	 * @return True/False
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// find whether sheets exists
	/**
	 * To Verify the sheet exists or not.
	 * 
	 * @param sheetName
	 * @return boolean - true/false.
	 */
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	/**
	 * Overloaded method. To read a data from a cell based column No. Returns the
	 * data from a cell
	 * 
	 * @param sheetName
	 * @param colNum
	 * @param rowNum
	 * @return cellText - the text value in the specific cell.
	 */
	// returns the data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			return cell.getStringCellValue();

		}

		catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xlsx";
		}
	}

	public int getRows() {
		try {
			return sheet.getLastRowNum();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	public int getColumns() {
		try {
			row = sheet.getRow(0);
			return row.getLastCellNum();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	public Object[][] getDataHashTable(String sheetName, int startRow, int endRow) {
		Log.info("Excel File: " + path);
		Log.info("Selected Sheet: " + sheetName);

		Object[][] data = null;

		try {

			File f = new File(path);

			if (!f.exists()) {
				try {
					Log.info("File Excel path not found.");
					throw new InvalidPathForExcelException("File Excel path not found.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);

			int rows = getRows();
			int columns = getColumns();

			Log.info("Row: " + rows + " - Column: " + columns);
			Log.info("StartRow: " + startRow + " - EndRow: " + endRow);

			data = new Object[(endRow - startRow) + 1][1];
			Hashtable<String, String> table = null;
			for (int rowNums = startRow; rowNums <= endRow; rowNums++) {
				table = new Hashtable<>();
				for (int colNum = 0; colNum < columns; colNum++) {
					table.put(getCellData(sheetName, colNum, 0), getCellData(sheetName, colNum, rowNums));
				}
				data[rowNums - startRow][0] = table;
			}

		} catch (IOException e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}

		return data;

	}

}
