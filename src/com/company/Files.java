package com.company;

import java.io.File;
import java.util.List;

public class Files {
    public static void getFiles(File rootFile, List<String> fileList) {
        if (rootFile.isDirectory()) {
            System.out.println("Поиск в директории: " + rootFile.getAbsolutePath());
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file: directoryFiles) {
                    if (file.isDirectory()) {
                        getFiles(file, fileList);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".txt")) {
                            String query = "INSERT INTO `freq`.`file` (`path`) VALUES ('"+ file.getAbsolutePath() + "')";
                            query = query.replaceAll("\\\\", "\\\\\\\\");
                            Connector.executeQuery(query);
                            fileList.add(file.getName());

                        }
                    }
                }
            }
        }
    }
}
