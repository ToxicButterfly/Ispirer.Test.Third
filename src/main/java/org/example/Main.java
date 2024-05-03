package org.example;

public class Main {
    public static void main(String[] args) {
        List<String> stringList = new List<>();

        // Добавление слушателя изменений
        stringList.addChangedListener(event -> System.out.println("Размер списка изменился: " + event.getSource().size()));

        stringList.add("Привет");
        stringList.add("Мир");
        stringList.add("!");

        System.out.println("Элемент по индексу 1: " + stringList.get(1));
    }
}