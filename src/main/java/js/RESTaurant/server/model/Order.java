package js.RESTaurant.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import js.RESTaurant.server.enums.OrderStatus;
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
@Table(name="\"Order\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="orderID")
@EntityListeners(AuditingEntityListener.class)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long orderID;
    
    @Column(length = 255)
    private String number;
    
    @Column
    private String comments;
    
    @Column
    private String adress;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    //@JsonManagedReference(value = "ordertype_order")
    @JoinColumn(name = "orderType", referencedColumnName = "orderTypeID")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderType orderType;
    
    //@JsonManagedReference(value = "user_order")
    @JoinColumn(name = "user", referencedColumnName = "userID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    //@JsonManagedReference(value = "payment_order")
    @JoinColumn(name = "payment", referencedColumnName = "paymentID")
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @JsonManagedReference(value = "orderitem_order")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
