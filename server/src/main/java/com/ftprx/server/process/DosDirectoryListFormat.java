package com.ftprx.server.process;

import com.ftprx.server.util.ControlCharacters;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DosDirectoryListFormat implements DirectoryListFormat {

    @Override
    public String parse(File file) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime lastModifiedTime = attributes.lastModifiedTime();
            String dateTime = DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm")
                    .withZone(ZoneId.systemDefault())
                    .format(lastModifiedTime.toInstant());
            String type = file.isDirectory() ? "<DIR>" : "";
            String name = file.getName();
            return String.format("%s   %5s   %s", dateTime, type, name);
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
        return ControlCharacters.EMPTY;
    }
}
