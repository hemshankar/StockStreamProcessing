package stats.utils;

import javafx.util.Pair;

import java.util.*;

public class InfiniteMedian {

    private PriorityQueue<Double> leftQ = new PriorityQueue<>((x, y) -> -x.compareTo(y));
    private PriorityQueue<Double> rightQ = new PriorityQueue<>();
    public Double lastAdded = -1.0; //mpr
    public synchronized int getSize(){
        return leftQ.size() + rightQ.size();
    }

    public synchronized Pair<Double,Double> getMedianAndMRP(){
        int size = leftQ.size() + rightQ.size();
        //System.out.println("Size: " + size + "[" + leftQ + " --- " + rightQ + "]");
        if(size %2 == 0){
            return new Pair<>(lastAdded, (leftQ.peek() + rightQ.peek())/2);
        }else{
            return new Pair<>(lastAdded, leftQ.peek());
        }
    }


    public synchronized void add(Double val){
        lastAdded = val;
        int size = leftQ.size() + rightQ.size();
        if(size == 0){
            leftQ.add(val);
        }else if(size == 1){
            if(leftQ.peek() > val){
                rightQ.add(leftQ.remove());
                leftQ.add(val);
            }else{
                rightQ.add(val);
            }
        }else{
            if(size % 2 == 0){
                if(rightQ.peek() < val){
                    leftQ.add(rightQ.remove());
                    rightQ.add(val);
                }else{
                    leftQ.add(val);
                }
            }else{
                if(leftQ.peek() > val){
                    rightQ.add(leftQ.remove());
                    leftQ.add(val);
                }else{
                    rightQ.add(val);
                }
            }
        }
    }





    public static void main(String[] args) {
        InfiniteMedian x = new InfiniteMedian();
        List<Double> dList = new ArrayList<>();
        dList.addAll(Arrays.asList(1.2,3.0,2.5,1.4,5.0));
        /*x.leftQ.addAll(dList);
        x.rightQ.addAll(dList);

        while(!x.leftQ.isEmpty()) {
            System.out.println(x.leftQ.remove());
        }
        System.out.println("===================");
        while(!x.rightQ.isEmpty()) {
            System.out.println(x.rightQ.remove());
        }*/

        dList.forEach(i -> x.add(i));

        System.out.println(x.getMedianAndMRP());

    }


}
