import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueueSystem {
    private String queueIdentifier; // Unique identifier for the queue (e.g., "Queue A", "Queue B")
    private int capacity; // Maximum capacity of the queue
    private List<Client> activeClients; // List of clients currently in the queue
    private Client currentClient; // Client currently being served
    private Request currentRequest; // Current request being processed
    private int startTime; // Time when the current request started processing
    private List<Client> processedClients; // History of clients who have been processed

    // Constructor to initialize the queue with a name and capacity
    public QueueSystem(String queueIdentifier, int capacity) {
        this.queueIdentifier = queueIdentifier;
        this.capacity = capacity;
        this.activeClients = new ArrayList<>();
        this.processedClients = new ArrayList<>();
        this.currentClient = null;
        this.currentRequest = null;
        this.startTime = -1; // Indicates no processing has started
    }

    // Method to add a client to the queue
    public boolean addClientToQueue(Client client) {
        if (activeClients.size() < capacity) {
            activeClients.add(client);
            System.out.println("Client " + client.getFirstName() + " added to " + queueIdentifier + ".");
            return true;
        } else {
            System.out.println("Queue " + queueIdentifier + " is at full capacity.");
            return false;
        }
    }

    // Method to serve the next client in the queue
    public Optional<Client> serveNextClient() {
        if (!activeClients.isEmpty()) {
            currentClient = activeClients.remove(0);
            currentRequest = currentClient.getRequests()[0]; // Assuming the first request is served
            startTime = System.currentTimeMillis();
            System.out.println("Serving Client: " + currentClient.getFirstName() + " in " + queueIdentifier);
            return Optional.of(currentClient);
        } else {
            System.out.println("No clients left to serve in " + queueIdentifier);
            return Optional.empty();
        }
    }

    // Method to process the current request for the current client
    public void processCurrentRequest() {
        if (currentClient != null && currentRequest != null) {
            int processingTime = currentRequest.calculateProcessingTime();
            int currentTime = (int) (System.currentTimeMillis() - startTime);
            if (currentTime >= processingTime) {
                System.out.println("Request completed for " + currentClient.getFirstName());
                processedClients.add(currentClient); // Move to processed clients
                currentClient = null; // Reset current client and request
                currentRequest = null;
            } else {
                System.out.println("Processing request for " + currentClient.getFirstName() + " ("
                        + currentTime + "/" + processingTime + " ms).");
            }
        } else {
            System.out.println("No client or request is currently being processed.");
        }
    }

    // Method to clear the queue entirely
    public void clearQueue() {
        activeClients.clear();
        System.out.println(queueIdentifier + " cleared.");
    }

    // Method to display the current status of the queue
    public void displayQueueStatus() {
        System.out.println("[" + queueIdentifier + "] Capacity: " + capacity);
        System.out.println("Currently Serving: " + (currentClient != null ? currentClient.getFirstName() : "None"));
        System.out.println("Clients in Queue: ");
        activeClients.forEach(client -> System.out.println(" - " + client.getFirstName() + " (ID: " + client.getId() + ")"));
        System.out.println("Processed Clients: " + processedClients.size());
    }

    // Override toString() to provide a detailed string representation of the queue
    @Override
    public String toString() {
        StringBuilder queueString = new StringBuilder("[Queue: " + queueIdentifier + "]\n");
        queueString.append("Capacity: ").append(capacity).append("\n");
        queueString.append("Current Client: ").append(currentClient != null ? currentClient.getFirstName() : "None").append("\n");
        queueString.append("Clients in Queue: ").append(activeClients.size()).append("\n");
        for (Client client : activeClients) {
            queueString.append(" - ").append(client.getFirstName()).append(" (ID: ").append(client.getId()).append(")\n");
        }
        queueString.append("Total Processed Clients: ").append(processedClients.size()).append("\n");
        return queueString.toString();
    }

    // Method to get the total number of clients served
    public int getTotalProcessedClients() {
        return processedClients.size();
    }

    // Method to calculate the average processing time of requests in the queue
    public double getAverageProcessingTime() {
        if (processedClients.isEmpty()) {
            return 0.0;
        }
        int totalProcessingTime = processedClients.stream()
                .flatMap(client -> List.of(client.getRequests()).stream())
                .mapToInt(Request::calculateProcessingTime)
                .sum();
        return (double) totalProcessingTime / processedClients.size();
    }
}
