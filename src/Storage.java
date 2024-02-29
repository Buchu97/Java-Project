import java.util.ArrayList;
import java.util.List;

public class Storage implements Subject {
    private String storageName;
    private Inventory items;
    private List<Observer> observers;

    public Storage(String name, Inventory startingInventory) {
        storageName = name;
        items = startingInventory;
        observers = new ArrayList<>();
    }

    public Inventory getInventory() {
        return items;
    }

    public String getName() {
        return storageName;
    }
    
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(items);  // Pass the current inventory to observers
        }
    }
    public void store(ItemInterface item) {
        items.addOne(item);
        notifyObservers();  // Notify observers when an item is stored
    }

    public ItemInterface retrieve(ItemInterface item) throws ItemNotAvailableException {
        ItemInterface removed = items.remove(item);
        notifyObservers();  // Notify observers when an item is retrieved
        return removed;
    }
}
