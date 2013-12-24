package foo.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.*;

public class FileConverter {
    static final int wdFormatPDF = 17;
    static final int ppSaveAsPDF = 32;

    public static void toPdf(String filename){
        File source = new File(filename);
        if (!source.exists()) {
            return ;
        }
        String tofilename = null;

        if(filename.endsWith(".pdf"))
            return;

        if(filename.endsWith(".doc") || filename.endsWith(".txt") || filename.endsWith(".xls") || filename.endsWith(".ppt")){
            tofilename = filename.substring(0,filename.length()-3) + "pdf";
        }else{
            tofilename = filename.substring(0,filename.length()-4) + "pdf";
        }

        if(filename.endsWith(".doc") || filename.endsWith(".docx") || filename.endsWith(".txt")){
            doc2pdf(filename,tofilename);
        } else if (filename.endsWith(".ppt") || filename.endsWith(".pptx")){
            ppt2pdf(filename,tofilename);
        } else {
            xls2pdf(filename,tofilename);
        }
    }

    private static boolean xls2pdf(String filename, String tofilename) {
        ActiveXComponent app = null;
        Dispatch xls = null;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible",false);
            Dispatch xlss = app.getProperty("Workbooks").toDispatch();
            xls = Dispatch.invoke(xlss, "Open", Dispatch.Method, new Object[]
                    { filename, new Variant(false), new Variant(false) }, new int[9]).toDispatch();
            Dispatch.invoke(xls, "SaveAs", Dispatch.Method, new Object[]
                    { tofilename, new Variant(57), new Variant(false), new Variant(57), new Variant(57), new Variant(false), new Variant(true), new Variant(57), new Variant(false), new Variant(true), new Variant(false) }, new int[1]);
            return true;
        }catch (Exception e) {
            return false;
        } finally {
            if (xls != null) {
                Dispatch.call(xls, "Close", new Variant(false));
            }
            if (app != null) {
                app.invoke("Quit");
            }
            ComThread.Release();
        }
    }

    public static boolean doc2pdf(String srcFilePath, String pdfFilePath){
        ActiveXComponent app = null;
        Dispatch doc = null;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
                    new Object[] { srcFilePath,
                            new Variant(false),
                            new Variant(true),//是否只读
                            new Variant(false),
                            new Variant("pwd") },
                    new int[1]).toDispatch();
//          Dispatch.put(doc, "Compatibility", false);  //兼容性检查,为特定值false不正确
            Dispatch.put(doc, "RemovePersonalInformation", false);
            Dispatch.call(doc, "ExportAsFixedFormat", pdfFilePath, wdFormatPDF); // word保存为pdf格式宏，值为17

            return true; // set flag true;
        } catch (ComFailException e) {
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (doc != null) {
                Dispatch.call(doc, "Close", false);
            }
            if (app != null) {
                app.invoke("Quit", 0);
            }
            ComThread.Release();
        }
    }

    public static boolean ppt2pdf(String srcFilePath, String pdfFilePath) {
        ActiveXComponent app = null;
        Dispatch ppt = null;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("PowerPoint.Application");
            Dispatch ppts = app.getProperty("Presentations").toDispatch();

            // 因POWER.EXE的发布规则为同步，所以设置为同步发布
            ppt = Dispatch.call(ppts, "Open", srcFilePath, true,// ReadOnly
                    true,// Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();

            Dispatch.call(ppt, "SaveAs", pdfFilePath, ppSaveAsPDF); //ppSaveAsPDF为特定值32

            return true; // set flag true;
        } catch (ComFailException e) {
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (ppt != null) {
                Dispatch.call(ppt, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
            ComThread.Release();
        }
    }

    /**
     * SWTOOLS的安装路径,我的SWFTools安装目录为:"C:/Program Files (x86)/SWFTools"
     */
    public static final String SWFTOOLS_PATH = "E:\\SWFTools";

    /**
     * the exit value of the subprocess represented by this Process object. By
     * convention, the value 0 indicates normal termination.
     *
     * @param sourcePath
     *            pdf文件路径 ，如："c:/hello.pdf"
     * @return 正常情况下返回：0，失败情况返回：1
     * @throws IOException
     */
    public static int toSwf(String sourcePath) throws IOException {

        if(!sourcePath.endsWith(".pdf")){
            return 1;
        }

        // 源文件不存在则返回
        File source = new File(sourcePath);
        if (!source.exists()) {
            return 1;
        }

        String destPath = sourcePath.substring(0,sourcePath.length()-3) + "swf";
        // 调用pdf2swf命令进行转换
        String command = SWFTOOLS_PATH + "/pdf2swf.exe  -t \"" + sourcePath + "\" -o  \"" + destPath + "\" -s flashversion=9 -s languagedir=E:\\xpdf\\xpdf-chinese-simplified ";
        System.out.println("命令操作:" + command + "\n开始转换...");
        // 调用外部程序
        Process process = Runtime.getRuntime().exec(command);
        final InputStream is1 = process.getInputStream();
        new Thread(new Runnable() {
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                try {
                    while (br.readLine() != null)
                        ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // 启动单独的线程来清空process.getInputStream()的缓冲区
        InputStream is2 = process.getErrorStream();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
        // 保存输出结果流
        StringBuilder buf = new StringBuilder();
        String line = null;
        while ((line = br2.readLine()) != null)
            // 循环等待ffmpeg进程结束
            buf.append(line);
        while (br2.readLine() != null)
            ;
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("转换结束...");
        return process.exitValue();
    }
}