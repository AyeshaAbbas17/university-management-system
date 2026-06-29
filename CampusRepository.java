/*
 * Department only knows its own courses while Repository knows ALL courses in
 * university, “The ArrayLists inside classes manage relationships between
 * objects, such as students enrolled in a course. CampusRepository<T> is used
 * as a centralized reusable storage manager for system-wide collections using
 * generics.”
 */

import java.io.*;
import java.util.*;
public class CampusRepository<T> implements Serializable {
    private ArrayList<T> items;

    // Constructor
    public CampusRepository() {
        items = new ArrayList<>();
    }

    // Add Field
    public boolean addItem(T item) {
        if (item != null) {
            items.add(item);
            return true;
        }

        return false;
    }
    // Remove field
    public boolean removeItem(T item) {

        return items.remove(item);
    }
    // Update field
    public boolean updateItem(int index, T newItem) {

        if (index >= 0 && index < items.size() && newItem != null) {

            items.set(index, newItem);
            return true;
        }
        return false;
    }
    // Get Field by index
    public T getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    // Get all items
    public ArrayList<T> getAllItems() {
        return new ArrayList<>(items);
    }

    // Check if Repository contains field
    public boolean containsItem(T item) {
        return items.contains(item);
    }

    // Check if empty
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Get total no of field
    public int getSize() {
        return items.size();
    }

    // Clear all fields
    public void clearAll() {
        items.clear();
    }

    // Display All Fields
    public void displayItems() {
        int i = 1;
        for (T item : items) {
            System.out.println("===== Item " + i + " =====");
            System.out.println(item);
            i++;
        }
    }
    // toString
    @Override
    public String toString() {
        String temp = "";
        int i = 1;
        for (T item : items) {
            temp += "\n===== Item " + i + " =====\n";
            temp += item + "\n";
            i++;
        }
        return temp;
    }
}