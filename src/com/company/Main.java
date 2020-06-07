package com.company;

import java.sql.*;
import java.util.*;
import java.io.File;
import java.lang.String;
import java.util.Map.Entry;

public class Main {

    public static void main(String[] args) throws SQLException {
        ArrayList<String> fileNames = new ArrayList<>();
        String rootDirectory = "texts"; // директория для поиска
        Files.getFiles(new File(rootDirectory), fileNames); // Получение путей текстовых файлов
        Map files = Connector.getDataFromBD("select * from freq.file");
        for (Iterator<Entry<Integer, String>> entries = files.entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<Integer, String> entry = entries.next();
            //System.out.println(entry.getValue());
            new TextFile(entry.getKey(), entry.getValue()).start();
        }

    }

}