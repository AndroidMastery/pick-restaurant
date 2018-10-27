package android.kaushik.com.pickrestaurant.firebase;

public class Restaurant {
    private String id;
    private String name;
    private String cuisine;

    public Restaurant(String id, String name, String cuisine) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        // Generate this from database
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
