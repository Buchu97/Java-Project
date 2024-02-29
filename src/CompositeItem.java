import java.util.ArrayList;
import java.util.List;

public class CompositeItem extends Item {
    private ItemDefinition definition;
    private List<ItemInterface> components = new ArrayList<>();

    public CompositeItem(ItemDefinition def, List<ItemInterface> components) {
        super(def);
        this.components = components;
        this.definition = def;
    }

    public void addComponent(ItemInterface item) {
        components.add(item);
    }


    @Override
    public double getWeight() {
        return components.stream().mapToDouble(ItemInterface::getWeight).sum();
    }

    @Override
    public String getName() {
        return definition.getName();
    }

    @Override
    public String getDescription() {
        return definition.getDescription();
    }

    @Override
    public ItemDefinition getDefinition() {
        return definition;
    }

    @Override
    public String getCompositionDescription() {
        StringBuilder description = new StringBuilder();
        for (ItemInterface item : components) {
            description.append(item.getName()).append("\n");
        }
        return description.toString();
    }

    @Override
    public boolean equals(ItemInterface other) {
        return isOf(other.getDefinition());
    }

    @Override
    public boolean isOf(ItemDefinition def) {
        return getName().equals(def.getName());
    }

    public List<ItemInterface> getComponents() {
        return components;
    }
}
