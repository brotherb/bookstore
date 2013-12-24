package foo.servlet;

import foo.dao.DocDao;
import foo.model.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        description = "Cart Servlet",
        urlPatterns = {"/addtocart"}
)
public class AddtoCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

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

        HttpSession session = request.getSession();
        DocDao docdao = new DocDao();
        List<Document> docincart = (ArrayList<Document>)session.getAttribute("docincart");
        if(docincart == null){
            docincart = new ArrayList<Document>(0);
        }

        Document doc = docdao.getDocById(docid);
        if(doc == null) {
            response.sendRedirect("404.jsp");
            return;
        }
        if(!docincart.contains(doc)){
            docincart.add(doc);
            session.setAttribute("docincart", docincart);
            session.setAttribute("addtocartsuccessmsg", "文档已加入购物车");
        }else{
            session.setAttribute("repeaterror", "该文档已经在购物车里了，不能重复添加");
        }

//        session.setAttribute("docincart", docincart);
        response.sendRedirect("/Doc?id="+docid);
    }
}
