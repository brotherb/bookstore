package foo.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "document", catalog = "wenku")
public class Document {
    private int id;
    private String name;
    private String path;
    private int price;
    private Date upload_time;

    @Override
    public boolean equals(Object obj) {
        if(obj == null)                return false;
        if(!(obj instanceof Document)) return false;

        Document other = (Document) obj;
        return this.getId() == other.getId();
    }

    @Column(name = "upload_time", nullable = false)
    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    private String description;

    private User author;

    public Document(){

    }
    public Document(String name, String path, int price, String description, User author) {
        this.name = name;
        this.path = path;
        this.price = price;
        this.description = description;
        this.author = author;
        this.upload_time = new Date();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id", nullable = false)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "path", nullable = false, length = 128)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Column(name = "description", nullable = false, length = 600)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
