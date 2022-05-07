package com.wellsfargo.training.test.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(of = { "id" })
public class BidDto {

	private Long id;

	@NotNull
	private Long productId;

	private Double amount;

	@NotBlank
	@Length(min = 5, max = 30)
	private String firstName;

	@NotBlank
	@Length(min = 3, max = 30)
	private String lastName;

	private String address;

	private String city;

	private String state;

	private String pin;

	@NotNull
	@Min(value = 1000000000L)
	@Max(value = 9999999999L)
	private Long phone;

	@NotNull
	@Email
	private String email;

}
