package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/choose")
public class Fileupload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIR = "C:/Users/dhana/git/XLFileReader";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		try {
			ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());

			List<FileItem> multifiles = sf.parseRequest(request);

			for (FileItem item : multifiles) {
			//String name= new File(item.getName()).getName();

				item.write(new File(UPLOAD_DIR + item.getName()));
			} //+ File.separator 

			System.out.println("Choose servlet called !! ");
			// Create Workbook object and pass the filename
			String fileName= "C:/Users/dhana/git/XLFileReaderdhananjay.xlsx";
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileName));
			// As every workbook has a sheets , so select 1 sheet
			XSSFSheet sheet = workbook.getSheetAt(0);
			// Select row from the workbook
			// XSSFRow row = sheet.getRow(0);
			// Select cell from each row
			// System.out.println(row.getCell(0).getNumericCellValue() +
			// row.getCell(1).getStringCellValue());

			FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
			for (Row row : sheet) {
				for (Cell cell : row) {

					switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t\t");
						break;

					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue()+ "\t\t");
						break;
					}
				}
					System.out.println();
			}	

			// Traditional way we pass the request object to UI -- display.jsp
		//	request.setAttribute("row", row);
			// get a rd object and forward the row object to display.jsp
			//RequestDispatcher rd = request.getRequestDispatcher("display.jsp");
			//rd.forward(request, response);
			// Use expression language in display.jsp to show the content of xls
			// file.

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
