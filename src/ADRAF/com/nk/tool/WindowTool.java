package ADRAF.com.nk.tool;

import javax.swing.*;

public class WindowTool {
    private static JFrame JF;
    private static JFrame JFS;


    public static JFrame getJFS() {
        return JFS;
    }

    public static void setJFS(JFrame JFS) {
        WindowTool.JFS = JFS;
    }

    public static JFrame getJF() {
        return JF;
    }


    public static void setJF(JFrame JF) {
        WindowTool.JF = JF;
    }
}
