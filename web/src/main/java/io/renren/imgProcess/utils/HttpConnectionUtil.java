package io.renren.imgProcess.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpConnectionUtil {

//    /**
//     * 多文件上传的方法
//     *
//     * @param actionUrl：上传的路径
//     * @param uploadFilePaths：需要上传的文件路径，数组
//     * @return
//     */
//    @SuppressWarnings("finally")
//    public static String uploadFile(String actionUrl, String[] uploadFilePaths) {
//        String end = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//
//        DataOutputStream ds = null;
//        InputStream inputStream = null;
//        InputStreamReader inputStreamReader = null;
//        BufferedReader reader = null;
//        StringBuffer resultBuffer = new StringBuffer();
//        String tempLine = null;
//
//        try {
//            // 统一资源
//            URL url = new URL(actionUrl);
//            // 连接类的父类，抽象类
//            URLConnection urlConnection = url.openConnection();
//            // http的连接类
//            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
//
//            // 设置是否从httpUrlConnection读入，默认情况下是true;
//            httpURLConnection.setDoInput(true);
//            // 设置是否向httpUrlConnection输出
//            httpURLConnection.setDoOutput(true);
//            // Post 请求不能使用缓存
//            httpURLConnection.setUseCaches(false);
//            // 设定请求的方法，默认是GET
//            httpURLConnection.setRequestMethod("POST");
//            // 设置字符编码连接参数
//            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
//            // 设置字符编码
//            httpURLConnection.setRequestProperty("Charset", "UTF-8");
//            // 设置请求内容类型
//            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//            // 设置DataOutputStream
//            ds = new DataOutputStream(httpURLConnection.getOutputStream());
//            for (int i = 0; i < uploadFilePaths.length; i++) {
//                String uploadFile = uploadFilePaths[i];
//                String filename = uploadFile.substring(uploadFile.lastIndexOf("\\") + 1);
//                ds.writeBytes(twoHyphens + boundary + end);
//                ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + i + "\";filename=\"" + filename
//                        + "\"" + end);
//                ds.writeBytes(end);
//                FileInputStream fStream = new FileInputStream(uploadFile);
//                int bufferSize = 1024;
//                byte[] buffer = new byte[bufferSize];
//                int length = -1;
//                while ((length = fStream.read(buffer)) != -1) {
//                    ds.write(buffer, 0, length);
//                }
//                ds.writeBytes(end);
//                /* close streams */
//                fStream.close();
//            }
//            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
//            /* close streams */
//            ds.flush();
//            if (httpURLConnection.getResponseCode() >= 300) {
//                throw new Exception(
//                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
//            }
//
//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                inputStream = httpURLConnection.getInputStream();
//                inputStreamReader = new InputStreamReader(inputStream);
//                reader = new BufferedReader(inputStreamReader);
//                tempLine = null;
//                resultBuffer = new StringBuffer();
//                while ((tempLine = reader.readLine()) != null) {
//                    resultBuffer.append(tempLine);
//                    resultBuffer.append("\n");
//                }
//            }
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (ds != null) {
//                try {
//                    ds.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (inputStreamReader != null) {
//                try {
//                    inputStreamReader.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//
//            return resultBuffer.toString();
//        }
//    }


    public static String doPostSubmitBody(String url, Map<String, String> map,
                                          String filePath, byte[] body_data, String charset) {
        // 设置三个常用字符串常量：换行、前缀、分界线（NEWLINE、PREFIX、BOUNDARY）；
        final String NEWLINE = "\r\n";
        final String PREFIX = "--";
        final String BOUNDARY = "#";
        HttpURLConnection httpConn = null;
        BufferedInputStream bis = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 实例化URL对象。调用URL有参构造方法，参数是一个url地址；
            URL urlObj = new URL(url);
            // 调用URL对象的openConnection()方法，创建HttpURLConnection对象；
            httpConn = (HttpURLConnection) urlObj.openConnection();
            // 调用HttpURLConnection对象setDoOutput(true)、setDoInput(true)、setRequestMethod("POST")；
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            // 设置Http请求头信息；（Accept、Connection、Accept-Encoding、Cache-Control、Content-Type、User-Agent）
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            httpConn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");
            // 调用HttpURLConnection对象的connect()方法，建立与服务器的真实连接；
            httpConn.connect();

            // 调用HttpURLConnection对象的getOutputStream()方法构建输出流对象；
            dos = new DataOutputStream(httpConn.getOutputStream());
            // 获取表单中上传控件之外的控件数据，写入到输出流对象（根据HttpWatch提示的流信息拼凑字符串）；
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = map.get(key);
                    dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
                    dos.writeBytes("Content-Disposition: form-data; "
                            + "name=\"" + key + "\"" + NEWLINE);
                    dos.writeBytes(NEWLINE);
                    dos.writeBytes(URLEncoder.encode(value.toString(), charset));
                    // 或者写成：dos.write(value.toString().getBytes(charset));
                    dos.writeBytes(NEWLINE);
                }
            }

            // 获取表单中上传控件的数据，写入到输出流对象（根据HttpWatch提示的流信息拼凑字符串）；
            if (body_data != null && body_data.length > 0) {
                dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
                String fileName = filePath.substring(filePath
                        .lastIndexOf(File.separatorChar));
                dos.writeBytes("Content-Disposition: form-data; " + "name=\""
                        + "uploadFile" + "\"" + "; filename=\"" + fileName
                        + "\"" + NEWLINE);
                dos.writeBytes(NEWLINE);
                dos.write(body_data);
                dos.writeBytes(NEWLINE);
            }
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + NEWLINE);
            dos.flush();

            // 调用HttpURLConnection对象的getInputStream()方法构建输入流对象；
            byte[] buffer = new byte[8 * 1024];
            int c = 0;
            // 调用HttpURLConnection对象的getResponseCode()获取客户端与服务器端的连接状态码。如果是200，则执行以下操作，否则返回null；
            if (httpConn.getResponseCode() == 200) {
                bis = new BufferedInputStream(httpConn.getInputStream());
                while ((c = bis.read(buffer)) != -1) {
                    baos.write(buffer, 0, c);
                    baos.flush();
                }
            }
            // 将输入流转成字节数组，返回给客户端。
            return new String(baos.toByteArray(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
                bis.close();
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}