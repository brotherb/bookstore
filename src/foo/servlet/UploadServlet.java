package foo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import foo.dao.DocDao;
import foo.model.Document;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(
        description = "File Upload Servlet",
        urlPatterns = {"/upload"}
)
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if(session.getAttribute("loggedin") == null){
            session.setAttribute("nexturl","/upload");
            response.sendRedirect("/login");
        } else {
            request.setCharacterEncoding("utf-8");

            DiskFileItemFactory factory = new DiskFileItemFactory();

            String path = getServletContext().getRealPath("/upload");
            factory.setRepository(new File(path));
            factory.setSizeThreshold(1024*1024) ;

            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List<FileItem> list = upload.parseRequest(request);

                for(FileItem item : list){

                    String name =item.getFieldName();
                    if(item.isFormField()){
                        String value = item.getString();
                        request.setAttribute(name,value);
                    }else{
                        String value = item.getName();
                        if(value.length() == 0){
                            request.setAttribute("Error", "请选择要上传的文件！");
                            request.getRequestDispatcher("/upload.jsp").forward(request, response);
                            return;
                        } 
                        if(!(value.endsWith(".txt") || value.endsWith(".xls") || value.endsWith("xlsx")
                                ||value.endsWith(".doc") || value.endsWith(".ppt") || value.endsWith(".pdf")
                                || value.endsWith("pptx") || value.endsWith("docx"))){
                            request.setAttribute("Error", "只能上传txt, pdf, ppt/pptx, doc/docx, xls/xlsx格式的文档！");
                            request.getRequestDispatcher("/upload.jsp").forward(request, response);
                            return;
                        }

//                        DocDao docdao = new DocDao();
//                        List<Document> result = docdao.getDocByName(value);
//                        if(result.size()>0){
//                            request.setAttribute("Error", "已存在同名文档，请重新选择文件，或者修改文件名！");
//                            request.getRequestDispatcher("/upload.jsp").forward(request, response);
//                            return;
//                        }
                        int start = value.lastIndexOf("\\");
                        String filename = value.substring(start + 1);
                        filename = filename.substring(0, filename.length() - 4) + "." + (new Date()).getTime() + filename.substring(filename.length() - 4);

                        session.setAttribute(name, filename);
                        session.setAttribute("path", path);
                        item.write(new File(path, filename));
                    }
                }

            }catch (FileUploadException e){
                e.printStackTrace();
            }catch (Exception e){

            }
            response.sendRedirect("/docsetting");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("loggedin") == null){
            session.setAttribute("nexturl","/upload");
            response.sendRedirect("/login");
        } else {
            response.sendRedirect("/upload.jsp");
        }
    }
}
