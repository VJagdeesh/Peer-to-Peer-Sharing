package com.example.basicactions;
import com.example.daos.BookDao;
import com.example.objects.Book;
import com.example.util.CloudStorageHelper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
@WebServlet(name = "create",urlPatterns = {"/create"})
public class CreateTBS extends HttpServlet
{
Private static final Logger logger = Logger.getLogger(CreateTBS.class.getName());
@Override
public void doGet(HttpServletRequestreq,HttpServletResponseresp) throws ServletException, IOException {
req.setAttribute("action", "Add");
req.setAttribute("destination", "create");
req.setAttribute("page", "form");
req.getRequestDispatcher("/base.jsp").forward(req, resp);
}
@Override
public void doPost(HttpServletRequestreq,HttpServletResponseresp) throws ServletException, IOException {
assertServletFileUpload.isMultipartContent(req);
CloudHstorageHelper=(CloudH) getServletContext().getAttribute("storageHelper");
String newImageUrl = null;
Map<String,String> parameter = new HashMap<String, String>();
try {
FileItemIteratoriter = new ServletFileUpload().getItemIterator(req);


while (iter.hasNext()) {
FileItemStream item = iter.next();
if (item.isFormField()) {
parameter.put(item.getFieldName(), Streams.asString(item.openStream()));
} else if (!Strings.isNullOrEmpty(item.getName())) {
newImageUrl =storageHelper.uploadFile(item, System.getenv("BOOKSHELF_BUCKET"));
}
}
} catch (FileUploadException e) {
throw new IOException(e);
}
String createdByString = "";
String createdByIdString = "";
HttpSession session = req.getSession();
if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
createdByString = (String) session.getAttribute("userEmail");
createdByIdString = (String) session.getAttribute("userId");
}
Book book =
newBook.Builder()
.author(parameter.get("author"))
.description(parameter.get("description"))
.publishedDate(parameter.get("publishedDate"))
.title(parameter.get("title"))
.imageUrl(null == newImageUrl ? parameter.get("imageUrl") : newImageUrl)
.createdBy(createdByString)
.createdById(createdByIdString)
.build();
TBScriptdao = (TBScript) this.getServletContext().getAttribute("dao");
String id = dao.createBook(book);
logger.log(Level.INFO, "Created book {0}", book);
resp.sendRedirect("/read?id=" + id);
}
}
