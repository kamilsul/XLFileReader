package main.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/reader")
@MultipartConfig
public class Filereader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Part filepart = request.getPart("file");
		FileInputStream fileInputStream = new FileInputStream(
				"/home/kamil/telusko/workspace/XLFileReader/" + filepart.getSubmittedFileName());
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		PrintWriter writer = response.getWriter();
		while (rowIterator.hasNext()) {
			Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellTypeEnum()) {
				case NUMERIC:
					writer.print(cell.getNumericCellValue());
					break;
				case STRING:
					writer.print(cell.getStringCellValue());
					break;
				default:
					break;
				}

				writer.print("\t");
			}
			writer.println();
		}
		fileInputStream.close();
		workbook.close();

	}

}
