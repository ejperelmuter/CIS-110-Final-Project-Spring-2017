/* Name: Ethan Perelmuter
 * PennKey: peethan
 * Recitation: 211
 *
 * Class Function: Creates a generic LinkedList to be implemented in my game
 * 
 * Future use: Will store separate LinkedLists, one of bricks and one
 *             of balls in my game
 * 
 *  
 */
public class LinkedList<T> implements List<T> {
    
    private int counter; //Holds the current size of the list
    private Node head; //first Node in the linked list
    
    //Contructor for LinkedList
    public LinkedList() {
        this.counter = 0;
        this.head = null;
    }
    
    
    public static void main(String[] args) {
        // Declare and initialize with String as <T>
        LinkedList<String> ethan = new LinkedList<String>();
        
        System.out.println("List is empty: " + ethan.isEmpty());
        ethan.add(0, "a string");
        //System.out.println(ethan.size());
        //ethan.add("b string");
        //ethan.add(0, "0 string");
        ethan.printList();
        
    }
    
    /**
     * Helper method
     * Function: Prints the list and statistics about it
     * Input: None
     * Output: None
     */
    private void printList() {
        System.out.println("Size = " + size());
        System.out.println("Head = " + head.element);
        System.out.println();
        System.out.println("List:");
        for (int i = 0; i < size(); i++) {
            System.out.println("Element " + i + " = " + get(i));
        }
        System.out.println();
    }
        

    //Implements Node inner class and constructor; holds element of object T
    private class Node {
        private Node next;
        private T element;
        
        // Node Constructor; creates a Node containing Value val
        public Node(T element) { 
            next = null;
            this.element = element;
        }
        
        // Creates Node containing Value val, with n as next Node in the list
        public Node(Node n, T element) {
            this.next = n;
            this.element = element;
        }
    }
    
    
    /**
     * Function: Adds the object x to the end of the list.
     * Input: T x the element to be added to this list
     * Output: returns true
     */
    public boolean add(T x) {
        //If the list is empty, insert this data as a node at head
        if (head == null) {
            head = new Node(x);
            counter++; //Increments number of current objects in list
        }
        else {
            Node insertLast = new Node(x);
            Node tempHolder = head;
            
            //Iterates through the list to the last node
            while (tempHolder.next != null) { 
                tempHolder = tempHolder.next;
            }
            
            //Sets the next of last node to the newly inserted node
            tempHolder.next = insertLast;
            counter++;
        }
        
        return true;
    }
    
    /**
     * Function: Adds the object x at the specified position
     * Input: int index = the position to add the element
     *        T x = the element to be added to the list
     * Output: true if the operation succeeded, false otherwise
     */
    public boolean add(int index, T x) {
        //Throws error if index is longer than the currentlength of list
        if (index > size()) {
            throw new IllegalArgumentException("Index is longer than total " + 
                                                   "number of elements");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index must be >0");
        }
        
        counter++; //Increments number of current objects in list
        //Special case if the list is empty
        if (head == null) {
            add(x);
        }
        //Special case if adding at front of list
        if (index == 0) {
            Node insertHead = new Node(x);
            insertHead.next = head;
            head = insertHead;
            
            boolean success = get(index) == x;
            return success;
        }
        
        
        Node inserted = new Node(x);
        Node tempHolder = head; 
        
        //Iterates through list to given index
        for (int i = 0; i < index - 1; i++) {
            tempHolder = tempHolder.next;
        }
        
        inserted.next = tempHolder.next;
        tempHolder.next = inserted;
        
        boolean success = get(index) == x;
        return success;
    }
    
     /**
     * Function: Returns the number of elements in this list
     * Input: none
     * Output: current number of elements in the list as an int
     */
    public int size() {
        return counter; 
    }
    
    /**
     * Function: Returns the element with the specified position in this list
     * Input: int index = the position of the element to be fetched  
     * Output: the element at the specified position
     */
    public T get(int index) {
        if (index >= size()) {
            throw new IllegalArgumentException("Index is longer than total " + 
                                                   "number of elements");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index must be >0");
        }
        
        Node finder = head;
        for (int i = 0; i < index; i++) {
            finder = finder.next;
        }
        
        return finder.element;
    }
    
    /**
     * Function: Replaces the object at the specified position
     * Input: int index = the position as an int to replace
     *        T x = the new element to be stored
     * Output: the previous value of the element at index as an object T
     */
    public T set(int index, T x) {
        if (index >= size()) {
            throw new IllegalArgumentException("Index is longer than total " + 
                                                   "number of elements");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index must be >0");
        }
        
        Node finder = head;
        for (int i = 0; i < index; i++) {
            finder = finder.next;
        }

        T priorElement = finder.element; //Holds previous element
        finder.element = x; //Replaces object with given object T
        
        return priorElement; 
    }
    
    /** 
     * Function: Removes the object at the specified position
     * Input: int index = the position to remove
     * Output: the object T that was removed 
     */
    public T remove(int index) {
        if (index >= size()) {
            throw new IllegalArgumentException("Index is longer than total " + 
                                                   "number of elements");
        }
        
        counter--; //Deincrements number of current objects in list
        
        //Special case for if the first object is being removed
        if (index == 0) {
            T removed = head.element;
            head = head.next;
            return removed;
        }
        
        Node finder = head;
        for (int i = 0; i < index - 1; i++) {
            finder = finder.next;
        }
        T removed = finder.next.element;
        finder.next = finder.next.next;
        
        return removed;
    }
    
    /**
     * Function: Tests if this list has no elements.
     * Input: None
     * Output: true if list has no elements
     *         false otherwise
     */
    public boolean isEmpty() {
        if (counter == 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Function: Determines if given element is contained in the list
     * Input: T element to be searched for in the list
     * Output: true if the list contains specifiec element
     *         false otherwise
     */
    public boolean contains(T element) {
        //Iterates through list, checking each element
        for (int i = 0; i < size(); i++) {
            if (get(i) == element) {
                return true;
            }
        }
        return false; 
    }
    
    
    /** 
     * Function: Returns the index of the specified element
     * Input: T element to be searched for in the list
     * Output: the index of the element in the list
     *         -1 if the element is not contained in the list
     */
    public int indexOf(T element) {
        //Finds index of element if contained in the list
        for (int i = 0; i < size(); i++) {
            if (get(i) == element) {
                return i;
            }
        }
        return -1;
    }
    
}

