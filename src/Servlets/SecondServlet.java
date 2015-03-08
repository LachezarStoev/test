package Servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class SecondServlet
 */
@WebServlet(value = "/")
@MultipartConfig(
	fileSizeThreshold = 5_242_880, //5MB
	maxFileSize = 20_971_520L, //20MB
	maxRequestSize = 41_943_040L //40MB
)
public class SecondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SecondServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		System.out.println("THIRD SERVLET!");

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String html = "<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "<title>File Upload</title>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "</head>"
				+ "<body>"
				+ "<form method=\"POST\" action=\"SecondServlet\" enctype=\"multipart/form-data\" >"
				+ "<input type=\"file\" name=\"file\" id=\"file\" /> <br/>"
				+ "</br>"
				+ "<input type=\"submit\" value=\"Upload\" name=\"upload\" id=\"upload\" />"
				+ "</form>" + "</body>" + "</html>";
		response.getWriter().write(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Part part = request.getPart("file");
		OutputStream out = new FileOutputStream(new File("/tmp/"
				+ getFileName(part)));

		InputStream is = part.getInputStream();

		int read = 0;
		final byte[] bytes = new byte[1024];

		while ((read = is.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		
		response.getWriter().write("OBICHAM TE, PIL!");
	}
	
	private String getFileName(final Part part) {
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
}
