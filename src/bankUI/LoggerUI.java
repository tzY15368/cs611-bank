package bankUI;

import Utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;

public class LoggerUI extends JPanel {
    private Deque<String> logBuffer;
    private JTextPane textPane;
    private JButton button;
    private int bufferSize;
    private boolean paused;

    public LoggerUI(int bufferSize){
        this.bufferSize = bufferSize;
        this.textPane = new JTextPane();
        this.button = new JButton("pause");
        this.logBuffer = new LinkedList<>();
        this.add(textPane);
        this.add(button);
        this.button.addActionListener(e -> {
            button.setText(!paused ? "pause" : "resume");
            this.paused = !this.paused;
        });
        //textPane.setSize(500,100);
        textPane.setEditable(false);
        Logger.getInstance().addHandler(this::addLog);
        this.setBounds(400,600,500,100);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
    }

    public void addLog(String log){
        if(paused)return;
        if(logBuffer.size() >= bufferSize){
            logBuffer.removeFirst();
        }
        logBuffer.addLast(log);
        textPane.setText(String.join("\n", logBuffer));
    }

}
