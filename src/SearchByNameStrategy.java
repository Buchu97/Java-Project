import java.util.ArrayList;

public class SearchByNameStrategy implements ItemSearchStrategy {
    public ArrayList<ItemInterface> search(ArrayList<ItemInterface> stock, String searchTerm) {
        String term = searchTerm.toLowerCase();
        ArrayList<ItemInterface> result = new ArrayList<>(stock);
        for (int i = 0; i < result.size(); i++) {
            ItemInterface curItem = result.get(i);
            if (!curItem.getName().contains(term)) {
                result.remove(i);
                i--;  // Going back to revisit current index on next run of loop
            }
        }
        return result;
    }
}
