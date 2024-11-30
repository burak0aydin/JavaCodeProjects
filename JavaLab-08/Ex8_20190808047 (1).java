/**
 * Burak Aydın
 * 20190808047
 * 2023-06-02
 */
public class Ex8_20190808047 {
    public static void main(String[] args) {
    }

    public interface Sellable {
        String getName();

        double getPrice();
    }

    public interface Package<T> {
        T extract();

        boolean pack(T item);

        boolean isEmpty();
    }

    public interface Wrappable extends Sellable {
    }

    public abstract class Product implements Sellable {
        private String name;
        private double price;

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

    public class Mirror extends Product {
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
            System.out.println("Reflected item: " + item);
            return item;
        }
    }

    public class Paper extends Product implements Wrappable {
        private String note;

        public Paper(String note) {
            super("A4", 3);
            this.note = note;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

    public class Matroschka<T extends Wrappable> extends Product implements Wrappable, Package<T> {
        private T item;

        public Matroschka(T item) {
            super("Doll", 5 + item.getPrice());
            this.item = item;
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
        public String toString() {
            return super.toString() + "{" + item + "}";
        }
    }

    public class Box<T extends Sellable> implements Package<T> {
        private T item;
        private boolean seal;

        public Box() {
            this.item = null;
            this.seal = false;
        }

        public Box(T item) {
            this.item = item;
            this.seal = true;
        }

        @Override
        public T extract() {
            T extractedItem = item;
            item = null;
            seal = false;
            return extractedItem;
        }

        @Override
        public boolean pack(T item) {
            if (isEmpty()) {
                this.item = item;
                seal = true;
                return true;
            }
            return false;
        }

        @Override
        public boolean isEmpty() {
            return item == null;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " {" + item + "} Seal: " + seal;
        }
    }
}
