package foo.servlet;

import foo.dao.UserDao;
import foo.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(
        description = "Login Servlet",
        urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession httpSession = request.getSession();

        if(httpSession.getAttribute("loggedin") == null){
            UserDao ud = new UserDao();
            List<User> result = ud.validateUserByUsername(username, password);
            if(result.size()>0) {
                request.setCharacterEncoding("utf-8");
                httpSession.setAttribute("loggedin",true);
                httpSession.setAttribute("user",result.get(0));

                if(httpSession.getAttribute("nexturl") == null){
                    response.sendRedirect("/index.jsp");
                }
                else {
                    String nexturl = httpSession.getAttribute("nexturl").toString();
                    httpSession.removeAttribute("nexturl");
                    response.sendRedirect(nexturl);
                }

            } else {
                request.setAttribute("loginstatus", "用户名或密码错误");
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        if(httpSession.getAttribute("loggedin") == null){
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            response.sendRedirect("/index.jsp");
        }
    }
}
