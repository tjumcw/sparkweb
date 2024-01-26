package com.mcw.sparkweb.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SyncMongoDBTask {

    public static void main(String[] args) {
        // ZIP 文件的路径
        String zipFilePath = "src/main/resources/data/speech.zip";
        // 解压缩的目标目录
        String destDirectory = "src/main/resources/data/";

        try {
            // 1、调用方法解压 ZIP 文件并提取 .xmi.gz 文件
            unzipZipAndGz(zipFilePath, destDirectory);
            System.out.println("解压成功");

            // 2、删除所有的 .xmi.gz 文件
            deleteXmiGzFiles(destDirectory + "speech");
            System.out.println("删除 .xmi.gz 文件成功");

            // 3、同步数据到mongoDB
            XMIReader.syncDataToDB();
            System.out.println("数据同步到mongoDB成功");

        } catch (IOException e) {
            System.out.println("解压时发生错误：" + e.getMessage());
        }
    }

    // 解压 ZIP 文件并提取 .xmi.gz 文件的方法
    public static void unzipZipAndGz(String zipFilePath, String destDirectory) throws IOException {
        // 用于读写数据的缓冲区
        byte[] buffer = new byte[1024];

        // 步骤 1：解压外部的 ZIP 文件
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            // 当前 ZIP 文件中条目的目标路径
            String entryPath = destDirectory + File.separator + zipEntry.getName();

            // 如果目录不存在，则创建目录
            new File(entryPath).getParentFile().mkdirs();

            if (!zipEntry.isDirectory()) {
                // 提取文件
                FileOutputStream fos = new FileOutputStream(entryPath);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }

            // 关闭当前 ZIP 文件中的条目
            zipInputStream.closeEntry();
        }
        // 关闭 ZIP 文件输入流
        zipInputStream.close();

        // 步骤 2：解压提取目录中的 .xmi.gz 文件
        File extractedDirectory = new File(destDirectory + "speech");
        // 列出提取目录中的 .xmi.gz 文件
        File[] gzFiles = extractedDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".gz"));
        // 检查是否存在 .xmi.gz 文件
        if (gzFiles != null) {
            // 遍历每个 .xmi.gz 文件
            for (File gzFile : gzFiles) {
                // 获取 .xmi.gz 文件的绝对路径
                String gzFilePath = gzFile.getAbsolutePath();
                // 移除 ".gz" 扩展名以获取提取后文件的路径
                String extractedFilePath = gzFilePath.substring(0, gzFilePath.length() - 3);
                // 创建 GZIP 输入流以读取压缩数据
                try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(gzFile));
                     // 创建输出流以写入解压缩后的数据
                     FileOutputStream fos = new FileOutputStream(extractedFilePath)) {

                    int len;
                    // 以块的方式读取和写入数据
                    while ((len = gzipInputStream.read(buffer)) > 0) {
                        // 打印数据到控制台
                        fos.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    // 删除目录中所有的 .xmi.gz 文件的方法
    public static void deleteXmiGzFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // 列出目录中的 .xmi.gz 文件
        File[] xmiGzFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xmi.gz"));

        // 检查是否存在 .xmi.gz 文件
        if (xmiGzFiles != null) {
            // 遍历每个 .xmi.gz 文件并删除
            for (File xmiGzFile : xmiGzFiles) {
                if (xmiGzFile.delete()) {
                    System.out.println("Deleted: " + xmiGzFile.getName());
                } else {
                    System.out.println("Failed to delete: " + xmiGzFile.getName());
                }
            }
        }
    }
}
