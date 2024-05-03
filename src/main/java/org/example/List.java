package org.example;

import javax.swing.event.EventListenerList;
import java.util.EventListener;

public class List<T> {

    private Object[] elements;
    private int size;
    private EventListenerList listenerList;

    public List() {
        elements = new Object[10];
        size = 0;
        listenerList = new EventListenerList();
    }

    public void add(T element) {
        if (size == elements.length) {
            // Увеличиваем размер массива, если он заполнен
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
        elements[size++] = element;
        fireListChangedEvent();
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        fireListChangedEvent();
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    public int size() {
        return size;
    }

    public void addChangedListener(ChangedListener listener) {
        listenerList.add(ChangedListener.class, listener);
    }

    public void removeChangedListener(ChangedListener listener) {
        listenerList.remove(ChangedListener.class, listener);
    }

    protected void fireListChangedEvent() {
        ChangedEvent event = new ChangedEvent(this);
        for (ChangedListener listener : listenerList.getListeners(ChangedListener.class)) {
            listener.changed(event);
        }
    }

    // Вспомогательный интерфейс и событие для обработки изменений
    public interface ChangedListener extends EventListener {
        void changed(ChangedEvent event);
    }

    public static class ChangedEvent <T> {
        private final List<T> source;

        public ChangedEvent(List<T> source) {
            this.source = source;
        }

        public List<T> getSource() {
            return source;
        }
    }
}