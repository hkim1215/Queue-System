import java.util.*;

public class QueueSystem {
    private int clock; // Tracks system time
    private int totalWaitingTime; // Tracks total waiting time across all clients
    private List<Client> waitingLine; // List of clients waiting in line
    private List<Client> clientsWorld; // Clients entering the system
    private Map<String, Queue> queues; // A map of queue names to Queue objects
    private boolean hasTV; // Flag indicating if the waiting area has a TV
    private boolean hasCoffee; // Flag indicating if the waiting area has coffee

    // Constructor to initialize the QueueSystem
    public QueueSystem(boolean hasTV, boolean hasCoffee) {
        this.clock = 0;
        this.hasTV = hasTV;
        this.hasCoffee = hasCoffee;
        this.waitingLine = new ArrayList<>();
        this.clientsWorld = new ArrayList<>();
        this.queues = new HashMap<>();
    }

    // Method to add a client to the system (to be queued later)
    public void addClient(Client client) {
        clientsWorld.add(client);
        adjustPatienceForAmenities(client);
    }

    // Method to initialize a queue with a given name and capacity
    public void addQueue(String queueName, int capacity) {
        queues.put(queueName, new Queue(queueName, capacity));
    }

    // Adjust client patience based on amenities available in the waiting area
    private void adjustPatienceForAmenities(Client client) {
        if (hasTV) {
            client.setPatience(client.getPatience() + 20);
        }
        if (hasCoffee) {
            client.setPatience(client.getPatience() + 15);
        }
    }

    // Method to move clients from the world to the waiting line
    public void moveClientsToWaitingLine(int numberOfClients) {
        for (int i = 0; i < numberOfClients && !clientsWorld.isEmpty(); i++) {
            waitingLine.add(clientsWorld.remove(0));
        }
    }

    // Simulate the progression of time by processing client requests
    public void incrementTime() {
        clock++;
        for (Queue queue : queues.values()) {
            queue.processRequest(clock);
        }
        checkClientPatience();
    }

    // Method to distribute clients from the waiting line to available queues
    public void distributeClientsToQueues() {
        Iterator<Client> waitingIterator = waitingLine.iterator();

        while (waitingIterator.hasNext()) {
            Client client = waitingIterator.next();
            Queue leastBusyQueue = findLeastBusyQueue();

            if (leastBusyQueue != null && leastBusyQueue.canAcceptClient()) {
                leastBusyQueue.addClient(client);
                waitingIterator.remove();
            }
        }
    }

    // Find the least busy queue to balance load
    private Queue findLeastBusyQueue() {
        return queues.values().stream()
                .min(Comparator.comparingInt(Queue::getQueueSize))
                .orElse(null);
    }

    // Check patience of clients in the waiting line and remove those who leave
    private void checkClientPatience() {
        Iterator<Client> iterator = waitingLine.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getPatience() <= 0) {
                System.out.println(client.getFirstName() + " left due to impatience.");
                iterator.remove();
            } else {
                client.setPatience(client.getPatience() - 1);
            }
        }
    }

    // Method to get the status of the queue system
    public void displayQueueStatus() {
        System.out.println("Current System Time: " + clock);
        System.out.println("Clients in waiting line: " + waitingLine.size());
        queues.values().forEach(queue -> {
            System.out.println(queue.toString());
        });
    }

    // Get total waiting time for statistics
    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    // Process the entire system for multiple time steps
    public void runSimulation(int timeSteps) {
        for (int i = 0; i < timeSteps; i++) {
            incrementTime();
            distributeClientsToQueues();
            displayQueueStatus();
        }
    }

    // Internal Queue class to manage clients and request processing
    class Queue {
        private String name;
        private int capacity;
        private List<Client> queueClients;
        private Client currentClient;
        private int currentProcessingStartTime;

        public Queue(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
            this.queueClients = new ArrayList<>();
            this.currentClient = null;
            this.currentProcessingStartTime = -1;
        }

        public int getQueueSize() {
            return queueClients.size();
        }

        public boolean canAcceptClient() {
            return queueClients.size() < capacity;
        }

        public void addClient(Client client) {
            if (queueClients.size() < capacity) {
                queueClients.add(client);
                System.out.println(client.getFirstName() + " added to " + name);
            } else {
                System.out.println("Queue " + name + " is full!");
            }
        }

        // Process the request of the client being served
        public void processRequest(int currentTime) {
            if (currentClient == null && !queueClients.isEmpty()) {
                currentClient = queueClients.remove(0);
                currentProcessingStartTime = currentTime;
                System.out.println("Serving " + currentClient.getFirstName() + " in " + name);
            }

            if (currentClient != null) {
                Request currentRequest = currentClient.getRequests()[0]; // Assume first request
                int processingTime = currentRequest.calculateProcessingTime();

                if ((currentTime - currentProcessingStartTime) >= processingTime) {
                    System.out.println(currentClient.getFirstName() + " completed service in " + name);
                    currentClient = null;
                }
            }
        }

        // Overriding toString to show queue status
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[Queue: ").append(name).append("] ");
            sb.append("Current Client: ").append(currentClient != null ? currentClient.getFirstName() : "None").append(" | ");
            sb.append("Queue Size: ").append(queueClients.size());
            return sb.toString();
        }
    }
}
