# Task2- Concurrent Task Manager

## Table of Contents
•	Introduction
•	Authors
•	Technologies
•	Launch
•	Package structure
•	Packages and Classes Descriptions
•	Test
•	Examples of Use 
•	Acknowledgements\Bibliography
•	Contact

## Introduction
The Concurrent Task Manager is a project that allows the creation and execution of tasks concurrently. It includes a custom thread pool executor and a task object that can be submitted to the executor for execution.
The aim of this project is to implement concurrent solutions to the following tasks:
1.	Create a specified number of text files, each containing a specified number of random lines of text.
2.	Count the total number of lines in all of the text files.
3.	Implement a custom thread pool and a custom future task.

## Authors
Batel Cohen & Tal sahar

## Technologies
•	Java 11
•	JUnit 5

## Launch
To use this project, import it as a Maven project into your preferred Java development environment and run the tests in the tests package to ensure that everything is working as expected. 

## Package structure
The project is divided into the following packages:

**	Part 1:**
Contains the implementation of a simple concurrent task manager that executes tasks concurrently using threads.
It includes the following classes:
•	`Ex2_1`: main class that creates a number of text files and counts the total number of lines in all the files concurrently using threads.
•	`LinesCounterThread`: a thread class that counts the number of lines in a single file.
•	`LinesCounterCallable`: a callable class that counts the number of lines in a single file and returns the result.
**	Part 2**: 
contains the implementation of a custom concurrent task manager that executes tasks concurrently using a custom thread pool executor. It includes the following classes:
•	`CustomExecutor`: a custom thread pool executor that executes tasks concurrently using a priority queue.
•	`CustomFutureTask`: a custom future task that wraps a task and includes a priority field to allow for priority execution of tasks.
•	`MaxPriorityHolder`: a class that keeps track of the maximum priority value of submitted tasks.
•	`Task`: a task class that represents a unit of work to be executed concurrently. It includes a task type field to allow for different treatment of tasks based on their type.

•	**Part 3:** Test unit.

## Packages and Classes Descriptions
### part1 package
**Ex2_1 class**
*Ex2_1* is a utility class that contains methods for creating a specified number of text files with a random number of lines and a specified upper bound, and for cleaning up the created files. It also contains a method for counting the total number of lines in a set of files using threads.
###### It has the following capabilities:
1. 	Create n text files, and print to them random number of lines.
2. .	Calculate the total number of lines of all the created files one by one.
3. 	Calculate the total number of lines of all the created files by running concurrently multiple threads.
4. 	Calculate the total number of lines of all the created files by running tasks concurrently by a thread pool.

###### The program has four main methods:
1.	`createTextFiles(int n, int seed, int bound):` Creates n files with a pre-defined line. The number of lines in each file is randomly generated using the provided seed and bound. It returns an array of strings holding the names of the created files.
2.	`getNumOfLines(String[] fileNames):` Calculates the total number of lines in the files provided one by one. It takes an array of strings holding file names as an input, and returns the total number of lines.
3.	`getNumOfLinesWithThreads(String[] fileNames):` Calculates the total number of lines in the files provided by running a thread to work on each file. It takes an array of strings holding file names as an input, and returns the total number of lines.
4.	`getNumOfLinesWithThreadPool(String[] fileNames):` Calculates the total number of lines in the files provided by running tasks concurrently using a thread pool. It takes an array of strings holding file names as an input, and returns the total number of lines.

**LinesCounterCallable class**
This is a Java class that implements the *Callable interface*. The purpose of this class is to calculate and return the number of lines of a text file.

###### The program has two main methods:
1.	`LinesCounterCallable(String fileName):` which holds the name of the file to scan for lines. It has a single constructor that takes the file name as an input and sets the fileName field.
2.	`call():` which is implemented from the Callable interface. This method creates a File object and scans the number of lines from it, then returns the number. If the file could not be opened, it throws an Exception.

The *Callable* interface is similar to the *Runnable* interface, but it allows a return value and can throw an exception. 

**LinesCounterThread class**
This is a Java class that *extends* the Thread class. The purpose of this class is to calculate the number of lines in a text file and add the number to an atomic integer provided in the instance's constructor.
###### The class has three fields:
1.	*fileName:* A string holding the name of the file to read from.
2.	*totalNumOfLines:* An atomic integer holding the total number of lines from all the files.
3.	*numOfThreadFinished:* An atomic integer that is increased by 1 each time a thread finishes its job. It is used to tell when all files have been read.

