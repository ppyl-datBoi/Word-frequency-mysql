package com.company;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.lang.String;
import java.util.Map.Entry;

public class Main extends Connector {

    private static void getFiles(File rootFile, List<String> fileList) {
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
                            executeQuery(query);
                            fileList.add(file.getName());
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws SQLException {
        ArrayList<String> fileNames = new ArrayList<>();
        String rootDirectory = "texts";
        getFiles(new File(rootDirectory), fileNames); // Получение путей текстовых файлов
        Connector conn = new Connector();
        Map files = conn.getDataFromBD("select * from freq.file");

        for (Iterator<Entry<Integer, String>> entries = files.entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<Integer, String> entry = entries.next();
            //System.out.println(entry.getValue());
            new TextFile(entry.getKey(), entry.getValue()).start();
        }

        //System.out.println(files.);
//        for(Map file: files) { // для каждого файла
//            new TextFile(file).start();
//        }
    }

}