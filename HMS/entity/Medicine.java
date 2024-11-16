package entity;

// Medicine Entity
public class Medicine {
    private String name;
    private int stock;
    private int lowStockAlert;

    public Medicine(String name, int stock, int lowStockAlert) {
        this.name = name;
        this.stock = stock;
        this.lowStockAlert = lowStockAlert;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public void updateStock(int quantity) {
        this.stock += quantity;
    }
}