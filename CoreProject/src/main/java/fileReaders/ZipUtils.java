package fileReaders;

import logging.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    private static final int BUFFER_SIZE = 4096;

    public static void unzip(String zipFilePath, String destDirectory) {
        Log.info("The zip file path is " + zipFilePath);
        try {
            File destDir = new File(destDirectory);
            if (!destDir.exists())
                destDir.mkdir();
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filepath;
                if (entry.getName().contains("xlsx"))
                    filepath = destDirectory + "\\" + entry.getName().substring(entry.getName().lastIndexOf("/")).replace("/", "");
                else
                    filepath = destDirectory + "\\" + entry.getName();
                if (!entry.isDirectory())
                    extractFile(zipIn, filepath);
                else {
                    File dir = new File(filepath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filepath) {

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath));
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1)
                bos.write(bytesIn, 0, read);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
