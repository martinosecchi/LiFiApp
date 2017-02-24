package msec.itu.lifiapp;

import java.util.LinkedList;

/**
 * Created by martinosecchi on 08/01/17.
 */

public class MyQueue<E> extends LinkedList<E> {

    private int limit;
    private boolean hasLimit = false;

    public MyQueue(int limit) {
        this.limit = limit;
        this.hasLimit = true;
    }
    public MyQueue(){}

    @Override
    public void addLast(E o){
        if (!hasLimit){
            super.addLast(o);
            return;
        }
        if (size() > limit)
            super.pollFirst();
        super.addLast(o);
    }

    public String joinToString(){
        String res = "";
        for (Object elem: this.toArray()) {
            res += elem.toString();
        }
        return res;
    }
}