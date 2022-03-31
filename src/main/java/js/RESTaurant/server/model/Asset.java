package js.RESTaurant.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Yolo
 */
//@Data
@Getter @Setter @ToString
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)
@Table(name="Asset")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="assetID", scope=Asset.class)
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long assetID;
    
    @Column(length = 255)
    private String type;
    
    @Column(length = 255)
    private String filename;
    
    @Column(length = 255)
    private String path;
    
    @Column(length = 255)
    private String extension;
    
    @JsonBackReference(value = "asset_item")
    @ManyToMany(mappedBy = "assetList", fetch = FetchType.LAZY)
    private Set<Item> itemList;
    
}