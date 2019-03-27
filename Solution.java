package JavaRush_Tasks.Java_Collections_4.Level1.Lecture2.task1;
/*
package com.javarush.task.task31.task3101;
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/*
Проход по дереву файлов
*/
/*
1. На вход метода main подаются два параметра.
Первый - path - путь к директории, второй - resultFileAbsolutePath - имя (полный путь) существующего файла, который будет содержать результат.
2. Для каждого файла в директории path и в ее всех вложенных поддиректориях выполнить следующее:
Если у файла длина в байтах НЕ больше 50, то для всех таких файлов:
2.1. Отсортировать их по имени файла в возрастающем порядке, путь не учитывать при сортировке.
2.2. Переименовать resultFileAbsolutePath в 'allFilesContent.txt' (используй метод FileUtils.renameFile, и, если понадобится, FileUtils.isExist).
2.3. В allFilesContent.txt последовательно записать содержимое всех файлов из п. 2.2.1. После каждого тела файла записать "\n".
Все файлы имеют расширение txt.
В качестве разделителя пути используй "/".
Требования:

    •
    Файл, который приходит вторым параметром в main, должен быть переименован в allFilesContent.txt.
    •
    Нужно создать поток для записи в переименованный файл.
    •
    Содержимое всех файлов, размер которых не превышает 50 байт, должно быть записано в файл allFilesContent.txt в отсортированном по имени файла порядке.
    •
    Поток для записи в файл нужно закрыть.
    •
    Не используй статические переменные.
    */

public class Solution {

    private static void recursiveSearch(File folder, Map<String, String> filesMap) {
        File[] allInFolder = folder.listFiles();
        for (int i = 0; i < allInFolder.length; i++) {
            if (allInFolder[i].isFile()) {
                if (allInFolder[i].length() <= 50) {
                    filesMap.put(allInFolder[i].getName(), allInFolder[i].getAbsolutePath());
                }
            } else if (allInFolder[i].isDirectory()) {
                recursiveSearch(allInFolder[i], filesMap);
            }
        }
    }

    public static void main(String[] args) {
        String path = args[0];
        String resultFileAbsolutePath = args[1];

        File folder = new File(path);
        TreeMap<String, String> filesMap = new TreeMap<>();

        File resultFile = new File(resultFileAbsolutePath);
        File newFile = new File(resultFile.getParent() + File.separator + "allFilesContent.txt");
        FileUtils.renameFile(resultFile, newFile);

        recursiveSearch(folder, filesMap);

        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            filesMap.forEach((k,v) -> {
                try (FileInputStream fis = new FileInputStream(v)) {
                    byte[]buff = new  byte[fis.available()];
                    fis.read(buff);
                    fos.write(buff);
                    fos.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("err_in_lambda");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
