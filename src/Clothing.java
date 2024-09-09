class Clothing extends Product {
    private String size;

    public Clothing(int id, String name, double price, String size) {
        super(id, name, price);
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString() + ", Size: " + size;
    }
}