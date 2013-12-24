package foo.model;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;

@Entity
@Table(name = "user", catalog = "wenku", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private int points;
    private byte status;

    private Set<Document> documents = new HashSet<Document>(0);

    public User(){

    }
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.points = 10;
        this.status = 1;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    @Column(name = "name", nullable = false, unique = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true, length = 32)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "points", nullable = false)
    public int getPoints() {
        return points;
    }

    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }
}