The class has a single constructor that takes the file name, total number of lines, and number of threads finished as input and initializes the corresponding fields.

###### The class also has a single method:
`run() `: which is the entry point of the thread. This method creates a File object and reads the number of lines from it. It then adds the number of lines to the *totalNumOfLines* atomic integer and increases the *numOfThreadFinished* atomic integer by 1. If the file could not be found, it throws a ***FileNotFoundException***.

### Part2 package
**CustomExecutor class**
The *CustomExecutor* class extends the *ThreadPoolExecutor* class and provides additional features for executing tasks.

###### The CustomExecutor class has the following features:
1.	Tasks are executed from the queue based on their priority, from lowest to highest.
2.	The `submit` method can accept either a *Callable* or a *Task *object.
 If a Callable is provided, it will be wrapped in a Task object with a default priority of "Other" and then submitted.
 If a Task object is provided, it will be submitted as is.
3.	The core pool size of the executor is set to half the number of cores available to the JVM.
4.	The max pool size of the executor is set to one less than the number of cores available to the JVM.
5.	The keep alive time for idle threads is set to 300 milliseconds.
6.	The getMaxPriority method can be used to get the maximum priority task in the queue at any given time.

###### The CustomExecutor class overrides the following methods:
1.	The `submit` method to handle the submission of Callable and Task objects.
If a *Callable task* is received, it is wrapped in a Task object with a default priority and then submitted. If a *Task* object is received, it is submitted as is. 
The method also updates the maximum priority in the queue if the current number of active tasks is greater than or equal to the core pool size.
2.	The `beforeExecute` method which is called before a task is executed. This method removes the priority value of the task being executed from the *maxPriorityHolder* object, as the task has been dequeued.
3.	The `afterExecute` method to update the maximum priority value in the queue after a task has been executed. The method is used to create a *RunnableFuture* object for a given Callable task. This method returns a *CustomFutureTask* object, which is an implementation of the *RunnableFuture* interface that allows the *PriorityBlockingQueue* to compare *CustomFutureTask* objects based on their priority.

**CustomFutureTask<T> class**
The CustomFutureTask class extends the FutureTask class and implements the Comparable interface. It is used to enable the PriorityQueue in the ThreadPool to compare tasks based on their priority.

###### The CustomFutureTask class has the following features:
1.	It stores the priority and task name of the Task object it is wrapping.
2.	It has getters for the priority and task name.
3.	It implements the compareTo method to compare two CustomFutureTask objects based on their priority. The task with the lower priority value is considered to have higher priority.

The* CustomFutureTask* class is used by the *CustomExecutor* class to wrap Task objects and add them to the PriorityQueue in the ThreadPool. The *CustomExecutor *class uses the *CustomFutureTask* objects to determine the order in which to execute tasks based on their priority.

**MaxPriorityHolder class**
The* MaxPriorityHolder* class is used to store the priority values of tasks that are waiting to be executed in a thread pool. It maintains a list of priority values and uses a lock to ensure thread safety.

###### The MaxPriorityHolder class has the following features:
1.	It stores a list of values representing the priorities of tasks in the queue.
2.	It maintains the lowest value in the list, which represents the maximum priority of the tasks in the queue.
3.	It has methods for adding and removing values from the list and updating the lowest value accordingly.
4.	It has a method for getting the current lowest value, which represents the maximum priority of the tasks in the queue.
5.	It is synchronized to ensure thread safety when adding and removing values from the list.

###### The MaxPriorityHolder class has the following methods:
1.	The `addValue` method adds a new priority value to the list and updates the lowest value (highest priority) if necessary.
2.	The` removeValue` method removes a priority value from the list and updates the lowest value if necessary.
3.	The `getLowestValue` method returns the current lowest value (highest priority).

The *MaxPriorityHolder* class is used by the *CustomExecutor* class to maintain the maximum priority of tasks in the queue. The *CustomExecutor* class updates the *MaxPriorityHolder* when tasks are enqueued and dequeued. The *CustomExecutor* class can use the *MaxPriorityHolder* to get the current maximum priority of tasks in the queue using the `getLowestValue` method.

**Task<T> class**
The Task class is a Callable object that can be submitted to the *CustomExecutor* for execution. It is the only type of object that can be submitted to the *CustomExecutor*. The *CustomExecutor* will wrap Callable objects in a Task object before submitting them for execution.

