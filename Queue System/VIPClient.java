public class VIPClient extends Client implements Prioritizable {
    private int memberSince;
    private int priorityLevel;

    // Additional fields for VIP benefits and services
    private String vipStatus; // E.g., "Gold", "Platinum"
    private double discountRate; // VIP discount for services
    private boolean accessToExclusiveEvents;

    // Constructor to initialize VIPClient with more attributes
    public VIPClient(String firstName, String lastName, int birthYear, Gender gender, 
                     int arrivalTime, int patience, Request[] requests, 
                     int memberSince, int priorityLevel, String vipStatus, 
                     double discountRate, boolean accessToExclusiveEvents) {
        super(firstName, lastName, birthYear, gender, arrivalTime, patience, requests);
        this.memberSince = memberSince;
        this.priorityLevel = priorityLevel;
        this.vipStatus = vipStatus;
        this.discountRate = discountRate;
        this.accessToExclusiveEvents = accessToExclusiveEvents;
    }

    // Simplified constructor for basic VIPClient initialization
    public VIPClient(String firstName, String lastName, int birthYear, Gender gender, 
                     int memberSince, int priorityLevel) {
        this(firstName, lastName, birthYear, gender, 0, 10, new Request[0], memberSince, 
             priorityLevel, "Silver", 5.0, false); // Default values
    }

    // Getters and setters for new fields
    public String getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(String vipStatus) {
        this.vipStatus = vipStatus;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public boolean hasAccessToExclusiveEvents() {
        return accessToExclusiveEvents;
    }

    public void setAccessToExclusiveEvents(boolean accessToExclusiveEvents) {
        this.accessToExclusiveEvents = accessToExclusiveEvents;
    }

    // Implementing Prioritizable methods
    @Override
    public int getPriority() {
        return priorityLevel;
    }

    @Override
    public void setPriority(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public int getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(int memberSince) {
        this.memberSince = memberSince;
    }

    // Method to calculate loyalty based on membership duration
    public int calculateLoyaltyYears(int currentYear) {
        return currentYear - memberSince;
    }

    // Override toString() to include VIPClient-specific information
    @Override
    public String toString() {
        return String.format("%s** VIP Status: %s\n** Member Since: %d\n** Priority Level: %d\n" +
                             "** Discount Rate: %.2f%%\n** Access to Exclusive Events: %s\n",
                             super.toString(), vipStatus, memberSince, priorityLevel, 
                             discountRate, accessToExclusiveEvents ? "Yes" : "No");
    }

    // New method to apply VIP discount to a service
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - (discountRate / 100));
    }
    
    // Method to upgrade VIP status based on loyalty years
    public void upgradeVipStatus(int currentYear) {
        int loyaltyYears = calculateLoyaltyYears(currentYear);
        
        if (loyaltyYears > 10) {
            vipStatus = "Platinum";
            discountRate = 20.0;
            accessToExclusiveEvents = true;
        } else if (loyaltyYears > 5) {
            vipStatus = "Gold";
            discountRate = 15.0;
        } else {
            vipStatus = "Silver";
            discountRate = 10.0;
        }
    }
}
