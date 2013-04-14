package org.qassert.checkstyle;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import java.io.File;
import java.util.List;

public class CheckstyleAssert {

    private List<File> files;
    private DefaultConfiguration configuration = new DefaultConfiguration("checker");

    public CheckstyleAssert(List<File> files)  {
        this.files = files;
    }

    public void hasNoErrors() throws CheckstyleException {
        Checker checker = getChecker();
        int errors = checker.process(files);
        if (errors > 0) {
            throw new AssertionError("Checkstyle detects errors");
        }
    }

    public int countErrors() throws CheckstyleException {
        Checker checker = getChecker();
        return checker.process(files);
    }

    private Checker getChecker() throws CheckstyleException {
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(configuration);
        return checker;
    }

    public CheckstyleAssert unusedImport()  {
        DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
        treeWalker.addChild(new DefaultConfiguration("UnusedImports"));
        configuration.addChild(treeWalker);
        return this;
    }
}
