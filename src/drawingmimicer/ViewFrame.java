package drawingmimicer;

import drawingmimicer.Utils.SmartPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by Joona on 5.10.2015.
 */
public class ViewFrame extends JFrame {

    private JPanel drawPanel;
    public final static int PANEL_WIDTH = 520;
    public final static int PANEL_HEIGHT = 700;
    private List<List<Point>> actualStrokes;
    private List<List<SmartPoint>> dumbStrokes;
    private DrawController controller;

    public ViewFrame(DrawController controller){
        this.controller = controller;
        actualStrokes = Collections.synchronizedList(new ArrayList<>());
        dumbStrokes = Collections.synchronizedList(new ArrayList<>());
        
        JButton calibrateButton = new JButton("Calibrate");
        calibrateButton.addActionListener(e -> controller.onCalibrateButtonClick());
        JButton readyButton = new JButton("Ready");
        readyButton.addActionListener(e -> controller.onReadyButtonClick());
        add(calibrateButton, BorderLayout.LINE_END);
        add(readyButton, BorderLayout.LINE_START);
        
        initializeDrawPanel();
        add(drawPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        prepareForNextStroke();
        Runnable frameUpdater = () -> {
            while(true) {
                repaint();
                try {
                    sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(frameUpdater).start();
    }
    
    public void prepareForNextStroke(){
        synchronized (actualStrokes) {
            actualStrokes.add(Collections.synchronizedList(new ArrayList<Point>()));
        }
        synchronized (dumbStrokes) {
            dumbStrokes.add(Collections.synchronizedList(new ArrayList<SmartPoint>()));
        }
    }

    public void addPoint(Point p){
        synchronized(actualStrokes){
            actualStrokes.get(actualStrokes.size() - 1).add(p);
            actualStrokes.notifyAll();
        }
    }

    public void addDumbLines(List<SmartPoint> partialStroke){
        if(partialStroke.size()>0){
            synchronized(dumbStrokes){
                dumbStrokes.get(dumbStrokes.size() - 1).addAll(partialStroke);
                dumbStrokes.notifyAll();
            }
            System.out.println("Add dump strokes");
        }
    }

    private void initializeDrawPanel(){
        drawPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                synchronized (actualStrokes) {
                    for (List<Point> stroke : actualStrokes) {
                        synchronized(stroke){
                            if(stroke.size()>1) {
                                for (int i = 0; i < stroke.size() - 1; i++) {
                                    Point from = stroke.get(i);
                                    Point to = stroke.get(i + 1);
                                    g.drawLine(from.x, from.y, to.x, to.y);
                                }
                            }
                            stroke.notifyAll();
                        }
                    }
                    actualStrokes.notifyAll();
                }
                g.setColor(Color.BLACK);
                synchronized (dumbStrokes) {
                    for (List<SmartPoint> stroke : dumbStrokes) {
                        synchronized (stroke) {
                            if(stroke.size()>1) {
                                for (int i = 0; i < stroke.size() - 1; i++) {
                                    Point from = stroke.get(i);
                                    Point to = stroke.get(i + 1);
                                    g.drawLine(from.x, from.y, to.x, to.y);
                                }
                            }
                            stroke.notifyAll();
                        }
                    }
                    dumbStrokes.notifyAll();
                }
            }
        };
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        MouseAdapter mAdapter = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {controller.onMouseExit();}

            @Override
            public void mouseReleased(MouseEvent e) {
                controller.onMouseReleased();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                controller.onMouseDragged(e);
            }
        };
        drawPanel.addMouseListener(mAdapter);
        drawPanel.addMouseMotionListener(mAdapter);

    }
}
