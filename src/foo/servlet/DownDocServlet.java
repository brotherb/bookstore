package foo.servlet;

import foo.dao.DocDao;
import foo.dao.UserDao;
import foo.model.Document;
import foo.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

@WebServlet(
        description = "List ALL Documentation",
        urlPatterns = {"/down"}
)
public class DownDocServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
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

        UserDao userdao = new UserDao();
        User user = (User) session.getAttribute("user");

        int price = doc.getPrice();
        int money = user.getPoints();
        if(price>money){
            session.setAttribute("repeaterror", "积分不足");
            response.sendRedirect("/Doc?id=" + doc.getId());
            return;
        } else {
            user.setPoints(money-price);
            userdao.updateUser(user);
        }

        response.setContentType("application/jar");

        ServletContext ctx = getServletContext();
        InputStream is = ctx.getResourceAsStream("upload\\" + doc.getName());

        response.addHeader("Content-Disposition", "attachment;filename=" + new String(doc.getName().getBytes("utf-8"),"ISO-8859-1"));

        int read = 0;
        byte[] bytes = new byte[1024];

        OutputStream os = response.getOutputStream();
        while ((read = is.read(bytes))!=-1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
    }
}
