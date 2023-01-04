package part2;

/**
 * TaskType is an enum of the available types of a task.
 * The types are:
 *      1. Computational task.
 *      2. IO task.
 *      3. Other task.
 *  The priority of the taskTypes is represented by an integer, as the lowest integer value (1), is the most prioritized
 *  taskType, and the highest integer value (3), is the lease prioritized taskType.
 */
public enum TaskType {

    COMPUTATIONAL(1){
        @Override
        public String toString(){return "Computational CustomCallable";}
    },
    IO(2){
        @Override
        public String toString(){return "IO-Bound CustomCallable";}
    },
    OTHER(3){
        @Override
        public String toString(){return "Unknown CustomCallable" ;}
    };

    private int typePriority;

    /**
     * Constructor for the enum types.
     * @param priority - the priority integer value of the type.
     */
    private TaskType(int priority){
        if (validatePriority(priority)) typePriority = priority;
        else
            throw new IllegalArgumentException("Priority is not an integer") ;
    }

    /**
     * Setter to the priority value.
     * @param priority - integer value represents the wanted priority.
     */
    public void setPriority(int priority){
        if(validatePriority(priority)) this.typePriority = priority;
        else
            throw new IllegalArgumentException("Priority is not an integer");
    }

    /**
     * Getter for the priority value.
     * @return an integer represents the priority value.
     */
    public int getPriorityValue(){
        return typePriority;
    }

    /**
     * Getter for the TaskType.
     * @return the instance TaskType.
     */
    public TaskType getType(){
        return this;
    }

    /**
     * Ensures that a given priority is a valid priority - an integer between 1 - 10.
     * @param priority - the priority to validate.
     * @return True - if priority is valid,
     *         False - if priority is not valid.
     */
    private static boolean validatePriority(int priority){
        if (priority < 1 || priority >10) return false;
        return true;
    }
}
