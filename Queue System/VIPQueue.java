public class VIPQueue extends Queue {
    private int maxVipQueueSize; // Maximum size for VIP clients in the queue
    private PriorityQueue<VIPClient> vipClientQueue; // Priority queue for VIP clients

    // Constructor that initializes VIPQueue with custom size and behavior
    public VIPQueue(String serverName, int maxVipQueueSize) {
        super(serverName, maxVipQueueSize);
        this.maxVipQueueSize = maxVipQueueSize;
        this.vipClientQueue = new PriorityQueue<>((vip1, vip2) -> 
            Integer.compare(vip2.getPriority(), vip1.getPriority())); // Higher priority first
    }

    // Method to add a VIP client to the queue, enforcing size limit
    public boolean addVipClient(VIPClient vipClient) {
        if (vipClientQueue.size() < maxVipQueueSize) {
            vipClientQueue.offer(vipClient);
            System.out.println("VIP Client " + vipClient.getFirstName() + " added to the VIP queue.");
            return true;
        } else {
            System.out.println("VIP Queue is full. Client " + vipClient.getFirstName() + " cannot be added.");
            return false;
        }
    }

    // Method to process the highest-priority VIP client
    public VIPClient serveVipClient() {
        if (!vipClientQueue.isEmpty()) {
            VIPClient clientBeingServed = vipClientQueue.poll(); // Retrieves and removes the highest-priority client
            System.out.println("Serving VIP Client: " + clientBeingServed.getFirstName());
            return clientBeingServed;
        } else {
            System.out.println("No VIP clients in the queue.");
            return null;
        }
    }

    // Method to display the current status of the VIP queue
    public void displayVipQueueStatus() {
        System.out.println("[VIPQueue:" + getServerName() + "] Current VIP Clients in Queue: " + vipClientQueue.size());
        vipClientQueue.forEach(vip -> 
            System.out.println("VIP Client: " + vip.getFirstName() + " | Priority: " + vip.getPriority() + 
                               " | VIP Status: " + vip.getVipStatus()));
    }

    // Override method to handle VIP-specific queue clearing
    @Override
    public void clearQueue() {
        vipClientQueue.clear();
        System.out.println("VIP Queue cleared.");
    }

    // Method to check if the VIP queue is full
    public boolean isVipQueueFull() {
        return vipClientQueue.size() >= maxVipQueueSize;
    }

    // Override toString to provide a detailed status of the VIPQueue
    @Override
    public String toString() {
        StringBuilder vipQueueInfo = new StringBuilder("[VIPQueue:" + getServerName() + "]\n");
        vipQueueInfo.append("Max VIP Queue Size: ").append(maxVipQueueSize).append("\n");
        vipQueueInfo.append("Current VIP Clients in Queue: ").append(vipClientQueue.size()).append("\n");

        vipClientQueue.forEach(vip -> vipQueueInfo.append("VIP Client: ").append(vip.getFirstName())
            .append(" | Priority: ").append(vip.getPriority())
            .append(" | VIP Status: ").append(vip.getVipStatus()).append("\n"));

        return vipQueueInfo.toString();
    }

    // Method to get the current VIP clients in the queue as an array
    public VIPClient[] getVipClientsInQueue() {
        return vipClientQueue.toArray(new VIPClient[0]);
    }
}
