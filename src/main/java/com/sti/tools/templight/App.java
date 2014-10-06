package com.sti.tools.templight;

import java.io.IOException;

public class App {
    /**
     * @param args template_filename output_filename variables_properties_filename
     */
    public static void main(String[] args) {
        try {
            Main.execute(args);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
