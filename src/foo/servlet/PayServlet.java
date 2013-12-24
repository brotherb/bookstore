package foo.servlet;

import foo.dao.UserDao;
import foo.model.Document;
import foo.model.User;
import foo.util.FileCompressor;

import javax.print.Doc;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(
        description = "Pay Servlet",
        urlPatterns = {"/pay"}
)
public class PayServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        if(httpSession.getAttribute("loggedin") == null){
            httpSession.setAttribute("nexturl","/pay");
            response.sendRedirect("/login");
//            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            User user = (User)httpSession.getAttribute("user");
            List<Document> docincart = (ArrayList<Document>)httpSession.getAttribute("docincart");
            if(docincart == null){
                response.sendRedirect("/404.jsp");
                return;
            }
            int sum = 0;
            ArrayList<String> list = new ArrayList<String>(0);
            for(Document item : docincart){
                sum += item.getPrice();
                list.add(item.getPath() + "\\" + item.getName());
            }


            int money = user.getPoints();
            if(sum > money){
                httpSession.setAttribute("error", "积分不足");
                response.sendRedirect("/cart.jsp");
                return;
            }

            String[] filenames = new String[list.size()];
            filenames = list.toArray(filenames);
            String path = getServletContext().getRealPath("/zips");
            String zipfile =  path + "\\" + (new Date()).getTime()+".zip";
            try{
                FileCompressor.zipFiles(filenames,zipfile);
            }catch (Exception e){
                response.sendRedirect("/failure.jsp");
                return;
            }

            UserDao userdao = new UserDao();
            user.setPoints(money - sum);
            userdao.updateUser(user);
            httpSession.removeAttribute("docincart");
            httpSession.setAttribute("zipfile", zipfile);
            response.sendRedirect("/success");
        }
    }
}
