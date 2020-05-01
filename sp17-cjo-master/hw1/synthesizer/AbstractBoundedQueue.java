package synthesizer;

/**
 * Created by vigneshvasu on 2/20/17.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    public AbstractBoundedQueue() { };
    protected int fillCount;
    protected int capacity;
    public int capacity() {
        return this.capacity;
    }
    public int fillCount() {
        return this.fillCount;
    }
}
