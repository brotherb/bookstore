package foo.servlet;

import foo.dao.DocDao;
import foo.model.Document;
import foo.model.User;
import foo.util.FileConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        description = "Doc Setting Servlet",
        urlPatterns = {"/docsetting", "/docsetting/*"}
)
public class DocSettingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        if(httpSession.getAttribute("loggedin") == null){
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            String description = new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
            int price = Integer.parseInt(request.getParameter("price").toString());
            String path = httpSession.getAttribute("path").toString();
            String filename = httpSession.getAttribute("file").toString();
            User user = (User) httpSession.getAttribute("user");
            Document doc = new Document(filename, path, price, description, user);
            DocDao docdao = new DocDao();
            docdao.addDoc(doc);
            new FileConvertionThread().run(path + "\\" + filename);
            response.sendRedirect("/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        if(httpSession.getAttribute("loggedin") == null){
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/docsetting.jsp").forward(request, response);
        }
    }

    public class FileConvertionThread extends Thread{

        public void run(String filetoconvert) {
            FileConverter.toPdf(filetoconvert);
            try{
                FileConverter.toSwf(filetoconvert.substring(0, filetoconvert.length()-3)+"pdf");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
