package foo.servlet;

import foo.dao.UserDao;
import foo.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        description = "Register Servlet",
        urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        String conpasswd = request.getParameter("conpasswd");
        String email = request.getParameter("email");

        UserDao ud = new UserDao();

        if(!passwd.equals(conpasswd)){
            request.setAttribute("registerStatus", "两次输入的密码不一致！");
            getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
        } else {
            if(ud.getUserByEmail(email).size()>0){
                request.setAttribute("registerStatus", "邮箱已经被注册过了！");
                getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);

            } else if(ud.getUserByName(username).size()>0){
                request.setAttribute("registerStatus", "用户名已经被注册了！");
                getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
            } else {
                ud.addUser(new User(username, passwd, email));
                response.sendRedirect("/login");
                return;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
