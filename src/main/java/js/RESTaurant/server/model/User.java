package js.RESTaurant.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Table(name="User")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="userID")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long userID;
    
    @Column(length = 255, nullable = false)
    private String username;
    
    @Column(length = 255, nullable = false)
    private String password;
    
    @Column(length = 255)
    private String firstname;
    
    @Column(length = 255)
    private String lastname;
    
    @Column(length = 255)
    private String email;
    
    @Column(length = 255)
    private String phone;
    
    @Column(length = 255)
    private String street;
    
    @Column(length = 255)
    private String propertyNumber;
    
    @Column(length = 255)
    private String numberOfPremises;
    
    @Column(length = 255)
    private String city;
    
    @Column(length = 255)
    private String postcode;
    
    @Column(length = 255)
    private String postcity;
    
    private Boolean active;
    
    private Boolean locked;
    
    @JsonBackReference(value = "role_user")
    @JoinColumn(name = "role", referencedColumnName = "roleID")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Role role;
    
    @JsonBackReference(value = "language_user")
    @JoinColumn(name = "language", referencedColumnName = "languageID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Language language;
   
    @JsonBackReference(value = "user_order")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orderList;
    
    @JsonManagedReference(value = "user_orderitem")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;
}