package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class EgeScores {
    public void makeScoreFromTxt(String pathOfScoreDocument, String pathToResult) {
        try {
            FileInputStream fileInput;
            fileInput = new FileInputStream(pathOfScoreDocument);
            FileOutputStream fileOutput = new FileOutputStream(pathToResult + "ОШИБКА ВВОДА ДАННЫХ!.txt");
            File errorFile = new File(pathToResult + "ОШИБКА ВВОДА ДАННЫХ!.txt");
            errorFile.delete();
            byte[] buff = new byte[fileInput.available()];
            fileInput.read(buff, 0, buff.length);
            String sBuff = new String(buff, "UTF-8");
            Pattern pattern = Pattern.compile("[\\n]");
            String[] lines = pattern.split(sBuff);
            String name;
            for (String line : lines) {
                if (line.replaceAll("\\r", "").matches("ФИО: (([а-яА-Я]+)|(\\s))+")) {
                    name = line.replaceAll("((ФИО: )|(\\r))", "");
                    fileOutput = new FileOutputStream(pathToResult + "\\" + name + ".TXT");
                    fileOutput.write((name + "\n").getBytes(StandardCharsets.UTF_8));
                }
                if (line.replaceAll("\\r", "").matches("Математика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    if (Integer.parseInt(line.replaceAll("((Математика: )|( баллов)|(\\r))", "")) > 24) {
                        fileOutput.write("Математика сдана\n".getBytes(StandardCharsets.UTF_8));
                    } else {
                        fileOutput.write("Математика не сдана\n".getBytes(StandardCharsets.UTF_8));
                    }
                } else if (line.replaceAll("\\r", "").matches("Русский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    if (Integer.parseInt(line.replaceAll("((Русский язык: )|( баллов)|(\\r))", "")) > 24) {
                        fileOutput.write("Русский язык сдан\n".getBytes(StandardCharsets.UTF_8));
                    } else {
                        fileOutput.write("Русский язык не сдан\n".getBytes(StandardCharsets.UTF_8));
                    }
                } else if (line.replaceAll("\\r", "").matches("Англиский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    if (Integer.parseInt(line.replaceAll("((Англиский язык: )|( баллов)|(\\r))", "")) > 24) {
                        fileOutput.write("Англиский язык сдан\n".getBytes(StandardCharsets.UTF_8));
                    } else {
                        fileOutput.write("Англиский язык не сдан\n".getBytes(StandardCharsets.UTF_8));
                    }
                } else if (line.replaceAll("\\r", "").matches("Физика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    if (Integer.parseInt(line.replaceAll("((Физика: )|( баллов)|(\\r))", "")) > 24) {
                        fileOutput.write("Физика сдана\n".getBytes(StandardCharsets.UTF_8));
                    } else {
                        fileOutput.write("Физика не сдана\n".getBytes(StandardCharsets.UTF_8));
                    }
                } else if (line.replaceAll("\\r", "").matches("Химия: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    if (Integer.parseInt(line.replaceAll("((Химия: )|( баллов)|(\\r))", "")) > 24) {
                        fileOutput.write("Химия сдана\n".getBytes(StandardCharsets.UTF_8));
                    } else {
                        fileOutput.write("Химия не сдана\n".getBytes(StandardCharsets.UTF_8));
                    }
                } else if (line.replaceAll("\\r", "").matches("Литература: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    if (Integer.parseInt(line.replaceAll("((Литература: )|( баллов)|(\\r))", "")) > 24) {
                        fileOutput.write("Литература сдана\n".getBytes(StandardCharsets.UTF_8));
                    } else {
                        fileOutput.write("Литература не сдана\n".getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeScoreFromXml(String pathOfScoreDocument, String pathToResult) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(pathToResult + "ОШИБКА ВВОДА ДАННЫХ!.txt");
            File errorFile = new File(pathToResult + "ОШИБКА ВВОДА ДАННЫХ!.txt");
            errorFile.delete();

            File file = new File(pathOfScoreDocument);
            DocumentBuilderFactory documentBuilder = DocumentBuilderFactory.newInstance();
            Document document = documentBuilder.newDocumentBuilder().parse(file);

            Node root = document.getDocumentElement();
            NodeList applicants = root.getChildNodes();

            for (int i = 0; i < applicants.getLength(); i++) {
                Node applicant = applicants.item(i);
                NodeList aplicantInfo = applicant.getChildNodes();
                for (int j = 0; j < aplicantInfo.getLength(); j++) {
                    Node aplicantInfoItem = aplicantInfo.item(j);
                    if (aplicantInfoItem.getNodeName() == "FullName") {
                        fileOutput = new FileOutputStream(pathToResult + "\\" + aplicantInfoItem.getTextContent() + "(xml).TXT");
                        fileOutput.write((aplicantInfoItem.getTextContent()+"\n").getBytes(StandardCharsets.UTF_8));
                    }
                    else if (aplicantInfoItem.getNodeName() == "Math") {
                        if (Integer.parseInt(aplicantInfoItem.getTextContent()) > 24) {
                            fileOutput.write("Математика сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                        else {
                            fileOutput.write("Математика не сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                    }
                    else if (aplicantInfoItem.getNodeName() == "RussianLanguage") {
                        if (Integer.parseInt(aplicantInfoItem.getTextContent()) > 24) {
                            fileOutput.write("Русский язык сдан\n".getBytes(StandardCharsets.UTF_8));
                        }
                        else {
                            fileOutput.write("Русский язык не сдан\n".getBytes(StandardCharsets.UTF_8));
                        }
                    }
                    else if (aplicantInfoItem.getNodeName() == "EnglishLanguage") {
                        if (Integer.parseInt(aplicantInfoItem.getTextContent()) > 24) {
                            fileOutput.write("Англиский язык сдан\n".getBytes(StandardCharsets.UTF_8));
                        }
                        else {
                            fileOutput.write("Англиский язык не сдан\n".getBytes(StandardCharsets.UTF_8));
                        }
                    }
                    else if (aplicantInfoItem.getNodeName() == "Physics") {
                        if (Integer.parseInt(aplicantInfoItem.getTextContent()) > 24) {
                            fileOutput.write("Физика сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                        else {
                            fileOutput.write("Физика не сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                    }
                    else if (aplicantInfoItem.getNodeName() == "Chemistry") {
                        if (Integer.parseInt(aplicantInfoItem.getTextContent()) > 24) {
                            fileOutput.write("Химия сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                        else {
                            fileOutput.write("Химия не сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                    }
                    else if (aplicantInfoItem.getNodeName() == "Literature") {
                        if (Integer.parseInt(aplicantInfoItem.getTextContent()) > 24) {
                            fileOutput.write("Литература сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                        else {
                            fileOutput.write("Литература не сдана\n".getBytes(StandardCharsets.UTF_8));
                        }
                    }
                }
            }

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
