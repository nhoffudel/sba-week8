package com.github.perscholas.utils;

import java.io.File;

/**
 * @author leonhunter
 * @created 02/12/2020 - 8:58 PM
 */
public enum DirectoryReference {
    RESOURCE_DIRECTORY(new StringBuilder()
            .append(System.getProperty("user.dir")) // local directory
            .append("/src/resources/")
            .toString());

    private final String path;

    DirectoryReference(String path) {
        this.path = path;
    }

    public File getDirectoryFile() {
        return new File(getDirectoryPath());
    }

    public String getDirectoryPath() {
        return path;
    }

    public File getFileFromDirectory(String fileName) {
        return new File(getDirectoryPath() + fileName);
    }
}