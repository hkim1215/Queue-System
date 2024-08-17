# Queue System Simulation

This project is a simulation of a multi-queue system designed to handle client requests efficiently. The system includes various components such as VIP clients, standard queues, and a dynamic queue system that adjusts client behavior based on external factors like waiting area amenities (TV, coffee, etc.). The project demonstrates concepts in object-oriented programming, client-server systems, and real-time request processing.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Future Improvements](#future-improvements)
- [Contributions](#contributions)
- [License](#license)

## Overview

The Queue System Simulation mimics real-world service environments where clients queue up to be served by different servers. VIP clients are given priority service, while regular clients are processed based on a First-In-First-Out (FIFO) policy. The system is flexible and scalable, allowing for different queue capacities and varying client priorities.

## Features

- **Dynamic Queue Handling**: Clients are distributed across multiple queues with a priority system for VIP clients.
- **Client Patience System**: Clients in the waiting line lose patience over time, influenced by amenities like TV or coffee.
- **Customizable Queues**: Create multiple queues with customizable capacities and client handling rules.
- **Real-Time Simulation**: Tracks system time, processes client requests, and simulates real-time behavior.
- **Queue Load Balancing**: Clients are distributed to the least busy queue to optimize service.

## Project Structure

Here is an overview of the project files:

- **QueueSystem.java**: Main class responsible for managing multiple queues, client distribution, and time progression. Adjusts client patience based on amenities.
  
- **Queue.java**: Handles individual queue management, including client processing and load balancing.

- **VIPClient.java**: Extends the `Client` class and adds VIP-specific attributes like `priorityLevel` and `memberSince`. VIP clients receive prioritized service.

- **VIPQueue.java**: Specialized queue class for handling VIP clients with a priority-based system.

- **Client.java**: Represents a client waiting in the queue with a set of requests. Clients have patience, which can be influenced by amenities.

- **Request.java**: Represents a client's request for service, with varying levels of difficulty and processing time.

## Installation

To run this project locally, you'll need the following:

1. **Java**: Ensure Java is installed on your machine (version 8 or higher).

   You can check your Java installation with:
   ```bash
   java -version
