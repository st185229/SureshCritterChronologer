package com.udacity.jdnd.course3.critter.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED)
@Table(name = "USER")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID")
    private Long id;

    public User(Long id, String name, Date createdOn) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
    }

    @Column(name = "CREATED_ON")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "USER_NAME")
    @Basic(optional = false)
    private String name;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private Date createdOn;

    public User() {
        id=null;
        name = "";
    }
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
