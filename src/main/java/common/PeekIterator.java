package common;

import lombok.Data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * @author zhangran
 * @since 2020-09-07
 **/
@Data
public class PeekIterator<T> implements Iterator<T> {
    private Iterator<T> it;

    private LinkedList<T> cachedQueue = new LinkedList<>();
    private LinkedList<T> putbackStack = new LinkedList<>();
    private final static int CACHE_SIZE = 10;
    private T endToken;


    public PeekIterator(Stream<T> in) {
        this.it = in.iterator();
    }

    public PeekIterator(Stream<T> in, T endToken) {
        this.it = in.iterator();
        this.endToken = endToken;
    }

    public T peek() {
        if (!putbackStack.isEmpty()) {
            return putbackStack.peek();
        }
        if (!it.hasNext()) {
            return endToken;
        }
        T val = next();
        putBack();
        return val;

    }

    public void putBack() {
        if (!cachedQueue.isEmpty()) {
            putbackStack.push(cachedQueue.pollLast());
        }

    }


    @Override
    public boolean hasNext() {
        return endToken != null || !putbackStack.isEmpty() || it.hasNext();
    }

    @Override
    public T next() {
        T val;
        if (!putbackStack.isEmpty()) {
            val = putbackStack.pop();
            return val;
        }

        if (!this.it.hasNext()) {
            val = endToken;
            endToken = null;
            return val;
        }

        while (cachedQueue.size() > CACHE_SIZE - 1) {
            cachedQueue.pollLast();
        }
        val = this.it.next();
        cachedQueue.add(val);
        return val;
    }


}
