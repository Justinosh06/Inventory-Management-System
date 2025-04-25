package Account;

// The Product class represents a product item in the inventory management system.
// It stores various attributes of a product, such as its ID, name, brand, description, category, quantity,
// modal price (cost price), and retail price (selling price).
public class Product {
    private String productID; // Unique identifier for the product.
    private String Name; // Name of the product.
    private String Brand; // Brand of the product.
    private String Description; // Detailed description of the product.
    private String Category; // Category to which the product belongs.
    private int Quantity; // Current quantity of the product in stock.
    private double modalPrice; // The cost price of the product.
    private double retailPrice; // The selling price of the product.

    // Getter method for the productID.
    public String getProductID() {
        return productID;
    }

    // Setter method for the productID.
    public void setProductID(String productID) {
        this.productID = productID;
    }

    // Getter method for the Name.
    public String getName() {
        return Name;
    }

    // Setter method for the Name.
    public void setName(String name) {
        Name = name;
    }

    // Getter method for the Description.
    public String getDescription() {
        return Description;
    }

    // Setter method for the Description.
    public void setDescription(String description) {
        Description = description;
    }

    // Getter method for the Category.
    public String getCategory() {
        return Category;
    }

    // Setter method for the Category.
    public void setCategory(String category) {
        Category = category;
    }

    // Getter method for the Quantity.
    public int getQuantity() {
        return Quantity;
    }

    // Setter method for the Quantity.
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    // Getter method for the modalPrice.
    public double getModalPrice() {
        return modalPrice;
    }

    // Setter method for the modalPrice.
    public void setModalPrice(double modalPrice) {
        this.modalPrice = modalPrice;
    }

    // Getter method for the retailPrice.
    public double getRetailPrice() {
        return retailPrice;
    }

    // Setter method for the retailPrice.
    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    // Getter method for the Brand.
    public String getBrand() {
        return Brand;
    }

    // Setter method for the Brand.
    public void setBrand(String brand) {
        Brand = brand;
    }
}