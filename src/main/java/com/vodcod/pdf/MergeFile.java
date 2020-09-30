package com.vodcod.pdf;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import cn.hutool.core.io.FileUtil;

public class MergeFile {
    public static void main(String[] args) {
    	File[] files = FileUtil.ls("C:\\Users\\jingqianwen\\Documents\\餐补发票");
        //String[] files = {  "e:\\1.pdf", "e:\\2.pdf" , "e:\\3.pdf"};
        String savepath = "C:\\Users\\jingqianwen\\Documents\\餐补发票\\tempNew.pdf";
        Boolean bool = mergePdfFiles(files, savepath);
        System.out.println(bool);
    }

    /*
     * 合并pdf文件 
     * @param files 要合并文件数组(绝对路径如{ "e:\\1.pdf", "e:\\2.pdf" ,
     * "e:\\3.pdf"}),合并的顺序按照数组中的先后顺序，如2.pdf合并在1.pdf后。
     * @param newfile 合并后新产生的文件绝对路径，如 e:\\temp\\tempNew.pdf,
     * @return boolean 合并成功返回true；否则，返回false
     * 
     */

    public static boolean mergePdfFiles(File[] files, String newfile) {
        boolean retValue = false;
        Document document = null;
        try {
            document = new Document(new PdfReader(files[0].getPath()).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
            document.open();
            for (int i = 0; i < files.length; i++) {
                PdfReader reader = new PdfReader(files[i].getPath());
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
            retValue = true;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("执行结束");
            document.close();
        }
        return retValue;
    }
}