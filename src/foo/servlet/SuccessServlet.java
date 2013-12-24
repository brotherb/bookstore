package foo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        description = "Success Servlet",
        urlPatterns = {"/success"}
)
public class SuccessServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if(session.getAttribute("loggedin") == null){
            response.sendRedirect("/404.jsp");
            return;
        }
        if(session.getAttribute("zipfile") == null){
            response.sendRedirect("/404.jsp");
            return;
        }

        response.sendRedirect("/success.jsp");
    }
}
