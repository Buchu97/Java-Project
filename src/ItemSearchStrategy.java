import java.util.ArrayList;

public interface ItemSearchStrategy {
    ArrayList<ItemInterface> search(ArrayList<ItemInterface> stock, String searchTerm);
}

