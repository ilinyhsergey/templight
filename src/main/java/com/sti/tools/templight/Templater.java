package com.sti.tools.templight;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sergeyi@speaktoit.com
 */
public class Templater {
    public static boolean execute(File src, File var, File dst) {

        BufferedReader varReader = null;
        BufferedReader srcReader = null;
        BufferedWriter dstWriter = null;
        try {

            if (!var.exists()) {
                return false;
            }
            varReader = new BufferedReader(new FileReader(var));

            if (!src.exists()) {
                return false;
            }
            srcReader = new BufferedReader(new FileReader(src));

            if (!dst.exists()) {
                dst.createNewFile();
            }
            dstWriter = new BufferedWriter(new FileWriter(dst));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            Properties varProp = new Properties();
            varProp.load(varReader);

            String line, newLine;
            while ((line = srcReader.readLine()) != null) {
                newLine = Templater.processLine(line, varProp);
                dstWriter.write(newLine, 0, newLine.length());
                dstWriter.newLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                varReader.close();
                srcReader.close();
                dstWriter.close();
            } catch (IOException ignore) {
            }

        }
        return true;
    }

    /**
     * Replace {{varName}} => varValue
     *
     * @param line    - line contain templates
     * @param varProp - properties for replace: varName=varValue
     * @return line contain fulfilled templates
     */
    private static String processLine(String line, Properties varProp) {

        String regex = "\\{\\{(.*?)\\}\\}";
        int groupId = 1;

        Pattern p = Pattern.compile(regex);// *? is lazy quantifier
        Matcher m = p.matcher(line);
        StringBuffer sb = new StringBuffer();

        String varName, varValue;
        while (m.find()) { // find part like {{varName}}
            varName = m.group(groupId);
            varValue = varProp.getProperty(varName);
            if (varValue != null) {
                // Use Matcher.quoteReplacement just in case if varValue contain regex special characters.
                m.appendReplacement(sb, Matcher.quoteReplacement(varValue));
            }
        }
        m.appendTail(sb);

        return sb.toString();
    }
}
