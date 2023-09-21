package com.cjf.demo.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileTransferListener {

    public static void main(String[] args) throws Exception {
        // 指定监听目录
        String folderPath = "C:/logs";
        Path path = Paths.get(folderPath);

        // 获取文件系统的WatchService实例
        WatchService watchService = path.getFileSystem().newWatchService();

        // 注册监听事件：ENTRY_CREATE表示文件创建时触发
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        // 不断轮询监听事件
        while (true) {
            // 获取监听队列里的下一个文件变化事件
            WatchKey watchKey = watchService.take();

            // 遍历所有文件变化事件
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                // 获取事件类型
                WatchEvent.Kind<?> kind = event.kind();

                // 如果是文件创建事件
                if (StandardWatchEventKinds.ENTRY_CREATE == kind) {
                    // 获取新创建的文件名
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    Path fileName = pathEvent.context();

                    // 记录文件名和内容到日志表
                    File file = new File(folderPath + "/" + fileName.toString());
                    // 读取文件内容并保存到日志表中
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                        String line;
                        // 逐行读取文件内容并输出到控制台
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // 重设WatchKey状态，以便下次监听
            boolean valid = watchKey.reset();
            if (!valid) {
                break;
            }
        }
    }
}

