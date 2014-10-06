package com.sti.tools.templight;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void _testApp() {
        assertTrue(false);
        URL resource = AppTest.class.getResource("/template.html");
        assertNotNull(resource);

        Pattern p = Pattern.compile("\\{\\{(.*?)\\}\\}");// *? is lazy quantifier
        Matcher m = p.matcher("one {{cat}} two {{cats in}} the yard");
        StringBuffer sb = new StringBuffer();
        String[] varNames = new String[2];
        int i = 0;
        while (m.find()) {
            assertTrue(i < 2);
            varNames[i++] = m.group(1);
//            if (varName.equalsIgnoreCase("cat"))
            m.appendReplacement(sb, Matcher.quoteReplacement("?*dog"));
        }
        m.appendTail(sb);

        assertEquals(varNames[0], "cat");
        assertEquals(varNames[1], "cats in");
        assertEquals("one ?*dog two ?*dog the yard", sb.toString());
    }

    public void testTemplater() {

        URL tmpl = AppTest.class.getResource("/template.html");
        URL prop = AppTest.class.getResource("/var.properties");
        URL root = AppTest.class.getResource("/");
        assertNotNull(tmpl);
        assertNotNull(prop);
        assertNotNull(root);
        File tmplFile = new File(tmpl.getPath());
        File propFile = new File(prop.getPath());
        File dstFile = new File(root.getPath() + "index.html");

        try {
            Templater.execute(tmplFile, propFile, dstFile);
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(e.getLocalizedMessage(), false);
        }

        String[] trueLines = {
                "<!DOCTYPE html>",
                "<html>",
                "<head lang=\"en\">",
                "    <meta charset=\"UTF-8\">",
                "    <title>The title</title>",
                "</head>",
                "<body>",
                "The body",
                "</body>",
                "</html>"
        };

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(dstFile));

            try {
                String line;
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    assertEquals(trueLines[i++], line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException ignore) {
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
