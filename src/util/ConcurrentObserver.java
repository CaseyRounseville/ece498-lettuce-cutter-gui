package util;

public interface ConcurrentObserver<T> {
    public void onChange(T value);
}
