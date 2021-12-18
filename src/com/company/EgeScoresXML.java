package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EgeScoresXML {

    private Map<String, ArrayList<String>> facultiesScoreMap = new HashMap<>();

    public EgeScoresXML(String pathOfFacultiesScoreDocument) throws IOException {
        try {
            File file = new File(pathOfFacultiesScoreDocument);

            DocumentBuilderFactory documentBuilder = DocumentBuilderFactory.newInstance();
            Document document = documentBuilder.newDocumentBuilder().parse(file);

            Node root = document.getDocumentElement();
            NodeList applicants = root.getChildNodes();

            ArrayList<String> ege = new ArrayList<>();
            for (int i = 0; i < applicants.getLength(); i++) {
                Node applicant = applicants.item(i);
                NodeList aplicantInfo = applicant.getChildNodes();
                for (int j = 0; j < aplicantInfo.getLength(); j++) {
                    Node faclitesInfoItem = aplicantInfo.item(j);

                    if (faclitesInfoItem.getNodeName() == "FacultyName") {
                        facultiesScoreMap.put(faclitesInfoItem.getTextContent(), ege);
                        ege = new ArrayList<>();
                    } else if (faclitesInfoItem.getNodeName() == "Math") {
                        ege.add("Математика: " + faclitesInfoItem.getTextContent() + " баллов");
                    } else if (faclitesInfoItem.getNodeName() == "RussianLanguage") {
                        ege.add("Русский язык: " + faclitesInfoItem.getTextContent() + " баллов");
                    } else if (faclitesInfoItem.getNodeName() == "EnglishLanguage") {
                        ege.add("Англиский язык: " + faclitesInfoItem.getTextContent() + " баллов");
                    } else if (faclitesInfoItem.getNodeName() == "Physics") {
                        ege.add("Физика: " + faclitesInfoItem.getTextContent() + " баллов");
                    } else if (faclitesInfoItem.getNodeName() == "Chemistry") {
                        ege.add("Химия: " + faclitesInfoItem.getTextContent() + " баллов");
                    } else if (faclitesInfoItem.getNodeName() == "Literature") {
                        ege.add("Литература: " + faclitesInfoItem.getTextContent() + " баллов");
                    }
                    if (faclitesInfoItem.getNodeName() == "FacultyName") {
                        facultiesScoreMap.put(faclitesInfoItem.getTextContent(), ege);
                    }
                }

            }
            int i32 = 1;
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void makeScoreFromTxt(String pathOfScoreDocument, String pathToResult) {
        try {
            Map<String, ArrayList<String>> studentScoreMap = new HashMap<>();
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
            ArrayList<String> ege = new ArrayList<>();
            for (String line : lines) {
                if (line.replaceAll("\\r", "").matches("ФИО: (([а-яА-Я]+)|(\\s))+")) {
                    ege = new ArrayList<>();
                    studentScoreMap.put(line.replaceAll("((ФИО: )|(\\r))", ""), ege);
                }
                if (line.replaceAll("\\r", "").matches("Математика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    ege.add(line);
                } else if (line.replaceAll("\\r", "").matches("Русский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    ege.add(line);
                } else if (line.replaceAll("\\r", "").matches("Англиский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    ege.add(line);
                } else if (line.replaceAll("\\r", "").matches("Физика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    ege.add(line);
                } else if (line.replaceAll("\\r", "").matches("Химия: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    ege.add(line);
                } else if (line.replaceAll("\\r", "").matches("Литература: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                    ege.add(line);
                }
                if (line.replaceAll("\\r", "").matches("ФИО: (([а-яА-Я]+)|(\\s))+")) {
                    studentScoreMap.put(line.replaceAll("((ФИО: )|(\\r))", ""), ege);
                }
            }

            int egeFacultiesSize = 0;
            for (Map.Entry<String, ArrayList<String>> itemStudent: studentScoreMap.entrySet()) {

                String name = itemStudent.getKey().replaceAll("((ФИО: )|(\\r))", "");
                fileOutput = new FileOutputStream(pathToResult + "\\" + name + "(xmltxt).TXT");
                fileOutput.write((name + "\n").getBytes(StandardCharsets.UTF_8));

                for (Map.Entry<String, ArrayList<String>> itemFaculties: facultiesScoreMap.entrySet()) {
                    int facultyFlag = 0;
                    ArrayList<String> egeStudent = itemStudent.getValue();
                    ArrayList<String> egeFaculties = itemFaculties.getValue();

                    for (String scoreStudent: egeStudent) {
                        egeFacultiesSize = 0;
                        for (String scoreFaculties: egeFaculties) {
                            egeFacultiesSize++;
                            if (scoreStudent.replaceAll("\\r", "").matches("Математика: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Математика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Математика: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Математика: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Русский язык: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Русский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Русский язык: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Русский язык: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Физика: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Физика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Физика: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Физика: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Англиский язык: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Англиский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Англиский язык: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Англиский язык: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Химия: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Химия: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Химия: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Химия: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Литература: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Литература: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Литература: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Литература: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                        }
                    }
                    if (facultyFlag == egeFacultiesSize) {
                        fileOutput.write(("Вы прошли на факультет: " + itemFaculties.getKey() + "\n").getBytes(StandardCharsets.UTF_8));
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
            Map<String, ArrayList<String>> studentScoreMap = new HashMap<>();

            FileOutputStream fileOutput = new FileOutputStream(pathToResult + "ОШИБКА ВВОДА ДАННЫХ!.txt");
            File errorFile = new File(pathToResult + "ОШИБКА ВВОДА ДАННЫХ!.txt");
            errorFile.delete();

            File file = new File(pathOfScoreDocument);
            DocumentBuilderFactory documentBuilder = DocumentBuilderFactory.newInstance();
            Document document = documentBuilder.newDocumentBuilder().parse(file);

            Node root = document.getDocumentElement();
            NodeList applicants = root.getChildNodes();

            ArrayList<String> ege = new ArrayList<>();
            for (int i = 0; i < applicants.getLength(); i++) {
                Node applicant = applicants.item(i);
                NodeList aplicantInfo = applicant.getChildNodes();
                for (int j = 0; j < aplicantInfo.getLength(); j++) {
                    Node aplicantInfoItem = aplicantInfo.item(j);

                    if (aplicantInfoItem.getNodeName() == "FullName") {
                        studentScoreMap.put(aplicantInfoItem.getTextContent(), ege);
                        ege = new ArrayList<>();
                    } else if (aplicantInfoItem.getNodeName() == "Math") {
                        ege.add("Математика: " + aplicantInfoItem.getTextContent() + " баллов");
                    } else if (aplicantInfoItem.getNodeName() == "RussianLanguage") {
                        ege.add("Русский язык: " + aplicantInfoItem.getTextContent() + " баллов");
                    } else if (aplicantInfoItem.getNodeName() == "EnglishLanguage") {
                        ege.add("Англиский язык: " + aplicantInfoItem.getTextContent() + " баллов");
                    } else if (aplicantInfoItem.getNodeName() == "Physics") {
                        ege.add("Физика: " + aplicantInfoItem.getTextContent() + " баллов");
                    } else if (aplicantInfoItem.getNodeName() == "Chemistry") {
                        ege.add("Химия: " + aplicantInfoItem.getTextContent() + " баллов");
                    } else if (aplicantInfoItem.getNodeName() == "Literature") {
                        ege.add("Литература: " + aplicantInfoItem.getTextContent() + " баллов");
                    }
                    if (aplicantInfoItem.getNodeName() == "FullName") {
                        studentScoreMap.put(aplicantInfoItem.getTextContent(), ege);
                    }
                }
            }
            int egeFacultiesSize = 0;
            for (Map.Entry<String, ArrayList<String>> itemStudent: studentScoreMap.entrySet()) {

                String name = itemStudent.getKey().replaceAll("((ФИО: )|(\\r))", "");
                fileOutput = new FileOutputStream(pathToResult + "\\" + name + "(xmlxml).TXT");
                fileOutput.write((name + "\n").getBytes(StandardCharsets.UTF_8));

                for (Map.Entry<String, ArrayList<String>> itemFaculties: facultiesScoreMap.entrySet()) {
                    int facultyFlag = 0;
                    ArrayList<String> egeStudent = itemStudent.getValue();
                    ArrayList<String> egeFaculties = itemFaculties.getValue();

                    for (String scoreStudent: egeStudent) {
                        egeFacultiesSize = 0;
                        for (String scoreFaculties: egeFaculties) {
                            egeFacultiesSize++;
                            if (scoreStudent.replaceAll("\\r", "").matches("Математика: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Математика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Математика: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Математика: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Русский язык: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Русский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Русский язык: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Русский язык: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Физика: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Физика: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Физика: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Физика: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Англиский язык: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Англиский язык: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Англиский язык: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Англиский язык: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Химия: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Химия: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Химия: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Химия: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                            if (scoreStudent.replaceAll("\\r", "").matches("Литература: ([0-9]|([1-9][0-9])|(100)) баллов") &&
                                    scoreFaculties.replaceAll("\\r", "").matches("Литература: ([0-9]|([1-9][0-9])|(100)) баллов")) {
                                int egeStudentInt = Integer.parseInt(scoreStudent.replaceAll("((Литература: )|( баллов)|(\\r))", ""));
                                int egeFacultiesInt = Integer.parseInt(scoreFaculties.replaceAll("((Литература: )|( баллов)|(\\r))", ""));
                                if (egeStudentInt >= egeFacultiesInt) {
                                    facultyFlag++;
                                }
                            }
                        }
                    }
                    if (facultyFlag == egeFacultiesSize) {
                        fileOutput.write(("Вы прошли на факультет: " + itemFaculties.getKey()  + "\n").getBytes(StandardCharsets.UTF_8));
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
