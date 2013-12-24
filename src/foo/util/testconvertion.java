package foo.util;

import java.net.URLEncoder;

public class testconvertion {

    public static void main(String[] args){
        try{
            FileCompressor.zipFiles(new String[]{"D:\\github\\bookstore\\lib\\ant.jar",
                    "D:\\github\\bookstore\\lib\\jstl.jar","D:\\github\\bookstore\\lib\\standard.jar"}, "D:\\github\\bookstore\\lib\\shit.zip");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
