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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author 13250
 */
@Entity
@Table(name = "good")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Good.findAll", query = "SELECT g FROM Good g")
    , @NamedQuery(name = "Good.findByGoodId", query = "SELECT g FROM Good g WHERE g.goodId = :goodId")
    , @NamedQuery(name = "Good.findByGoodName", query = "SELECT g FROM Good g WHERE g.goodName = :goodName")
    , @NamedQuery(name = "Good.findByGoodCate", query = "SELECT g FROM Good g WHERE g.goodCate = :goodCate")
    , @NamedQuery(name = "Good.findByGoodPrice", query = "SELECT g FROM Good g WHERE g.goodPrice = :goodPrice")
    , @NamedQuery(name = "Good.findByGoodPath", query = "SELECT g FROM Good g WHERE g.goodPath = :goodPath")
    , @NamedQuery(name = "Good.findByGoodDescribe", query = "SELECT g FROM Good g WHERE g.goodDescribe = :goodDescribe")
    , @NamedQuery(name = "Good.findByGoodStock", query = "SELECT g FROM Good g WHERE g.goodStock = :goodStock")
 , @NamedQuery(name = "Good.findBystorestoreid", query="SELECT g FROM Good g WHERE g.storestoreid.storeId = :storeid")})
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "good_id")
    private Integer goodId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "good_name")
    private String goodName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "good_cate")
    private String goodCate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "good_price")
    private int goodPrice;
    @Size(max = 45)
    @Column(name = "good_path")
    private String goodPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "good_describe")
    private String goodDescribe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "good_stock")
    private int goodStock;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goodgoodid")
    private Collection<Record> recordCollection;
    @JoinColumn(name = "Store_store_id", referencedColumnName = "store_id")
    @ManyToOne(optional = false)
    private Store storestoreid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goodgoodid")
    private Collection<Cart> cartCollection;

    public Good() {
    }

    public Good(Integer goodId) {
        this.goodId = goodId;
    }

    public Good(Integer goodId, String goodName, String goodCate, int goodPrice, String goodDescribe, int goodStock) {
        this.goodId = goodId;
        this.goodName = goodName;
        this.goodCate = goodCate;
        this.goodPrice = goodPrice;
        this.goodDescribe = goodDescribe;
        this.goodStock = goodStock;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodCate() {
        return goodCate;
    }

    public void setGoodCate(String goodCate) {
        this.goodCate = goodCate;
    }

    public int getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(int goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodPath() {
        return goodPath;
    }

    public void setGoodPath(String goodPath) {
        this.goodPath = goodPath;
    }

    public String getGoodDescribe() {
        return goodDescribe;
    }

    public void setGoodDescribe(String goodDescribe) {
        this.goodDescribe = goodDescribe;
    }

    public int getGoodStock() {
        return goodStock;
    }

    public void setGoodStock(int goodStock) {
        this.goodStock = goodStock;
    }

    @XmlTransient
    public Collection<Record> getRecordCollection() {
        return recordCollection;
    }

    public void setRecordCollection(Collection<Record> recordCollection) {
        this.recordCollection = recordCollection;
    }

    public Store getStorestoreid() {
        return storestoreid;
    }

    public void setStorestoreid(Store storestoreid) {
        this.storestoreid = storestoreid;
    }

    @XmlTransient
    public Collection<Cart> getCartCollection() {
        return cartCollection;
    }

    public void setCartCollection(Collection<Cart> cartCollection) {
        this.cartCollection = cartCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (goodId != null ? goodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Good)) {
            return false;
        }
        Good other = (Good) object;
        if ((this.goodId == null && other.goodId != null) || (this.goodId != null && !this.goodId.equals(other.goodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Good[ goodId=" + goodId + " ]";
    }
    
}
