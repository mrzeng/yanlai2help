package org.spring.springboot.zw.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author wangyanlai
 * @date 2018.5.16
 * @jdk.version 1.8
 * @desc 文件工具类
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 读取文件内容转换为字符串
     *
     * @param filePath 文件路径，带文件名
     * @return
     */
    public static String readFileToString(String filePath) throws IOException, ServletException {
        String os = System.getProperty("os.name");
        //   if (os.startsWith("win") || os.startsWith("Win")) {
        return readFileToString(filePath, CreditConstants.CharSet_GBK);
        //}else{
        //      return readFileToString(filePath, CreditConstants.CharSet_UTF_8);
        //   }

    }

    /**
     * 读取文件内容转换为字符串
     *
     * @param filePath 文件路径，带文件名
     * @param charset  读取文件时的编码方式，默认为GBK
     * @return
     */
    public static String readFileToString(String filePath, String charset) throws IOException, ServletException {

        if (StringUtils.isBlank(filePath)) {
            throw new ServletException("文件路径参数为空！");
        }
        // 设置编码方式
        if (StringUtils.isBlank(charset)) {
            charset = CreditConstants.CharSet_GBK;
        }
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            String tempStr = null;
            String lineSeparator = System.getProperty("line.separator");
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis, charset);
            br = new BufferedReader(isr);
            while ((tempStr = br.readLine()) != null) {
                sb.append(tempStr + lineSeparator);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (br != null) {
                br.close();
            }
        }
        String finalStr = sb.toString();
        return finalStr;
    }

    /**
     * 读取文件内容转换为字符串
     *
     * @param filePath   文件路径，带文件名
     * @param contentStr 文件内容字符串
     * @return
     */
    public static void writeStringToFile(String filePath, String contentStr)
            throws IOException, ServletException {
        String os = System.getProperty("os.name");
        //    if (os.startsWith("win") || os.startsWith("Win")) {
        writeStringToFile(filePath, contentStr, CreditConstants.CharSet_GBK);
        //    }else{
        //    writeStringToFile(filePath, contentStr, CreditConstants.CharSet_UTF_8);
        //   }

    }

    /**
     * 读取文件内容转换为字符串
     *
     * @param filePath   文件路径，带文件名
     * @param contentStr 文件内容字符串
     * @param charset    写文件时的编码方式，默认为GBK
     * @return
     */
    public static void writeStringToFile(String filePath, String contentStr, String charset)
            throws IOException, ServletException {

        if (StringUtils.isBlank(filePath)) {
            throw new ServletException("文件路径参数为空！");
        }
        if (StringUtils.isBlank(contentStr)) {
            throw new ServletException("待写入内容字符串为空！");
        }
        // 设置编码方式
        if (StringUtils.isBlank(charset)) {
            charset = CreditConstants.CharSet_GBK;
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            File file = new File(filePath);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            fos = new FileOutputStream(filePath);
            osw = new OutputStreamWriter(fos, charset);
            bw = new BufferedWriter(osw);
            bw.write(contentStr);
            bw.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (osw != null) {
                osw.close();
            }
            if (bw != null) {
                bw.close();
            }
        }
    }

    public static String streamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        String data = baos.toString();
        baos.close();
        return data;
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader bf = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();
        int aaa;
        while ((aaa = bf.read()) != -1) {
            buffer = buffer.append((char) aaa);
        }
        String data = buffer.toString();
        isr.close();
        return data;
    }

    public static void saveToImgByBytes(byte[] byteFile, String path) throws Exception {
        if (byteFile != null) {
            InputStream in = new ByteArrayInputStream(byteFile);

            File file = new File(path);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);

            byte[] b = new byte[1024];
            int nRead = 0;
            while ((nRead = in.read(b)) != -1) {
                fos.write(b, 0, nRead);
            }
            fos.flush();
            fos.close();
            in.close();
        }
    }

    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 二级链接文件写入磁盘
     */
    public static void writeSecondFile(String result, String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        BufferedWriter writer = new BufferedWriter(write);
        writer.write(result);
        writer.close();
    }

    /**
     * 根据filePath创建相应的目录
     *
     * @param filePath 要创建的文件路经
     * @return file 文件
     * @throws IOException
     */
    public static File mkdirFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        return file;
    }
}
