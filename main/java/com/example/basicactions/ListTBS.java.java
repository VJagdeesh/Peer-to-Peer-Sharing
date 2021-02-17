package com.example.basicactions;
import com.example.daos.BookDao;
import com.example.objects.Book;
import com.example.objects.Result;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
@WebServlet(name = "list",urlPatterns = {"", "/books"},loadOnStartup = 1)
public class ListTBS extends HttpServlet {
private static final Logger logger = Logger.getLogger(ListTBS.class.getName());
  @Override
public void doGet(HttpServletRequestreq, HttpServletResponseresp) throws IOException, ServletException {
TBScript dao = (TBScript) this.getServletContext().getAttribute("dao");
    String startcursor = req.getParameter("cursor");
    List<Book> books = null;
    String endCursor = null;
try {
      Result<Book> result = dao.listBooks(startcursor);
logger.log(Level.INFO, "Retrieved list of all books");
books = result.getResult();
endCursor = result.getCursor();
    } catch (Exception e) {
throw new ServletException("Error listing books", e);
    }
req.getSession().getServletContext().setAttribute("books", books);
StringBuilderbookNames = new StringBuilder();
for (Book book : books) {
bookNames.append(book.getTitle()).append(" ");
    }
logger.log(Level.INFO, "Loaded books: " + bookNames.toString());
req.setAttribute("cursor", endCursor);
req.setAttribute("page", "list");
req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
