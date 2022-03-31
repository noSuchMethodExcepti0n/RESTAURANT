package js.RESTaurant.server.model;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Yolo
 */
@Data
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "OrderItem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="orderItemID")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long orderItemID;
    
    @Column(length = 255)
    private String quantity;
    

    @JsonBackReference(value = "orderitem_order")
    @JoinColumn(name = "\"order\"", referencedColumnName = "orderID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Order order;
     
    
    //@JsonManagedReference(value = "item_orderitem")
    @JoinColumn(name = "item", referencedColumnName = "itemID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Item item;
    
    
    @JsonBackReference(value = "user_orderitem")
    @JoinColumn(name = "user", referencedColumnName = "userID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;


	public OrderItem(String quantity, Item item) {
		this.quantity = quantity;
		this.item = item;
	}
    
}
