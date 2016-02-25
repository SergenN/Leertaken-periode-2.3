package com.github.leertaken.leertaak2.opdracht15;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Hp user on 24-2-2016.
 */
public class Loader{
    private Scanner input;

    public Loader() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();

        if(fileChooser.showOpenDialog(null)==JFileChooser.OPEN_DIALOG){
            File file = fileChooser.getSelectedFile();
            input = new Scanner(file);

        }
    }

    public Scanner getInput() {
        return input;
    }
}
