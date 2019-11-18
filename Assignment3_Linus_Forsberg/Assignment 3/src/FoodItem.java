/**
 * Class describing a item of food
 *
 * @author Linus Forsberg
 */
public class FoodItem {

    private String name; // name of food item
    private double weight; // weight of food item
    private double volume; // volume of food item

    /**
     * Constructor
     *
     * @param name   name of food item
     * @param weight weight of food item
     * @param volume volume of food item
     */
    public FoodItem(String name, double weight, double volume) {
        this.name = name;
        this.weight = weight;
        this.volume = volume;
    }

    /**
     * Simple getter returns name of FoodItem
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Simple getter returns weight of FoodItem
     *
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Simple getter returns volume of FoodItem
     *
     * @return volume
     */
    public double getVolume() {
        return volume;
    }
}
