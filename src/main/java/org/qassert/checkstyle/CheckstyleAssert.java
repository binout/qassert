package org.qassert.checkstyle;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.qassert.QAssert;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class CheckstyleAssert extends QAssert {

    private List<File> files;
    private Configuration configuration;

    public CheckstyleAssert(List<File> files) {
        super(files);
        configuration = new DefaultConfiguration("checker");
    }

    @Override
    protected int processFile(File file) {
        try {
            Checker checker = new Checker();
            checker.setModuleClassLoader(Checker.class.getClassLoader());
            checker.configure(configuration);
            return checker.process(Arrays.asList(file));
        } catch (CheckstyleException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected CheckstyleAssert withConfig(File file) {
        try {
            InputSource inputSource = new InputSource(new FileInputStream(file));
            configuration = ConfigurationLoader.loadConfiguration(inputSource, new PropertiesExpander(System.getProperties()), false);
        } catch (FileNotFoundException | CheckstyleException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public CheckstyleAssert unusedImports() {
        DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
        treeWalker.addChild(new DefaultConfiguration("UnusedImports"));
        ((DefaultConfiguration)configuration).addChild(treeWalker);
        return this;
    }
}
