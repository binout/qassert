package org.qassert.pmd;

import net.sourceforge.pmd.*;
import net.sourceforge.pmd.typeresolution.rules.imports.UnusedImports;
import org.qassert.QAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;

public class PMDAssert extends QAssert {

    private RuleSet rules;

    public PMDAssert(List<File> files)  {
        super(files);
        this.rules = new RuleSet();
    }

    @Override
    protected int processFile(File file) {
        String fullPath = Paths.get("").toAbsolutePath() + "/" + file.toString();

        PMD pmd = new PMD();
        RuleContext ruleContext = new RuleContext();
        ruleContext.setSourceType(SourceType.JAVA_17);
        ruleContext.setSourceCodeFile(new File(fullPath));
        ruleContext.setSourceCodeFilename(fullPath);
        Report report = new Report();
        ruleContext.setReport(report);

        try {
            pmd.processFile(new FileReader(fullPath), new RuleSets(rules), ruleContext, SourceType.JAVA_17);
            return report.getViolationTree().size();
        } catch (FileNotFoundException  | PMDException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected PMDAssert withConfig(File file) {
        try {
            rules = new RuleSetFactory().createRuleSet(new FileInputStream(file));
            return this;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PMDAssert unusedImports()  {
        UnusedImports rule = new UnusedImports();
        rule.setMessage("UnusedImports");
        rules.addRule(rule);
        return this;
    }
}
