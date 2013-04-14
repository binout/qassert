package org.qassert.pmd;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import net.sourceforge.pmd.PMDException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.qassert.QAssertions.assertCheckstyle;
import static org.qassert.QAssertions.assertPMD;

public class PMDAssertTest {

    @Test
    public void assertPMD_unusedimport_should_not_fail() throws Exception {
        assertPMD().unusedImport().hasNoErrors();
    }

    @Test(expected = AssertionError.class)
    public void assertPMD_unusedimport_should_fail() throws FileNotFoundException, PMDException {
        assertPMD(new File("src/test/java/org/qassert/examples/UnusedImport.java")).unusedImport().hasNoErrors();
    }

    @Test
    public void assertPMD_unusedimport_should_count_errors() throws FileNotFoundException, PMDException {
        int errors = assertPMD(new File("src/test/java/org/qassert/examples/UnusedImport.java")).unusedImport().countErrors();
        Assert.assertEquals(1, errors);
    }
}
