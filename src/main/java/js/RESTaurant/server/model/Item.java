package js.RESTaurant.server.model;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Yolo
 */
@Data
//@Getter @Setter @ToString
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)
@Table(name="Item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="itemID")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long itemID;
    
    @Column(length = 255)
    private String name;
    
    @Column(length = 255)
    private String type;
    
    @Column(length = 255)
    private String description;
    
    @Column(length = 255)
    private String value;

    private Boolean active;
    
    //@JsonManagedReference(value = "asset_item")
    @JoinTable(name = "item_asset", joinColumns = {
            @JoinColumn(name = "itemID", referencedColumnName = "itemID", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "assetID", referencedColumnName = "assetID", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Asset> assetList;
    
    @JsonBackReference(value = "item_orderitem")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item", fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;

}