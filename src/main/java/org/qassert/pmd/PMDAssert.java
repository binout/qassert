package org.qassert.pmd;

import net.sourceforge.pmd.*;
import net.sourceforge.pmd.typeresolution.rules.imports.UnusedImports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class PMDAssert {

    private File file;
    private RuleSet rules;

    public PMDAssert(File file)  {
        this.file = file;
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
        String fullPath = Paths.get("").toAbsolutePath() + "/" + file.toString();

        PMD pmd = new PMD();
        RuleContext ruleContext = new RuleContext();
        ruleContext.setSourceType(SourceType.JAVA_17);
        ruleContext.setSourceCodeFile(new File(fullPath));
        ruleContext.setSourceCodeFilename(fullPath);
        Report report = new Report();
        ruleContext.setReport(report);

        pmd.processFile(new FileInputStream(fullPath), rules, ruleContext);

        return report.getViolationTree().size();
    }
}
