/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dima
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findByLieferadresseStr", query = "SELECT c FROM Customer c WHERE c.lieferadresseStr = :lieferadresseStr"),
    @NamedQuery(name = "Customer.findByLieferadresseStadt", query = "SELECT c FROM Customer c WHERE c.lieferadresseStadt = :lieferadresseStadt"),
    @NamedQuery(name = "Customer.findByRechnungsadresseStr", query = "SELECT c FROM Customer c WHERE c.rechnungsadresseStr = :rechnungsadresseStr"),
    @NamedQuery(name = "Customer.findByRechnungsadresseStadt", query = "SELECT c FROM Customer c WHERE c.rechnungsadresseStadt = :rechnungsadresseStadt")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lieferadresse_str")
    private String lieferadresseStr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "lieferadresse_stadt")
    private String lieferadresseStadt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rechnungsadresse_str")
    private String rechnungsadresseStr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rechnungsadresse_stadt")
    private String rechnungsadresseStadt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<CustomerOrder> customerOrderCollection;

    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
    }

    public Customer(Integer id, String name, String email, String lieferadresseStr, String lieferadresseStadt, String rechnungsadresseStr, String rechnungsadresseStadt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.lieferadresseStr = lieferadresseStr;
        this.lieferadresseStadt = lieferadresseStadt;
        this.rechnungsadresseStr = rechnungsadresseStr;
        this.rechnungsadresseStadt = rechnungsadresseStadt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLieferadresseStr() {
        return lieferadresseStr;
    }

    public void setLieferadresseStr(String lieferadresseStr) {
        this.lieferadresseStr = lieferadresseStr;
    }

    public String getLieferadresseStadt() {
        return lieferadresseStadt;
    }

    public void setLieferadresseStadt(String lieferadresseStadt) {
        this.lieferadresseStadt = lieferadresseStadt;
    }

    public String getRechnungsadresseStr() {
        return rechnungsadresseStr;
    }

    public void setRechnungsadresseStr(String rechnungsadresseStr) {
        this.rechnungsadresseStr = rechnungsadresseStr;
    }

    public String getRechnungsadresseStadt() {
        return rechnungsadresseStadt;
    }

    public void setRechnungsadresseStadt(String rechnungsadresseStadt) {
        this.rechnungsadresseStadt = rechnungsadresseStadt;
    }

    @XmlTransient
    public Collection<CustomerOrder> getCustomerOrderCollection() {
        return customerOrderCollection;
    }

    public void setCustomerOrderCollection(Collection<CustomerOrder> customerOrderCollection) {
        this.customerOrderCollection = customerOrderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + id + " ]";
    }

    public void setPasswort(String passwort) {
        this.password = passwort;
    }

    public String getPasswort() {
        return password;
    }
    
    
    
}
