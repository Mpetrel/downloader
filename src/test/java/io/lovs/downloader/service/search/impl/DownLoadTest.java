package io.lovs.downloader.service.search.impl;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.*;
import java.util.Arrays;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-25 10:58
 **/
public class DownLoadTest {
    public static void main(String[] args) {
        HttpRequest request = HttpRequest.get("http://47.104.248.133/file/apk/readme");
        System.out.println(request.contentLength() + "  " + request.contentType());
        int length = request.contentLength();
        BufferedInputStream stream = request.buffer();
        int mid = length / 2;
        byte[] bytes = new byte[1024];
        File f = new File("D:\\readme");
        try {
            stream.read(bytes);
            System.out.println(Arrays.toString(bytes));
            RandomAccessFile accessFile = new RandomAccessFile(f, "rw");
            accessFile.seek(mid);
            accessFile.write(bytes, mid, length - 1);
            accessFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
