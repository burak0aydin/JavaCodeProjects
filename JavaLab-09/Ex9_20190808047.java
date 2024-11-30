/**
 * Burak Aydın
 * 20190808047
 * 2023-06-11
 */

public class Ex9_20190808047 {
}

interface Common<T> {
    boolean isEmpty();

    T peek();

    int size();
}

interface Stack<T> extends Common<T> {
    boolean push(T item);

    T pop();
}

interface Node<T> {
    int DEFAULT_CAPACITY = 2;

    void setNext(T item);

    T getNext();

    double getPriority();
}

interface PriorityQueue<T> extends Common<T> {
    int FLEET_CAPACITY = 3;

    boolean enqueue(T item);

    T dequeue();
}

interface Package<T> {
    T extract();

    boolean pack(T item);

    boolean isEmpty();

    double getPriority();
}

interface Sellable {
    String getName();

    double getPrice();
}

interface Wrappable extends Sellable {
}

abstract class Product implements Sellable {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + name + ", " + price + ")";
    }
}

class Mirror extends Product {
    private int width;
    private int height;

    public Mirror(int width, int height) {
        super("mirror", 2);
        this.width = width;
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

    public <T> T reflect(T item) {
        System.out.println("Reflecting item: " + item);
        return item;
    }
}

class Paper extends Product implements Wrappable {
    private String note;

    public Paper() {
        super("A4", 3);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class Matroschka<T extends Wrappable> extends Product implements Wrappable, Package<T> {
    private T item;

    public Matroschka(T item) {
        super("Doll", 5 + item.getPrice());
        this.item = item;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + item + "}";
    }

    @Override
    public T extract() {
        T extractedItem = item;
        item = null;
        return extractedItem;
    }

    @Override
    public boolean pack(T item) {
        if (isEmpty()) {
            this.item = item;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return item == null;
    }

    @Override
    public double getPriority() {
        throw new UnsupportedOperationException("Not implemented");
    }
}

class Box<T extends Sellable> implements Package<T> {
    private int distanceToAddress;
    private T item;
    private boolean seal;

    public Box(int distanceToAddress, T item) {
        this.distanceToAddress = distanceToAddress;
        this.item = item;
        this.seal = true;
    }

    public Box() {
        this.distanceToAddress = 0;
        this.item = null;
        this.seal = false;
    }

    @Override
    public T extract() {
        if (item == null) {
            return null;
        }
        T extractedItem = item;
        item = null;
        seal = false;
        return extractedItem;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + item + "} Seal: " + seal;
    }

    public int getDistanceToAddress() {
        return distanceToAddress;
    }

    public T getItem() {
        return item;
    }

    @Override
    public boolean pack(T item) {
        throw new UnsupportedOperationException("Unimplemented method 'pack'");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public double getPriority() {
        throw new UnsupportedOperationException("Unimplemented method 'getPriority'");
    }
}

class Container implements Stack<Box<?>>, Node<Container>, Comparable<Container> {
    private static final int DEFAULT_CAPACITY = 10;

    private Box<?>[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;

    public Container() {
        this.boxes = new Box<?>[DEFAULT_CAPACITY];
        this.top = -1;
        this.size = 0;
        this.priority = 0;
        this.next = null;
    }

    @Override
    public boolean push(Box<?> item) {
        if (isFull()) {
            return false;
        }

        top++;
        boxes[top] = item;
        size++;
        priority += item.getItem().getPrice();
        return true;
    }

    @Override
    public Box<?> pop() {
        if (isEmpty()) {
            return null;
        }

        Box<?> item = boxes[top];
        boxes[top] = null;
        top--;
        size--;
        priority -= item.getItem().getPrice();
        return item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Box<?> peek() {
        if (isEmpty()) {
            return null;
        }

        return boxes[top];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void setNext(Container item) {
        this.next = item;
    }

    @Override
    public Container getNext() {
        return next;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Container other) {
        if (this.priority == other.priority) {
            return Double.compare(this.calculateDistanceSum(), other.calculateDistanceSum());
        }
        return Double.compare(this.priority, other.priority);
    }

    private double calculateDistanceSum() {
        double sum = 0;
        for (int i = 0; i <= top; i++) {
            sum += boxes[i].getDistanceToAddress();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Container with priority: " + priority;
    }

    private boolean isFull() {
        return size == boxes.length;
    }
}

class CargoFleet implements PriorityQueue<Container> {
    private Container head;
    private int size;

    public CargoFleet() {
        this.head = null;
        this.size = 0;
    }

    public boolean enqueue(Container item) {
        if (head == null) {
            head = item;
        } else {
            Container current = head;
            Container previous = null;
            while (current != null && item.compareTo(current) > 0) {
                previous = current;
                current = current.getNext();
            }
            if (previous == null) {
                item.setNext(head);
                head = item;
            } else {
                item.setNext(current);
                previous.setNext(item);
            }
        }
        size++;
        return true;
    }

    public Container dequeue() {
        if (isEmpty()) {
            return null;
        }
        Container item = head;
        head = head.getNext();
        item.setNext(null);
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Container peek() {
        return head;
    }

    public int size() {
        return size;
    }
}

class CargoCompany {
    private Container stack;
    private CargoFleet queue;

    public CargoCompany() {
        this.stack = null;
        this.queue = new CargoFleet();
    }

    public void add(Box<?> box) {
        if (stack == null || !stack.push(box)) {
            if (queue.enqueue(stack)) {
                stack = new Container();
                add(box);
            } else {
                ship(queue);
            }
        }
    }

    private void ship(CargoFleet fleet) {
        while (!fleet.isEmpty()) {
            Container container = fleet.dequeue();
            empty(container);
        }
    }

    private void empty(Container container) {
        while (!container.isEmpty()) {
            Box<?> box = container.pop();
            System.out.println(deliver(box));
        }
    }

    private Sellable deliver(Box<?> box) {
        return box.extract();
    }
}
