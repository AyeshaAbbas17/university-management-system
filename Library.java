import java.util.ArrayList;
import java.io.*;

public class Library extends Facility implements Reportable, Serializable {
    //book aggreagation
    protected ArrayList<Book> books;
    protected int dailyVisitors;
    

    // Constructors
    public Library() {
        this(0, "", "", 0.0,false,  0, new ArrayList<>());
        new ArrayList<>();
    }

    public Library(int entityId, String name, String location, double cost, boolean isOpen,int dailyVisitors, ArrayList<Book> books ) {
        super(entityId, name, location, cost,isOpen);
       setVisitors(dailyVisitors);
       setBooks(books);
        
    }

    // Essential Method
    @Override
    public double calculateOperationalCost() {
        if (!isOpen) {
        return maintenanceCost * 0.5;
        }
        return maintenanceCost+ (books.size() * 10)+ (dailyVisitors * 20);
    }
    
    // @Override interface's method
    public void generateReport() {
        System.out.println("Library usage statistics generated.");
        System.out.println("Total books in library : "+books.size());
        displayBooks();
    }

    //Book Management methods
    public void addBook(Book b) {
        if (b != null) {
            books.add(b);
        }
    }

    public void removeBook(Book b) {
        books.remove(b);
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No Books Available.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }


    // Setters, Getters, toString
    public void setBooks(ArrayList<Book> books) {
        if (books != null) {
            ArrayList<Book> temp = new ArrayList<>(books);
            this.books = temp;
        } else {
            System.out.println("Invalid Books List! Creating empty list.");
            this.books = new ArrayList<>();
        }
    }

    public void setVisitors(int dailyVisitors) {
        if (dailyVisitors >= 0) {
            this.dailyVisitors = dailyVisitors;
        } else {
            System.out.println("Invalid Visitor Count!");
            System.out.println("Setting to 0.");
            this.dailyVisitors = 0;
        }
    }

    public ArrayList <Book> getBooks() {
        return new ArrayList<Book>(books);
    }

    public int getDailyVisitors() {
        return dailyVisitors;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDaily Visitors : " + dailyVisitors + "\n Library is open: " + isOpen;

    }

} // end of library