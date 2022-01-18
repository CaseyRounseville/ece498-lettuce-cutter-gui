package util;

import java.util.Set;
import java.util.HashSet;

// inspired by this implementation:
// https://www.techyourchance.com/thread-safe-observer-design-pattern-in-java/

public class ConcurrentObservable<T> {
    private T value;

    private final Object lock;

    // maintain a thread safe list of observers
    private Set<ConcurrentObserver<T>> observers;

    public ConcurrentObservable(T value) {
        this.value = value;
        this.lock = new Object();
    }

    public void addObserver(ConcurrentObserver<T> observer) {
        // don't add null observers
        if (observer == null) {
            return;
        }

        // any code that touches the set of observers is a critical section,
        // and the thread running it must have mutually exclusive access to the
        // set
        synchronized (lock) {
            // first see if we need to create the set of observers
            if (observers == null) {
                observers = new HashSet<ConcurrentObserver<T>>();
            }

            // add the observer
            observers.add(observer);
        }
    }

    public void removeObserver(ConcurrentObserver<T> observer) {
        // we don't have null observers
        if (observer == null) {
            return;
        }

        // any code that touches the set of observers is a critical section,
        // and the thread running it must have mutually exclusive access to the
        // set
        synchronized (lock) {
            // make sure the set of observers is not null
            if (observers != null) {
                // remove the observer
                observers.remove(observer);
            }
        }
    }

    private void notifyObservers(T notificationValue) {
        Set<ConcurrentObserver<T>> observersCopy;

        // any code that touches the set of observers is a critical section,
        // and the thread running it must have mutually exclusive access to the
        // set
        synchronized(lock) {
            // make sure the set of observers is not null
            if (observers == null) {
                return;
            }

            // make a copy of the set of observers
            observersCopy = new HashSet<ConcurrentObserver<T>>(observers);
        }

        // notify each observer in the copy of the set of observers
        // note that this means the set of observers can be modified without
        // issue at the same time, since we are iterating over a copy
        for (ConcurrentObserver<T> observer : observersCopy) {
            observer.onChange(notificationValue);
        }
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        synchronized (lock) {
            this.value = value;
        }

        // make sure to notify the observers with the new value
        notifyObservers(value);
    }
}
