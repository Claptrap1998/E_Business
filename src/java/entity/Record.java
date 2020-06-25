/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 13250
 */
@Entity
@Table(name = "record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Record.findAll", query = "SELECT r FROM Record r")
    , @NamedQuery(name = "Record.findByRecordId", query = "SELECT r FROM Record r WHERE r.recordId = :recordId")
    , @NamedQuery(name = "Record.findByRecordTime", query = "SELECT r FROM Record r WHERE r.recordTime = :recordTime")
    , @NamedQuery(name = "Record.findByRecordAmount", query = "SELECT r FROM Record r WHERE r.recordAmount = :recordAmount")
    , @NamedQuery(name = "Record.findByRecordTotalprice", query = "SELECT r FROM Record r WHERE r.recordTotalprice = :recordTotalprice")})
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_id")
    private Integer recordId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_amount")
    private int recordAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_totalprice")
    private int recordTotalprice;
    @JoinColumn(name = "Good_good_id", referencedColumnName = "good_id")
    @ManyToOne(optional = false)
    private Good goodgoodid;
    @JoinColumn(name = "User_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User useruserid;

    public Record() {
    }

    public Record(Integer recordId) {
        this.recordId = recordId;
    }

    public Record(Integer recordId, Date recordTime, int recordAmount, int recordTotalprice) {
        this.recordId = recordId;
        this.recordTime = recordTime;
        this.recordAmount = recordAmount;
        this.recordTotalprice = recordTotalprice;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getRecordAmount() {
        return recordAmount;
    }

    public void setRecordAmount(int recordAmount) {
        this.recordAmount = recordAmount;
    }

    public int getRecordTotalprice() {
        return recordTotalprice;
    }

    public void setRecordTotalprice(int recordTotalprice) {
        this.recordTotalprice = recordTotalprice;
    }

    public Good getGoodgoodid() {
        return goodgoodid;
    }

    public void setGoodgoodid(Good goodgoodid) {
        this.goodgoodid = goodgoodid;
    }

    public User getUseruserid() {
        return useruserid;
    }

    public void setUseruserid(User useruserid) {
        this.useruserid = useruserid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Record)) {
            return false;
        }
        Record other = (Record) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Record[ recordId=" + recordId + " ]";
    }
    
}
