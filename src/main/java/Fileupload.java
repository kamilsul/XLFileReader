package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/choose")
public class Fileupload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		try {
			ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());

			List<FileItem> multifiles = sf.parseRequest(request);

			for (FileItem item : multifiles) {

				item.write(new File("C:/Users/dhana/git/XLFileReader" + item.getName()));
			}

			System.out.println("Choose servlet called !! ");
			//Create Workbook object and pass the filename
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("C:/Users/dhana/git/XLFileReaderdhan.xlsx"));
			//As every workbook has a sheets , so select 1 sheet 
			XSSFSheet sheet = workbook.getSheetAt(0);
			// Once we have the sheet select row 1 to access the data 
			XSSFRow row = sheet.getRow(0);
			// Once we have the row object we have the cell 0,1,2, and display the same on screen UI
			System.out.println(row.getCell(0).getNumericCellValue() + row.getCell(1).getStringCellValue());
			
			// Traditional way we pass the request object to UI -- display.jsp
			request.setAttribute("row", row);
			RequestDispatcher rd = request.getRequestDispatcher("display.jsp");
			rd.forward(request, response);
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
