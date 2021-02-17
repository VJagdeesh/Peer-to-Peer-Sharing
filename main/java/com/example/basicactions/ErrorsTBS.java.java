package com.example.basicactions;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
@WebServlet(name = "errors",urlPatterns = {"/errors"})
public class ErrorTBS extends HttpServlet {
  @Override
public void doGet(HttpServletRequestreq, HttpServletResponseresp) throws ServletException {
throw new ServletException("Expected exception.");
  }
}
