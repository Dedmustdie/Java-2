package com.company;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        //----------------------<1-ое задание>--------------------------------
        System.out.println("1) Содержимое каталога: ");
        String firstDir = "C:\\Users\\Kensein\\OneDrive\\Рабочий стол\\JAVA 2";
        WorkingDirectory workingDirectory = WorkingDirectory.getInstance(firstDir);
        ArrayList<String> allFilesList = workingDirectory.getAllFilesOfDirectory();
        for (String child: allFilesList) {
            System.out.println("->" + child);
        }

        String parentOfDir = workingDirectory.getParentNameOfDirectory();
        System.out.println("2) Родитель: ");
        System.out.println(parentOfDir);

        workingDirectory.moveToParentOfDirectory();
        System.out.println("3) Переход к родителю " + parentOfDir + "! Его содержимое: ");
        allFilesList = workingDirectory.getAllFilesOfDirectory();

        for (String child: allFilesList) {
            System.out.println("->" + child);
        }
        String firstDirBuff = firstDir .replaceAll("\\\\", "");
        String returnFirstDir = firstDirBuff.replaceAll(parentOfDir.replaceAll("\\\\", ""), "");
        System.out.println("Переход обратно к " + returnFirstDir);
        workingDirectory.moveToSubdirectory(returnFirstDir);

        String dir = ".idea";
        if (workingDirectory.directoryChecker(dir)) {
            System.out.println("4) " + dir + " существует в текущей директории");
        }
        else {
            System.out.println("4) " + dir + " не существует в текущей директории");
        }

        String newDir = "ewgwe";
        System.out.println("5) Новый каталог " + newDir + " создан");
        workingDirectory.createNewDirectory(newDir);

        String subDir = ".idea";
        if (workingDirectory.moveToSubdirectory(subDir)) {
            System.out.println("6) Переход к подкаталогу " + subDir);
        }
        else {
            System.out.println("6) Подкаталога " + subDir + " не существует в текущей директории");
        }

        //----------------------<2-ое задание>--------------------------------

        EgeScoresTXT egeScoresTxt = new EgeScoresTXT("C:\\Users\\Kensein\\Downloads\\Java-2\\Информация о факультетах.TXT");
        EgeScoresXML egeScoresXml = new EgeScoresXML("C:\\Users\\Kensein\\Downloads\\Java-2\\Информация о факультетах.xml");

        egeScoresTxt.makeScoreFromTxt("C:\\Users\\Kensein\\Downloads\\Java-2\\Информация об абитуриентах.TXT", "C:\\Users\\Kensein\\Downloads\\Java-2");
        egeScoresTxt.makeScoreFromXml("C:\\Users\\Kensein\\Downloads\\Java-2\\Информация об абитуриентах.xml", "C:\\Users\\Kensein\\Downloads\\Java-2");

        egeScoresXml.makeScoreFromTxt("C:\\Users\\Kensein\\Downloads\\Java-2\\Информация об абитуриентах.TXT", "C:\\Users\\Kensein\\Downloads\\Java-2");
        egeScoresXml.makeScoreFromXml("C:\\Users\\Kensein\\Downloads\\Java-2\\Информация об абитуриентах.xml", "C:\\Users\\Kensein\\Downloads\\Java-2");
    }
}
