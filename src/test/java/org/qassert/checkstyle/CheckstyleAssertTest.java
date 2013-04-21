package org.qassert.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Assert;
import org.junit.Test;
import static org.qassert.QAssertions.*;

import java.io.File;

public class CheckstyleAssertTest {

    public static final String EXAMPLES_UNUSED_IMPORTS = "src/test/java/org/qassert/examples/UnusedImports.java";

    @Test
    public void assertCheckstyle_unusedimport_should_not_fail() throws Exception {
        assertCheckstyle().unusedImports().hasNoViolations();
    }

    @Test(expected = AssertionError.class)
    public void assertCheckstyle_unusedimport_should_fail() throws Exception {
        assertCheckstyle(new File(EXAMPLES_UNUSED_IMPORTS)).unusedImports().hasNoViolations();
    }

    @Test(expected = AssertionError.class)
    public void assertCheckstyle_unusedimport_with_config_should_fail() throws Exception {
        String config = Thread.currentThread().getContextClassLoader().getResource("checkstyle/UnusedImports.xml").getFile();
        assertCheckstyle(new File(EXAMPLES_UNUSED_IMPORTS)).withConfig(new File(config)).hasNoViolations();
    }

    @Test
    public void assertCheckstyle_unusedimport_should_count_errors() throws Exception {
        int errors = assertCheckstyle(new File(EXAMPLES_UNUSED_IMPORTS)).unusedImports().countViolations();
        Assert.assertEquals(1, errors);
    }
}
