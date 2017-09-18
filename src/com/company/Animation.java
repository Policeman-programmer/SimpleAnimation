package com.company;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.util.jar.JarFile;

//шарик должен двигатся в рандомном порядке

public class Animation extends JFrame {
    //начальное полоение шарика
    static int x = (int) (Math.random() * 401), y = (int) (Math.random() * 401);
    //ширина высота окна
    static int widht = 700;
    static int height = 600;
    //диаметр шарика
    static int d = 100;
    //цвет
    static Color color = changeColor();

    static int dx = 1, dy = 1; // коефициент смещения


    Animation() {
        super("Oval Animation");
        setBounds(300, 300, widht + 20, height + 40);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    static class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, widht, height);
            g.setColor(color);
            g.fillOval(x, y, d, d);
        }
    }

    public static void makeSound(){
        int randomInstrument = (int)(Math.random()*128);
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();
            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();

            ShortMessage masOn = new ShortMessage(144,1,randomInstrument,100);
            MidiEvent me = new MidiEvent(masOn,0);
            track.add(me);

            ShortMessage masOff= new ShortMessage(128,1,randomInstrument,100);
            me = new MidiEvent(masOff, 2);
            track.add(me);

            player.setSequence(seq);
            player.start();

        }catch (Exception e){
        }
    }

    public static Color changeColor() {
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        Color randomColor = new Color(red, green, blue);
        return randomColor;
    }

    public static void move() {
        x += dx;
        y += dy;
        if (x >= widht - d) {
            dx = -1;
            color = changeColor();
            makeSound();
        }
        if (y >= height - d) {
            dy = -1;
            color = changeColor();
            makeSound();
        }
        if (x == 0) {
            dx = 1;
            color = changeColor();
            makeSound();
        }
        if (y == 0) {
            dy = 1;
            color = changeColor();
            makeSound();
        }
    }

    public static void main(String[] args) {
        Animation frame = new Animation();
        MyDrawPanel panel = new MyDrawPanel();
        frame.setContentPane(panel);
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            move();
            panel.repaint();

        }

    }
}


