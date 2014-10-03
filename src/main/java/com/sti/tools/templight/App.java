package com.sti.tools.templight;

import java.io.File;

public class App {
    /**
     * @param args template_filename output_filename variables_properties_filename
     */
    public static void main(String[] args) {
        if (args.length < 3)
            return;

        for (String arg : args) {
            if (arg == null || arg.trim().isEmpty())
                return;
        }

        File tmplFile = new File(args[0]);
        File propFile = new File(args[2]);
        File dstFile = new File(args[1]);

        Templater.execute(tmplFile, propFile, dstFile);
    }
}
