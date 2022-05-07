package com.wellsfargo.training.test.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wellsfargo.training.test.dto.ProductCategory;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "short_desc")
	private String shortDescription;

	@Column(name = "start_price")
	private double startPrice;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "detailed_desc")
	private String detailedDescription;

	@Column(name = "category")
	private ProductCategory category;

	@Column(name = "bid_end_date")
	private LocalDate bidEndDate;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "pin")
	private String pin;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private Long phone;

}