package foo.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;
import java.security.Timestamp;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order", catalog = "wenku")

public class Order {
    private int id;
    private Time time;
    private byte status;
    private int totalPrice;
    private byte docCount;

    private Set<Document> documents = new HashSet<Document>(0);

    public Order(){

    }
    public Order(int id, Time time, byte status, int totalPrice, byte docCount, Set<Document> documents) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.totalPrice = totalPrice;
        this.docCount = docCount;
        this.documents = documents;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "order_doc", catalog = "wenku",
            joinColumns = {
                    @JoinColumn(name = "order_id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "doc_id", nullable = false, updatable = false)
            })
    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
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

    @Column(name = "time", nullable = false)
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Column(name = "total_price", nullable = false)
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "doc_count", nullable = false)
    public byte getDocCount() {
        return docCount;
    }

    public void setDocCount(byte docCount) {
        this.docCount = docCount;
    }
}
