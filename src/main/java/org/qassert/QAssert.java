package org.qassert;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class QAssert {

    private List<File> files;

    public QAssert(List<File> files)  {
        this.files = files;
    }

    public void hasNoViolations() {
        Map<File, Integer> violationsPerFile = computeViolations();
        int nbViolatedFiles = violationsPerFile.size();
        if (nbViolatedFiles > 0) {
            StringBuilder builder = new StringBuilder("Violations detected in ").append(nbViolatedFiles).append(" file(s) :");
            for (File f : files) {
                builder.append("\n").append(f.toString());
            }
            throw new AssertionError(builder.toString());
        }
    }

    public int countViolations() {
        int nbViolations = 0;
        Map<File, Integer> violationsPerFile = computeViolations();
        for (File file : violationsPerFile.keySet()) {
            nbViolations += violationsPerFile.get(file);
        }
        return nbViolations;
    }

    private Map<File, Integer> computeViolations() {
        Map<File, Integer> violationsPerFile = new HashMap<>();
        for (File file : files) {
            int nbViolations = processFile(file);
            if (nbViolations > 0) {
                violationsPerFile.put(file, nbViolations);
            }
        }
        return violationsPerFile;
    }

    protected abstract int processFile(File file);

    protected abstract <T extends QAssert> T withConfig(File file);

}