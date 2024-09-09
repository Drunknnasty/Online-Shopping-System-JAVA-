class Electronics extends Product {
    private String brand;

    public Electronics(int id, String name, double price, String brand) {
        super(id, name, price);
        this.brand = brand;
    }

    @Override
    public String toString() {
        return super.toString() + ", Brand: " + brand;
    }
}