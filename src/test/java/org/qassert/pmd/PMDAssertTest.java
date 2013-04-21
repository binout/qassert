package org.qassert.pmd;

import net.sourceforge.pmd.PMDException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.qassert.QAssertions.assertCheckstyle;
import static org.qassert.QAssertions.assertPMD;

public class PMDAssertTest {

    public static final String EXAMPLES_UNUSED_IMPORTS = "src/test/java/org/qassert/examples/UnusedImports.java";

    @Test
    public void assertPMD_unusedimport_should_not_fail() throws Exception {
        assertPMD().unusedImports().hasNoViolations();
    }

    @Test(expected = AssertionError.class)
    public void assertPMD_unusedimport_should_fail() throws Exception {
        assertPMD(new File(EXAMPLES_UNUSED_IMPORTS)).unusedImports().hasNoViolations();
    }

    @Test(expected = AssertionError.class)
    public void assertPMD_unusedimport_with_config_should_fail() throws Exception {
        String config = Thread.currentThread().getContextClassLoader().getResource("pmd/UnusedImports.xml").getFile();
        assertPMD(new File(EXAMPLES_UNUSED_IMPORTS)).withConfig(new File(config)).hasNoViolations();
    }

    @Test
    public void assertPMD_unusedimport_should_count_errors() throws Exception {
        int errors = assertPMD(new File(EXAMPLES_UNUSED_IMPORTS)).unusedImports().countViolations();
        Assert.assertEquals(1, errors);
    }
}
