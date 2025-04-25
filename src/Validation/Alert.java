package Validation;

import Constant.FontConstant;
import Constant.GUIConstant;
import GUIComponent.roundifyBorder;

import static GUIComponent.Component.*;
import static GUIComponent.Icon.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Method;

public class Alert extends JFrame {
    public Alert(String title, String msg) {
        setTitle(title);
        setResizable(false);
        setVisible(true);
        setSize(480, 160);
        setIconImage(AlertIcon.getImage());
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        
        JPanel panel0 = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        
        JLabel label = new JLabel();

        panel0.setBackground(GUIConstant.black);
        panel0.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel0.setBorder(new EmptyBorder(12, 10, 12, 10));

        add(panel0);
        panel0.add(panel1);

        label.setText("!");
        int labelSize = 100;
        label.setSize(new Dimension(labelSize, labelSize));
        label.setBorder(new CompoundBorder(new roundifyBorder(100), new EmptyBorder(0, 20, 0, 20)));
        label.setForeground(GUIConstant.white1);
        label.setFont(FontConstant.PlexMonoBold.deriveFont(56f));
        panel1.setBackground(GUIConstant.black);
        panel1.add(label);

        JTextArea messageArea = new JTextArea(msg);
        messageArea.setForeground(GUIConstant.white1);
        messageArea.setFont(FontConstant.PlexMonoMedium.deriveFont(12f));
        messageArea.setBackground(GUIConstant.black);
        messageArea.setBounds(0, 0, 320, 180);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        panel0.add(messageArea);
    }

    public Alert(String title, String msg, String exit) {
        setTitle(title);
        setResizable(false);
        setVisible(true);
        setSize(480, 160);
        setIconImage(AlertIcon.getImage());
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        
        JPanel panel0 = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        JLabel label = new JLabel();

        panel0.setBackground(GUIConstant.black);
        panel0.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel0.setBorder(new EmptyBorder(12, 10, 12, 10));

        add(panel0);
        panel0.add(panel1);

        label.setText("!");
        int labelSize = 100;
        label.setSize(new Dimension(labelSize, labelSize));
        label.setBorder(new CompoundBorder(new roundifyBorder(100), new EmptyBorder(0, 20, 0, 20)));
        label.setForeground(GUIConstant.white1);
        label.setFont(FontConstant.PlexMonoBold.deriveFont(56f));
        panel1.setBackground(GUIConstant.black);
        panel1.add(label);

        JTextArea messageArea = new JTextArea(msg);
        messageArea.setForeground(GUIConstant.white1);
        messageArea.setFont(FontConstant.PlexMonoMedium.deriveFont(12f));
        messageArea.setBackground(GUIConstant.black);
        messageArea.setBounds(0, 0, 320, 180);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        panel0.add(messageArea);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}