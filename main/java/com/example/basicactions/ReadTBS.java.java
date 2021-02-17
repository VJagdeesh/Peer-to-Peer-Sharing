package com.example.basicactions;
import com.example.daos.BookDao;
import com.example.objects.Book;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
@WebServlet(name = "read",urlPatterns = {"/read"})
public class ReadTBS extends HttpServlet {
private final Logger logger = Logger.getLogger(ReadTBS.class.getName());
@Override
public void doGet(HttpServletRequestreq,HttpServletResponseresp) throws ServletException, IOException {
    String id = req.getParameter("id");
TBScript dao = (TBScript) this.getServletContext().getAttribute("dao");
    Book book = dao.readBook(id);
logger.log(Level.INFO, "Read book with id {0}", id);
req.setAttribute("book", book);
req.setAttribute("page", "view");
req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
