
/**
 * Burak Aydın
 * 20190808047
 * 2023-04-28
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Ex6_20190808047 {

}

abstract class Product implements Comparable<Product> {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int compareTo(Product other) {
        return Double.compare(price, other.price);
    }

    public String toString() {
        return getClass().getSimpleName() + " [name=" + name + ", price=" + price + "]";
    }
}

abstract class Book extends Product {

    private String author;
    private int pageCount;

    public Book(String name, double price, String author, int pageCount) {
        super(name, price);
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}

class ReadingBook extends Book {

    private String genre;

    public ReadingBook(String name, double price, String author, int pageCount, String genre) {
        super(name, price, author, pageCount);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

class ColoringBook extends Book implements Colorable {
    private String color;

    public ColoringBook(String name, double price, String author, int pageCount) {
        super(name, price, author, pageCount);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void paint(String color) {
        setColor(color);
    }

    @Override
    public String toString() {
        return "ColoringBook [name=" + getName() + ", price=" + getPrice() + ", author=" + getAuthor() +
                ", pageCount=" + getPageCount() + ", color=" + color + "]";
    }

}

class ToyHorse extends Product implements Rideable {

    public ToyHorse(String name, double price) {
        super(name, price);
    }

    @Override
    public void ride() {
        System.out.println("Riding the toy horse!");
    }
}

class Bicycle extends Product implements Colorable, Rideable {
    private String color;

    public Bicycle(String name, double price, String color) {
        super(name, price);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void paint(String color) {
        this.color = color;
    }

    @Override
    public void ride() {
        System.out.println("Riding the bicycle!");
    }

    @Override
    public String toString() {
        return "Bicycle [name=" + getName() + ", price=" + getPrice() + ", color=" + color + "]";
    }
}

class User {
    private String username;
    private String email;
    private PaymentMethod payment;
    private List<Product> cart;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.payment = null;
        this.cart = new ArrayList<Product>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }

    public Product getProduct(int index) {
        return this.cart.get(index);
    }

    public void removeProduct(int index) {
        this.cart.remove(index);
    }

    public void addProduct(Product product) {
        this.cart.add(product);
    }

    public void purchase() {
        double totalPrice = 0.0;
        for (Product product : this.cart) {
            totalPrice += product.getPrice();
        }
        if (this.payment != null && this.payment.pay(totalPrice)) {
            this.cart.clear();
        }
    }
}

class CreditCard implements PaymentMethod {
    private long cardNumber;
    private String cardHolderName;
    private Date expirationDate;
    private int cvv;

    public CreditCard(long cardNumber, String cardHolderName, Date expirationDate, int cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        return true;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}

class PayPal implements PaymentMethod {
    private String username;
    private Password password;

    public PayPal(String username, String password) {
        this.username = username;
        this.password = new Password(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean pay(double amount) {
        return true;
    }

    public boolean validatePassword(String password) {
        return this.password.isValid(password);
    }
}

class Password {
    private String hash;

    public Password(String password) {
        this.hash = hashPassword(password);
    }

    public boolean isValid(String password) {
        return hash.equals(hashPassword(password));
    }

    private String hashPassword(String password) {
        return password;
    }
}

interface Colorable {
    void paint(String color);
}

interface Rideable {
    void ride();
}

interface PaymentMethod {
    boolean pay(double amount);
}