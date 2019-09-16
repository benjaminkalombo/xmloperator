package com.infile.xmloperator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String type;
    private String accountid;
    private double amount;
    private String firstname;
    private String lastname;
    private String demographic;

   public  Account(String type, String accountid, double amount, String firstname, String lastname, String demographic){
        this.accountid=accountid;
        this.amount=amount;
        this.type=type;
        this.firstname=firstname;
        this.lastname=lastname;
        this.demographic=demographic;
    }

    public Account(){}

}
