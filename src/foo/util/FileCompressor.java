package foo.util;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;

public class FileCompressor {

    public static boolean zipFiles(String[] files, String zipfile) throws Exception {
        boolean bf = true;

        // 根据文件路径构造一个文件实例
        File ff = new File(zipfile);
        // 判断目前文件是否存在,如果不存在,则新建一个
        if (!ff.exists()) {
            ff.createNewFile();
        }
        // 根据文件路径构造一个文件输出流
        FileOutputStream out = new FileOutputStream(zipfile);
        // 传入文件输出流对象,创建ZIP数据输出流对象
        ZipOutputStream zipOut = new ZipOutputStream(out);

        // 循环待压缩的文件列表
        for (int i = 0; i < files.length; i++) {
            File f = new File(files[i]);
            if (!f.exists()) {
                bf = false;
            }
            try {
                // 创建文件输入流对象
                FileInputStream in = new FileInputStream(files[i]);
                // 得到当前文件的文件名称
                String fileName = files[i].substring(
                        files[i].lastIndexOf('\\') + 1, files[i].length());
                // 创建指向压缩原始文件的入口
                ZipEntry entry = new ZipEntry(fileName);
                zipOut.putNextEntry(entry);
                // 向压缩文件中输出数据
                int nNumber = 0;
                byte[] buffer = new byte[512];
                while ((nNumber = in.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, nNumber);
                }
                // 关闭创建的流对象
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                bf = false;
            }
        }
        zipOut.close();
        out.close();
        return bf;
    }

}
