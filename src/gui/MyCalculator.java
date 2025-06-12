/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author rkksg
 */
public class MyCalculator extends JFrame {

    private JTextField display;
    private String currentOperator;
    private double firstOperand;
    private boolean startNewNumber;

    public MyCalculator() {
        init();
    }

    private void init() {
        this.setTitle("My Calculater");
        this.setSize(400, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        //Text dispaly panel with a box layout
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Standard Calculator");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setAlignmentX(Component.LEFT_ALIGNMENT);

        displayPanel.add(label);
        displayPanel.add(Box.createVerticalStrut(10));
        displayPanel.add(display, BorderLayout.CENTER);

        //create and add JMenuItem to JmenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        //add action to menuItem Help
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Standard Windows Calculator Clone-V1.0 developed by kksg");
        });

        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        //button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));

        //button icons
        String[] buttonIcons = {
            "CE", "C", "\u2190", "%    ",
            "1/x", "x\u00B2", "\u221Ax", "/",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "\u00B1", "0", "."};

        //use For-each loop to implement buttons
        for (String buttonText : buttonIcons) {
            JButton btn = new JButton(buttonText);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.addActionListener(e -> handleButton(buttonText));
            buttonPanel.add(btn);
        }
        JButton resultBtn = new JButton("=");
        resultBtn.setBackground(Color.decode("#00b4d8"));
        resultBtn.setFont(new Font("Arial", Font.BOLD, 16));
        resultBtn.addActionListener(e -> handleButton("="));
        buttonPanel.add(resultBtn);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        this.setContentPane(mainPanel);

    }

    public void handleButton(String text) {
        switch (text) {
            case "C" -> {
                display.setText("");
                currentOperator = "";
                firstOperand = 0;
                startNewNumber = true;
            }
            case "\u2190" -> {
                String current = display.getText();
                if (!current.isEmpty()) {
                    display.setText(current.substring(0, current.length() - 1));
                }
            }
            case "+", "-", "x", "%", "/" -> {
                try {
                    double value = Double.parseDouble(display.getText());
                    firstOperand = value;
                    currentOperator = text.equals("x") ? "*" : text;
                    startNewNumber = true;
                } catch (NumberFormatException e) {
                    display.setText("ERROR");
                    startNewNumber = true;
                }
            }
            case "=" -> {
                try {
                    double value = Double.parseDouble(display.getText());
                    switch (currentOperator) {
                        case "+" -> {
                            firstOperand = firstOperand + value;
                        }
                        case "-" ->
                            firstOperand = firstOperand - value;
                        case "*" ->
                            firstOperand = firstOperand * value;
                        case "%" ->
                            firstOperand = firstOperand % value;
                        case "/" -> {
                            if (value != 0) {
                                firstOperand = firstOperand / value;
                            } else {
                                display.setText("ERROR");
                                startNewNumber = true;
                                return;
                            }
                        }
                    }
                    display.setText(String.valueOf(firstOperand));
                    startNewNumber = true;
                    currentOperator = null;
                } catch (NumberFormatException e) {
                    display.setText("ERROR");
                    startNewNumber = true;
                }
            }

            default -> {
                if (startNewNumber) {
                    if (text.equals(".")) {
                        display.setText("0.");
                    } else {
                        display.setText(text);
                    }
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + text);
                }
            }

        }
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new MyCalculator().setVisible(true);
    }
}