###### The Task class has the following features:
1.	It stores the *TaskType* and Callable object provided at instantiation.
2.	It implements the `call` method to delegate the call to the Callable object's call method.
3.	It has* **factory*** methods for creating Task objects from Callable objects.
 If no *TaskType *is provided, the *TaskType* is set to default (`TaskType.OTHER`).
4.	It has a getter for the *TaskType* of the Task object.
5.	It has a getter for the task name of the Task object.
The *Task* class is used by the *CustomExecutor* to wrap Callable objects and submit them for execution. The *CustomExecutor* uses the *TaskType* of the Task object to determine the priority of the task and the task name to identify the task.

**enum TaskType**
The* TaskType enum* represents the priority of a task.
It has three values:* COMPUTATIONAL*, *IO*, and *OTHER*. 
Each value has a corresponding integer priority value, with a lower value representing a higher priority.

###### The TaskType enum also provides the following method:
1.	`setPriority` method that allows you to set the priority of a *TaskType*.
2.	`getPriorityValue` method that returns the priority value of a *TaskType*.
3.	`validatePriority` method that checks whether a given priority is valid, which is defined as an integer between 1 and 10.

The *TaskType* enum is used by the *Task* class to determine the priority of a task.
When you create a new Task object, you can specify a *TaskType* or use the default `TaskType.OTHER` if you don't want to specify a priority. The priority of the Task is then determined by the priority of its *TaskType.*

## Test
### package tests.ex2_1

**Ex2_1_Test class**
This is a set of JUnit tests for a class called* Ex2_1*. 

###### The tests exercise several functionality of the* Ex2_1* class:
•	Test that each file created has a correct number of lines, less than the bound argument.
•	Tests that the correct number of files is created.
•	Test that the method `getNumOfLines() `returns the correct number of lines in all files.
•	Test that the method` getNumOfLinesThreads()` returns the correct number of lines in all files, where it uses threads for counting the lines.

### package tests.ex2_2
**CustomExecutorTest class**
This is a set of JUnit tests for a class called *CustomExecutor*. 

###### The tests exercise several functionality of the *CustomExecutor* class:
•	Test that the `submit` method works as expected, where it should return a future object that can be used to retrieve the result.
•	Test that tasks are stored in priority queue and are in the correct order according to their task types.
•	Test that the properties of the executor are set correctly.

**CustomFutureTaskTest class**
This is a set of JUnit tests for a class called *CustomFutureTask*. 

###### The tests exercise several functionality of the *CustomFutureTask* class:
•	Test that the `compareTo` method of the class *CustomFutureTask* works as expected and it compares the task types.
•	Test that the `call` method of the class *CustomFutureTask* works as expected and it's executed in the thread pool.

**MaxPriorityHolder class**
This is a set of JUnit tests for a class called *MaxPriorityHolder*. 

###### The tests exercise several functionality of the *MaxPriorityHolder* class:
•	Test that the `addValue` method of the class *MaxPriorityHolder* works as expected and it keeps the priority queue in a valid state.
•	Test that the `removeValue` method of the class *MaxPriorityHolder* works as expected and it keeps the priority queue in a valid state.
•	Test that the `getLowestValue` method of the class *MaxPriorityHolder *works as expected and it returns the lowest value of the priority queue.

**TaskTest class**
This test case is using JUnit to test the functionality of the *Task* class and its related functions.
The test creates two tasks using the `Task.createTask()` method and checks if the `get()` method of the Future objects returned by the *ExecutorService* return the expected result for both tasks. 
It also checks if the values of type and task name are correct and it's checks the functionality of the `Task.createTask()` method, which creates an instance of the *Task* class with a specific callable and *TaskType*, which is passed as an argument.

## Examples of Use  
1.	n=100000, bound =100
![](C:\Users\batel\OneDrive - Ariel University\שנה ב-2\מונחי עצמים\Task2\1.jpeg)
 
2.	n=1000, bound=100000
![](C:\Users\batel\OneDrive - Ariel University\שנה ב-2\מונחי עצמים\Task2\2.jpeg)
 
## Acknowledgements\Bibliography
•	OOP Course 2023 at Ariel University.

## Contact
•	Batelcohen96@gmail.com
•	talsahar11@gmail.com
