package foo.servlet;

import foo.dao.DocDao;
import foo.model.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceClient;
import java.io.IOException;
import java.util.List;

@WebServlet(
        description = "",
        urlPatterns = {"/doclist"}
)
public class DocListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DocDao docdao = new DocDao();
        List<Document> alldocs = docdao.getAllDoc();
        request.setAttribute("alldoc", alldocs);
        getServletContext().getRequestDispatcher("/doclist.jsp").forward(request,response);
    }
}
