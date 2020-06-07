package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class TextFile extends Thread {
    Integer index;
    String path;
    Map<String, Integer> wordMap;
    TextFile(Integer index, String path){
        this.index = index;
        this.path = path;
        wordMap = new HashMap<>(); // создаем свой map
    }
    public static void printFrequency(Map wordMap) {

        // Создание связанного списка для хранения всего набора записей
        List<Map.Entry<String, Integer>> linkedMap = new LinkedList<>(wordMap.entrySet());

        // сортировка
        Collections.sort(linkedMap, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -1 * o1.getValue().compareTo(o2.getValue());
            }
        });

        int num = 1;
        //System.out.printf("%-5s %-10s %-10s\n", "№", "Слово", "Частота");
        //System.out.printf("%-30s\n", "----------------------------");
        for (Map.Entry<String, Integer> wordFreq : linkedMap) {
            //System.out.printf("%-5d %-15s %-5d\n", num, wordFreq.getKey(), wordFreq.getValue());
            num++;
        }
    }
    public static Map GetStatistics(String fileName) {
        Map<String, Integer> wordMap = new HashMap<>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;

            // читаем пока есть строки
            while ((line = reader.readLine()) != null) {
                // Конвертируем к маленькому регистру
                line = line.toLowerCase();
                // Убираем все, кроме русских букв
                line = line.replaceAll("[^\\sа-яА-ЯёЁ]", "");
                // Убираем лишние проблеы
                line = line.replaceAll("\\s+", " ").trim();

                // если строка не пустая
                if (!line.isEmpty()) {
                    // Разбиваем линию на строки
                    String[] s = line.split(" ");
                    // Перебераем слова
                    for (String word : s) {
                        // Если слово уже существует, то увеличиваем счетчик
                        if (wordMap.containsKey(word)) {
                            Integer count = wordMap.get(word);
                            wordMap.put(word, count + 1);
                        } else {
                            //Иначе создаем новую запись
                            wordMap.put(word, 1);
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordMap;
    }
    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        System.out.println(path);
        wordMap = GetStatistics(path); //
        for (Iterator<Map.Entry<String, Integer>> entries = wordMap.entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, Integer> entry = entries.next();
            //System.out.println(entry.getValue());
            String query = "INSERT INTO `freq`.`words` (`word`, `freq`, `file_id`) VALUES ('" + entry.getKey()+ "', '" + entry.getValue()+ "', '" + index +"')";
            Connector.executeQuery(query);
        }
        //printFrequency(wordMap);
        System.out.printf("%s fiished... \n", Thread.currentThread().getName());
    }
}
