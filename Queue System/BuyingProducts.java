public class BuyingProducts extends Request {
    private String[] itemsToBuy;

    // Constructor to initialize the BuyingProducts object
    public BuyingProducts(String description, int priority, int difficulty, String[] itemsToBuy) {
        super();
        this.itemsToBuy = itemsToBuy;
        setDescription(description);
        setPriority(priority);
        setDifficulty(difficulty);
        setFactor(2); // Assuming the factor is constant
        setStatus(Status.NEW);
    }

    // Overriding the method to calculate the processing time based on difficulty and items to buy
    @Override 
    public int calculateProcessingTime() {
        return getDifficulty() * getFactor() * itemsToBuy.length;
    }

    // Getter for itemsToBuy
    public String[] getItemsToBuy() {
        return itemsToBuy;
    }
