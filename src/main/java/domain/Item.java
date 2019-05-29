package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

    @XmlRootElement
    @Entity
    @NamedQueries({
            @NamedQuery(name="item.all", query="SELECT p FROM Item p"),
            @NamedQuery(name="item.id", query="SELECT p FROM Item p WHERE p.id=:itemId"),
            @NamedQuery(name="item.id.byPrice", query="SELECT p FROM Item p WHERE p.price>=:min AND p.price<=:max"),
            @NamedQuery(name="item.id.byCategory", query="SELECT p FROM Item p WHERE p.category=:cat"),
            @NamedQuery(name="item.id.byName", query="SELECT p FROM Item p WHERE p.name LIKE :name"),
            @NamedQuery(name="item.id.comments", query="SELECT c FROM Comment c LEFT OUTER JOIN Item p ON c.item.id=:itemId"),
            @NamedQuery(name="item.id.comment.cid", query="SELECT c FROM Comment c WHERE c.id=:commentId and c.item.id=:itemId")
    })
    public class Item {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private Category category;
        private Double price;
        private String name;
        private List<Comment> comments = new ArrayList<Comment>();
        public enum Category {
            GRAPHICSCARD,
            MOTHERBOARD,
            HARDDISK,
            RAM,
        }

    public int getId() {
        return id;
    }
   
    public void setId(int id) {
        this.id = id;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    @OneToMany(mappedBy="item")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
