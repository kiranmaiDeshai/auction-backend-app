package com.wellsfargo.training.test.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(of = { "id" })
public class ProductDto {

	private Long id;
	
	@NotBlank
	@Length(min = 5, max = 30)
	private String name;

	private String shortDescription;

	private String detailedDescription;

	@NotNull
	private ProductCategory category;

	@NotNull
	private Double startPrice;

	@NotBlank
	@Length(min = 5, max = 30)
	private String firstName;

	@NotBlank
	@Length(min = 3, max = 30)
	private String lastName;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Future
	private LocalDate bidEndDate;

	private String address;

	private String city;

	private String state;

	private String pin;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Min(value = 1000000000L)
	@Max(value = 9999999999L)
	private Long phone;

}
