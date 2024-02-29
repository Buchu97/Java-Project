import javax.swing.JFrame;

public class App {
    private Player player;
    private Storage storage;
    private JFrame frame;
    private PageManager manager;

    public App(Player p, Storage s) {
        player = p;
        storage = s;
    
        storage.registerObserver(player);  // Register player as observer
    
        player.setStorageView(storage.getInventory());

        manager = new PageManager(player, storage);

        // Setup of sorting
        setupSearching((InventoryPage) manager.findPage("Player Inventory"));
        setupSearching((InventoryPage) manager.findPage("Storage"));

        // Setup of craftng
        setupCrafting((ItemCraftPage) manager.findPage("Item Crafting"), player);
        setupUncrafting((ProductPage) manager.findPage("Product Page"), player);

        // Window creation
        manager.refresh();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(manager.getJPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Defining what each button in the UI will do.
    void setupSearching(InventoryPage page) {
        page.addSearchByButton(new SearchByButton("All", () -> {
            player.getInventory().setSearchStrategy(new SearchAllStrategy());
            player.getStorageView().setSearchStrategy(new SearchAllStrategy());
        }));
    
        page.addSearchByButton(new SearchByButton("Name", () -> {
            player.getInventory().setSearchStrategy(new SearchByNameStrategy());
            player.getStorageView().setSearchStrategy(new SearchByNameStrategy());
        }));
    
        page.addSearchByButton(new SearchByButton("Description", () -> {
            player.getInventory().setSearchStrategy(new SearchByDescriptionStrategy());
            player.getStorageView().setSearchStrategy(new SearchByDescriptionStrategy());
        }));
    }
    
   

    void setupCrafting(ItemCraftPage page, Player player) {
        page.setCraftAction((def) -> {
            if (def.craft(player)) {
                System.out.println(def.getName() + " has been successfully crafted.");
            }
        });
    }
    
    void setupUncrafting(ProductPage page, Player player) {
        page.setUncraftAction((Item) -> {
            ItemDefinition def = Item.getDefinition();
            if (def.uncraft(player)) {
                System.out.println(def.getName() + " has been successfully uncrafted.");
            }
        });
    }
    
    
}
