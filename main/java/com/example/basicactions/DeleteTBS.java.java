package com.example.basicactions;
import com.example.daos.BookDao;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(name = "delete",urlPatterns = {"/delete"})
public class DeleteTBS extends HttpServlet {
  @Override
public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String id1 = req.getParameter("id");
TBScript dao = (TBScript) this.getServletContext().getAttribute("dao");    
dao.deleteBook(id1);
resp.sendRedirect("/books");
  }
}
