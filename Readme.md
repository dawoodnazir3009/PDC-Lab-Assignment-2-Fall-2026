
# PDC Lab – Assignment 2

**Course:** Parallel and Distributed Computing Lab (CS-426L)  
**University:** Air University, Kharian Campus – Department of Computer Science  
**Student:** Dawood Nazir  
**Semester:** 5th – Fall 2026  
**Instructor:** Mr. Qaiser Ali  

**GitHub Repository:**  
[ PDC Lab Assignment 2 – Fall 2026](https://github.com/dawoodnazir3009/PDC-Lab-Assignment-2-Fall-2026)

---

## Overview

This repository contains seven Java programming tasks developed for the Parallel and Distributed Computing Lab assignment.

The assignment focuses on object-oriented programming, custom data structures, multithreading, parallel processing, concurrent collections, and CPU cache locality.

Each question is organized into a separate directory (`Q1` to `Q7`) containing its Java source files and application driver.

The programs demonstrate how Java can be used to implement object-oriented architectures, manage concurrent execution, process large datasets, and analyze memory performance.

---

## Contents

| # | Directory | Topic | Key Concepts |
|---|---|---|---|
| 1 | `Q1` | Trading Event Dispatcher | Inheritance, constructor chaining, method overriding, runtime polymorphism |
| 2 | `Q2` | Packet Symmetry Auditor | Array-based stack, FIFO queue, LIFO operations, palindrome detection |
| 3 | `Q3` | Asynchronous Telemetry Engine | Thread, Runnable, thread naming, sleep, concurrent execution |
| 4 | `Q4` | Parallel Portfolio Assessment | Fork/Join framework, RecursiveTask, RecursiveAction, fork, join |
| 5 | `Q5` | Sensor Grid Search | Abstract classes, parallel minimum and maximum search, multithreading |
| 6 | `Q6` | Inventory Catalog Benchmark | SynchronizedMap, ConcurrentHashMap, thread pools, throughput measurement |
| 7 | `Q7` | Cache Locality Benchmark | Row-major traversal, column-major traversal, CPU cache, performance profiling |

---

## Project Structure

```text
Assig_02/
│
├── Q1/
│   ├── TradeEvent.java
│   ├── FIXOrderEvent.java
│   ├── ReutersQuoteEvent.java
│   └── TradingEngineApp.java
│
├── Q2/
│   ├── TransactionStack.java
│   ├── Requestqueue.java
│   └── PacketSymmetryAuditor.java
│
├── Q3/
│   ├── LogProcessorTask.java
│   ├── TelemetryThread.java
│   └── AsyncWebEngineApp.java
│
├── Q4/
│   ├── ParallelRiskCalculator.java
│   ├── PortfolioThresholdTask.java
│   └── ForkJoinPortfolioApp.java
│
├── Q5/
│   ├── SensorSearchEngine.java
│   ├── ParallelMinSearch.java
│   ├── ParallelMaxSearch.java
│   └── SensorGridMonitorApp.java
│
├── Q6/
│   └── InventoryCatalogManager.java
│
├── Q7/
│   └── CacheLocalityBenchmark.java
│
└── README.md
```

---

## Requirements

- Java Development Kit (JDK)
- Visual Studio Code or another Java IDE
- Java Extension Pack for VS Code (recommended)
- Command Prompt or PowerShell

Check the installed Java version:

```bash
java -version
javac -version
```

---

# Problem Statements and Solution Logic

## Q1 – Trading Event Dispatcher

**Files:** `TradeEvent.java`, `FIXOrderEvent.java`, `ReutersQuoteEvent.java`, `TradingEngineApp.java`

### Problem

Develop an object-oriented financial event dispatcher that processes different types of trading events using inheritance, method overriding, and polymorphism.

### Solution Logic

A superclass named `TradeEvent` provides common information such as sender ID, timestamp, and base price.

The `FIXOrderEvent` subclass represents stock purchase requests and calculates an exchange transaction fee of 0.15%.

The `ReutersQuoteEvent` subclass represents market quotations and calculates the bid-ask spread using bid and ask prices.

The application creates subclass objects using superclass reference variables and calls the overridden `dispatch()` method.

### Concepts Demonstrated

- Inheritance
- Protected instance variables
- Parameterized constructors
- Constructor chaining using `super`
- Method overriding
- Runtime polymorphism
- Dynamic method dispatch

---

## Q2 – Packet Symmetry Auditor

**Files:** `TransactionStack.java`, `Requestqueue.java`, `PacketSymmetryAuditor.java`

### Problem

Develop a packet verification program that uses a custom stack and queue to determine whether a given payload is symmetrical.

### Solution Logic

A custom array-based stack stores packet characters using the Last-In, First-Out (LIFO) principle.

A custom array-based queue stores the same characters using the First-In, First-Out (FIFO) principle.

The program inserts each character into both data structures.

During verification, characters are removed from the stack and queue and compared.

The queue provides the original character order, while the stack provides the reverse order.

If every corresponding character matches, the packet is symmetrical. Otherwise, the packet is not symmetrical.

### Concepts Demonstrated

- Arrays
- Stack implementation
- Queue implementation
- LIFO and FIFO
- Push and pop operations
- Enqueue and dequeue operations
- Palindrome detection
- Data structure comparison

---

## Q3 – Asynchronous Telemetry Engine

**Files:** `LogProcessorTask.java`, `TelemetryThread.java`, `AsyncWebEngineApp.java`

### Problem

Develop a multithreaded telemetry application that performs system logging and monitoring concurrently.

### Solution Logic

The `LogProcessorTask` class implements the `Runnable` interface and performs five logging iterations.

Each iteration displays the current thread name and log information, then pauses for 1000 milliseconds.

The `TelemetryThread` class extends `Thread` and performs five telemetry iterations with an 800-millisecond delay.

The main application creates both worker threads, assigns custom names, and starts them.

The application also displays thread execution states to demonstrate concurrent processing.

### Concepts Demonstrated

- Runnable interface
- Thread class
- Thread creation
- Thread naming
- `Thread.sleep()`
- `Thread.currentThread().getName()`
- Thread execution states
- Concurrent execution

---

## Q4 – Fork/Join Portfolio Assessment

**Files:** `ParallelRiskCalculator.java`, `PortfolioThresholdTask.java`, `ForkJoinPortfolioApp.java`

### Problem

Develop a parallel portfolio assessment application that calculates the cumulative risk score of financial assets and performs a volatility stress test.

### Solution Logic

The `ParallelRiskCalculator` class extends `RecursiveTask` and calculates the sum of asset values.

The application uses a threshold of 1000 elements.

If the array segment contains 1000 or fewer elements, the task calculates the sum directly.

Otherwise, the task divides the array into two smaller segments.

The subtasks execute using `fork()` and their results are combined using `join()`.

The `PortfolioThresholdTask` class extends `RecursiveAction` and applies a 5% stress-test adjustment to asset values exceeding the specified risk cap.

The main application generates 10,000 synthetic asset values and executes the tasks using a `ForkJoinPool`.

### Concepts Demonstrated

- Fork/Join framework
- RecursiveTask
- RecursiveAction
- Divide-and-conquer
- Recursive task splitting
- `fork()`
- `join()`
- ForkJoinPool
- Parallel processing
- Performance measurement

---

## Q5 – Parallel Sensor Grid Search

**Files:** `SensorSearchEngine.java`, `ParallelMinSearch.java`, `ParallelMaxSearch.java`, `SensorGridMonitorApp.java`

### Problem

Develop a parallel sensor monitoring application that identifies minimum temperature readings and maximum vibration values from a large telemetry dataset.

### Solution Logic

The `SensorSearchEngine` abstract class provides the sensor array and the range of elements to process.

It also defines the abstract `processGrid()` method.

The `ParallelMinSearch` subclass searches for the minimum sensor temperature.

The `ParallelMaxSearch` subclass searches for the maximum vibration value.

The main application generates 50,000 sensor readings and distributes the processing across four worker threads.

The workers process their assigned portions of the array.

The application combines their results to determine the global minimum and maximum values.

### Concepts Demonstrated

- Abstract classes
- Method overriding
- Multithreading
- Array partitioning
- Parallel searching
- Minimum and maximum reduction
- Thread synchronization
- Execution timing

---

## Q6 – Inventory Catalog Benchmark

**File:** `InventoryCatalogManager.java`

### Problem

Develop a concurrency benchmark that compares the performance of synchronized maps and concurrent maps under multiple worker threads.

### Solution Logic

The application maintains a shared inventory catalog containing product identifiers and stock quantities.

The first benchmark uses `Collections.synchronizedMap()` with a standard `HashMap`.

The second benchmark uses `ConcurrentHashMap`.

Both benchmarks execute concurrent inventory lookup and update operations using ten worker threads.

The program measures execution time and calculates throughput in operations per second.

The results demonstrate how different thread-safe collection implementations perform under concurrent workloads.

### Concepts Demonstrated

- HashMap
- Collections.synchronizedMap
- ConcurrentHashMap
- Thread safety
- ExecutorService
- Concurrent read and write operations
- Thread contention
- Throughput calculation
- Performance benchmarking

---

## Q7 – Cache Locality Benchmark

**File:** `CacheLocalityBenchmark.java`

### Problem

Develop a memory performance profiling application that compares row-major and column-major traversal of a two-dimensional matrix.

### Solution Logic

The application creates a 2000 × 2000 matrix of double-precision values.

The `traverseRowMajor()` method accesses matrix elements row by row.

The `traverseColumnMajor()` method accesses matrix elements column by column.

Both methods calculate the sum of the matrix elements.

The application uses `System.nanoTime()` to measure execution time.

Each traversal is repeated ten times to compare the average execution time.

The program calculates the speedup ratio between row-major and column-major traversal.

### Concepts Demonstrated

- Two-dimensional arrays
- Nested loops
- Row-major traversal
- Column-major traversal
- CPU cache locality
- Spatial locality
- Cache line utilization
- `System.nanoTime()`
- Performance profiling
- Speedup calculation

---

# Compilation and Execution

## Important

The existing project uses directories named `Q1`, `Q2`, `Q3`, and so on.

The following commands assume that the Java files do not declare package names and that compilation is performed from the repository root.

If package declarations are added to the source files, the compilation and execution commands must be adjusted accordingly.

### Q1 – Trading Event Dispatcher

```bash
javac Q1/*.java
java -cp Q1 TradingEngineApp
```

### Q2 – Packet Symmetry Auditor

```bash
javac Q2/*.java
java -cp Q2 PacketSymmetryAuditor
```

### Q3 – Asynchronous Telemetry Engine

```bash
javac Q3/*.java
java -cp Q3 AsyncWebEngineApp
```

### Q4 – Fork/Join Portfolio Assessment

```bash
javac Q4/*.java
java -cp Q4 ForkJoinPortfolioApp
```

### Q5 – Parallel Sensor Grid Search

```bash
javac Q5/*.java
java -cp Q5 SensorGridMonitorApp
```

### Q6 – Inventory Catalog Benchmark

```bash
javac Q6/*.java
java -cp Q6 InventoryCatalogManager
```

### Q7 – Cache Locality Benchmark

```bash
javac Q7/*.java
java -cp Q7 CacheLocalityBenchmark
```

---

# Git Workflow

This assignment requires maintaining a GitHub repository with at least 50 readable commit messages.

The repository is used to document the development process and track changes to the Java programs.

### Check Repository Status

```bash
git status
```

### Add Changes

```bash
git add .
```

### Commit Changes

```bash
git commit -m "Add Q1 trading event implementation"
```

### Push Changes

```bash
git push origin main
```

### Example Commit Messages

```text
Create initial assignment structure
Add Q1 trading event superclass
Implement FIX order event
Add Reuters quote event
Implement trading engine application
Add Q2 custom transaction stack
Implement request queue operations
Add packet symmetry auditor
Implement telemetry runnable task
Add telemetry thread implementation
Implement ForkJoin risk calculator
Add portfolio stress test task
Implement parallel sensor minimum search
Add parallel sensor maximum search
Implement inventory benchmark
Add cache locality benchmark
Update assignment documentation
```

Each commit should describe the actual change being made.

---

# Learning Outcomes

This assignment demonstrates the following programming concepts:

- Object-oriented programming
- Class inheritance and constructor initialization
- Method overriding and runtime polymorphism
- Custom array-based data structures
- Stack and queue operations
- Multithreading using Thread and Runnable
- Thread lifecycle management
- Parallel computation using Fork/Join
- RecursiveTask and RecursiveAction
- Concurrent collections
- Thread-safe inventory management
- Performance benchmarking
- CPU cache locality
- Memory access optimization

---

# Assignment Submission

**Course:** Parallel and Distributed Computing Lab  
**Course Code:** CS-426L  
**Semester:** Fall 2026  
**University:** Air University, Kharian Campus  

**GitHub Repository:**

[https://github.com/dawoodnazir3009/PDC-Lab-Assignment-2-Fall-2026](https://github.com/dawoodnazir3009/PDC-Lab-Assignment-2-Fall-2026)

---

## Author

**Dawood Nazir**

Air University, Kharian Campus  
Department of Computer Science  
Parallel and Distributed Computing Lab

GitHub: [@dawoodnazir3009](https://github.com/dawoodnazir3009)

---

*This repository contains the Java source code and documentation for Assignment 2, Fall 2026.*
