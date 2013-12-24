package foo.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(
        description = "Downzip Servlet",
        urlPatterns = {"/downzip"}
)
public class DownZipServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if(session.getAttribute("loggedin") == null){
            response.sendRedirect("/login");
            return;
        }

        response.setContentType("application/zip");

        ServletContext ctx = getServletConfig().getServletContext();
        if(session.getAttribute("zipfile")==null){
            response.sendRedirect("/404.jsp");
            return;
        }
        String zipfile = session.getAttribute("zipfile").toString();
        String[] tmp = zipfile.split("\\\\");
        int len = tmp.length;
        InputStream is = ctx.getResourceAsStream("zips\\" + tmp[len-1]);

        response.addHeader("Content-Disposition", "attachment;filename=" + tmp[len-1]);

        int read = 0;
        byte[] bytes = new byte[1024];

        OutputStream os = response.getOutputStream();
        while ((read = is.read(bytes))!=-1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
        session.removeAttribute("zipfile");
    }
}
