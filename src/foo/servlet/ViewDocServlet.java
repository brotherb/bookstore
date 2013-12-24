package foo.servlet;

import foo.dao.DocDao;
import foo.model.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet(
        description = "",
        urlPatterns = {"/Doc"}
)
public class ViewDocServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String DOCID = request.getParameter("id");
        if(DOCID== null){
            response.sendRedirect("404.jsp");
            return;
        }
        int docid;
        try{
            docid = Integer.parseInt(DOCID);
        }catch (Exception e){
            response.sendRedirect("404.jsp");
            return;
        }

        DocDao docdao = new DocDao();
        Document doc = docdao.getDocById(docid);
        if(doc == null){
            response.sendRedirect("404.jsp");
            return;
        }

        request.setCharacterEncoding("utf-8");
        String filename = doc.getName();
        String title = filename.split("\\.")[0];
        request.setAttribute("title",title);
        String swfname = "upload/" +filename.substring(0, filename.length() - 3) + "swf";
        request.setAttribute("swfname",swfname);
        String authorname = doc.getAuthor().getName();
        request.setAttribute("authorname",authorname);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String uploadtime = sdf.format(doc.getUpload_time());
        request.setAttribute("uploadtime",uploadtime);

        request.setAttribute("docid", doc.getId());

        getServletContext().getRequestDispatcher("/docview.jsp").forward(request, response);
    }
}
