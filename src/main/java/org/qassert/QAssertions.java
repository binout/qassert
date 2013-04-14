package org.qassert;

import org.qassert.checkstyle.CheckstyleAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QAssertions {

    public static CheckstyleAssert assertCheckstyle() throws IOException {
        String startDir = "src/main/java";
        List<File> files = getJavaFiles(startDir);
        return assertCheckstyle(files);
    }

    private static List<File> getJavaFiles(String startDir) throws IOException {
        final List<File> files = new ArrayList<>();
        Path startPath = Paths.get(startDir);
        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
                File file = filePath.toFile();
                if (file.getName().endsWith(".java")) {
                    files.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
        return files;
    }

    public static CheckstyleAssert assertCheckstyle(File... files) {
        return  new CheckstyleAssert(Arrays.asList(files));
    }

    public static CheckstyleAssert assertCheckstyle(List<File> files) {
        return  new CheckstyleAssert(files);
    }
}
