package com.example.elim.util;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具類
 */
public class FileUtil {
    /**
     * MultipartFile 轉  File
     * @param "file"
     * 注意：轉換的文件保存在項目的根目錄，因此使用完要及時清理
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        File toFile = null;
        if (multipartFile != null) {
            InputStream in = multipartFile.getInputStream();
            toFile = new File(multipartFile.getOriginalFilename());

            OutputStream os = new FileOutputStream(toFile);
            int r = 0;
            byte[] buffer = new byte[1024];
            while ((r = in.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, r);
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
        return toFile;
    }
}
