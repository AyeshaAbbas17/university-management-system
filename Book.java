//for aggregation in Library

import java.io.Serializable;

public class Book implements Serializable{
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private boolean isIssued;

    //Constructors
    public Book() {
        this("", "", "", "", false);
        
    }
    public Book(String bookId, String title, String author, String genre, boolean issued) {
        setBookId(bookId); 
        setTitle(title);
        setAuthor(author); 
        setGenre(genre);
        setIssued(issued);
    }

    //Setters, Getters, toString()
    public void setBookId(String bookId) {
        if (bookId != null && !bookId.trim().equals("")) {
            this.bookId = bookId;
        } else {
            System.out.println("Invalid Book ID! Please provide a valid unique ID.");
        }
    }
    public void setTitle(String title) {
        if (title != null && !title.trim().equals("")) {
            this.title = title;
        }else {
            System.out.println("Invalid Title!");
            this.title = "Untitled Book";
        }
    }
    public void setAuthor(String author) {
        if (author != null && !author.trim().equals("")) {
            this.author = author;
        } else {
            System.out.println("Invalid Author!");
            this.author = "Unknown Author";
        }
    }
    public void setGenre(String genre) {
        if (genre != null && !genre.trim().equals("")) {
            this.genre = genre;
        }else {
            System.out.println("Invalid Genre!");
            this.genre = "General";
        }
    }
    public void setIssued(boolean issued) {
        this.isIssued = issued;
    }
    public String getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public boolean isIssued() {
        return isIssued;
    }
    @Override
    public String toString() {
        return "Book ID : " + bookId + "\nTitle : " + title + "\nAuthor : " + author + "\nGenre : " + genre + "\nIs it isued ? "+ isIssued;
    }
    
}
