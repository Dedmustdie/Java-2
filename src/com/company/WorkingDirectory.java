package com.company;

import java.io.File;
import java.util.ArrayList;

public class WorkingDirectory {
    private static WorkingDirectory instance;
    private String directoryName;

    private WorkingDirectory(String directoryName) {
        this.directoryName = directoryName;
    }
    public static WorkingDirectory getInstance(String directoryName) {
        if (instance == null) {
            instance = new WorkingDirectory(directoryName);
        }
        return instance;
    }
    public ArrayList<String> getAllFilesOfDirectory() {
        File file = new File(directoryName);
        ArrayList<String> children = new ArrayList();
        String firstDirName = directoryName;
        String previousDirName = directoryName;;
        boolean flag = false;
        if (file.listFiles() != null) {
            for (File child : file.listFiles()) {

                if (child.isDirectory()) {

                    directoryName = previousDirName + "\\" + child.getName();
                    getInstance(directoryName);
                    for (String childOfChild : getAllFilesOfDirectory()) {
                        children.add(childOfChild);
                    }
                    flag = true;
                } else {
                    children.add(child.getName());
                }
                if (!flag) {
                    directoryName = previousDirName;
                }
            }
        }
        else {
            directoryName = previousDirName;
        }
        directoryName = firstDirName;
        getInstance(directoryName);
        return children;
    }
    public  String getParentNameOfDirectory() {
        File file = new File(directoryName);
        return file.getParent();
    }
    public void moveToParentOfDirectory() {
        File file = new File(directoryName);
        directoryName = file.getParent();
        getInstance(directoryName);
    }
    public boolean directoryChecker(String childDirectoryName) {
        File file = new File(directoryName);
        String[] children = file.list();
        for (String child: children) {
            File childFile = new File(directoryName + '\\' + child);
            if (child.equalsIgnoreCase(childDirectoryName) && childFile.isDirectory()) {
                return true;
            }
        }
        return false;
    }
    public  void createNewDirectory(String newDirectoryName) {
        String firstDirectoryName = directoryName;
        directoryName = directoryName + "\\" + newDirectoryName;
        File file = new File(directoryName);
        file.mkdir();
        directoryName = firstDirectoryName;
    }
    public  boolean moveToSubdirectory(String subDirectoryName) {
        if (directoryChecker(subDirectoryName)) {
            directoryName = directoryName + "\\" + subDirectoryName;
            return true;
        }
        return false;
    }
}
