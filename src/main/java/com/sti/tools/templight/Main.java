package com.sti.tools.templight;

import java.io.File;
import java.io.IOException;

/**
 * Created by s on 06.10.2014.
 */
public class Main {

    public static void execute(String[] args) throws IOException {
        if (args.length < 3)
            throw new IllegalArgumentException("Not all arguments specify: template_filename or output_filename or variables_properties_filename.");

        for (String arg : args) {
            if (arg == null || arg.trim().isEmpty())
                throw new IllegalArgumentException("Some argument is empty.");
        }

        File tmplFile = new File(args[0]);
        File propFile = new File(args[2]);
        File dstFile = new File(args[1]);

        Templater.execute(tmplFile, propFile, dstFile);
    }

}
