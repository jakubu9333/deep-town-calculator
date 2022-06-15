/**
 * @author Jakub Uhlarik
 */
public class Resource {
    private String name;
    private int cost;
    private CraftingMethod howCrafted;

    public Resource(String name,CraftingMethod howCrafted,int cost){
        this.cost=cost;
        this.name=name;
        this.howCrafted=howCrafted;
    }

}
