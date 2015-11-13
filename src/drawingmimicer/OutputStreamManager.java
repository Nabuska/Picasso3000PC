package drawingmimicer;

import drawingmimicer.Utils.IOConsumer;
import drawingmimicer.Utils.SmartPoint;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

/**
 * Created by WinNabuska on 27.10.2015.
 */
public class OutputStreamManager {

    private Optional<ObjectOutputStream> optObjOut;
    private final IOConsumer<ObjectOutputStream> sendPoints;
    private Deque<List<Point>> strokes;

    public OutputStreamManager(){
        strokes = new ConcurrentLinkedDeque<>();
        optObjOut = Optional.empty();
        sendPoints = o -> o.writeObject(strokes.pollFirst());
        initialize();
    }

    public void sendDumbStroke(List<Point> stroke){
        List <Point> pointsList = new ArrayList<>();
        for(Point p: stroke){
            if (pointsList.isEmpty() || !pointsList.get(pointsList.size()-1).equals(p)) {
                System.out.println("add");
                    pointsList.add(new Point(p.x, p.y));
            }
        }

        strokes.add(/*stroke.stream().map(p -> new Point(p.x,p.y)).collect(Collectors.toList())*/pointsList);
        if(optObjOut.isPresent()){
            while(!strokes.isEmpty()){
                //System.out.println("send " + strokes.peekFirst().get(0));
                try {
                    List<Point> pList = strokes.pollFirst();
                    optObjOut.get().writeObject(pList);
                    //System.out.println(pList.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
            System.out.println("optObjOut empty");
        /*optObjOut.ifPresent(o -> {
            try {
                while(!strokes.isEmpty()) {
                    sendPoints.accept(o);
                    System.out.println(strokes.peekFirst().get(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
    }

    public void sendCalibrateCommand(){
        sendDumbStroke(Arrays.asList(new SmartPoint(-1, -1))); //list with negative Point
    }

    public void sendAllDoneCommand(){
        sendDumbStroke(new ArrayList<>()); //Empty list
    }

    private void initialize() {
        Runnable openObjectOutputStream = () -> {
            try{
                Socket s = new Socket("10.0.1.1", 1111);
                optObjOut = Optional.of(new ObjectOutputStream(s.getOutputStream()));
            }catch (IOException e){}};
        new Thread(openObjectOutputStream).start();
    }
}
