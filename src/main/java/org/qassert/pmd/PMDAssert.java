package org.qassert.pmd;

import net.sourceforge.pmd.*;
import net.sourceforge.pmd.typeresolution.rules.imports.UnusedImports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;

public class PMDAssert {

    private List<File> files;
    private RuleSet rules;

    public PMDAssert(List<File> files)  {
        this.files = files;
        this.rules = new RuleSet();
    }

    public PMDAssert unusedImport()  {
        UnusedImports rule = new UnusedImports();
        rule.setMessage("UnusedImports");
        rules.addRule(rule);
        return this;
    }

    public void hasNoErrors() throws FileNotFoundException, PMDException {
        int violations = countErrors();
        if (violations > 0) {
            throw new AssertionError("PMD detects violations");
        }
    }

    public int countErrors() throws PMDException, FileNotFoundException {
        int nbViolations = 0;
        for (File file : files) {
            nbViolations += processFile(file);
        }
        return nbViolations;
    }

    private int processFile(File file) throws PMDException, FileNotFoundException {
        String fullPath = Paths.get("").toAbsolutePath() + "/" + file.toString();

        PMD pmd = new PMD();
        RuleContext ruleContext = new RuleContext();
        ruleContext.setSourceType(SourceType.JAVA_17);
        ruleContext.setSourceCodeFile(new File(fullPath));
        ruleContext.setSourceCodeFilename(fullPath);
        Report report = new Report();
        ruleContext.setReport(report);

        pmd.processFile(new FileReader(fullPath), new RuleSets(rules), ruleContext, SourceType.JAVA_17);

        return report.getViolationTree().size();
    }
}
