package org.qassert.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Assert;
import org.junit.Test;
import static org.qassert.QAssertions.*;

import java.io.File;

public class CheckstyleAssertTest {

    @Test
    public void assertCheckstyle_unusedimport_should_not_fail() throws Exception {
        assertCheckstyle().unusedImport().hasNoErrors();
    }

    @Test(expected = AssertionError.class)
    public void assertCheckstyle_unusedimport_should_fail() throws CheckstyleException {
        assertCheckstyle(new File("test/java/org/qassert/examples/UnusedImport.java")).unusedImport().hasNoErrors();
    }

    @Test
    public void assertCheckstyle_unusedimport_should_count_errors() throws CheckstyleException {
        int errors = assertCheckstyle(new File("test/java/org/qassert/examples/UnusedImport.java")).unusedImport().countErrors();
        Assert.assertEquals(1, errors);
    }
}
