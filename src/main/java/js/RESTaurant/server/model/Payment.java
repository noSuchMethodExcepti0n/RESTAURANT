package js.RESTaurant.server.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import js.RESTaurant.server.enums.PaymentStatus;
import js.RESTaurant.server.enums.PaymentType;
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
@Table(name="Payment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="paymentID")
@EntityListeners(AuditingEntityListener.class)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long paymentID;
    
    @Column(length = 255)
    private String value;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
   
    @JsonBackReference(value = "payment_order")
    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private Order order;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
