package foo.servlet;

import foo.dao.DocDao;
import foo.model.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        description = "Search Servlet",
        urlPatterns = {"/search"}
)
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String[] keywords = request.getParameter("keywords").split(",");
        DocDao docdao = new DocDao();
        List<Document> result = new ArrayList<Document>(0);
        for(String item : keywords){
            List<Document> part = docdao.getDocByKeyword(item);
            result.addAll(part);
        }
        request.setAttribute("result",result);
        getServletContext().getRequestDispatcher("/SearchResult.jsp").forward(request, response);
    }
}
