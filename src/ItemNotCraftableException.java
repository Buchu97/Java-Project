public class ItemNotCraftableException extends Exception{
    public ItemNotCraftableException(ItemDefinition itemDef) {
        super(String.format("Item '%s' not Craftable", itemDef.getName()));
    }
}