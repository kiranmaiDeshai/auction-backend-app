package com.wellsfargo.training.test.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductCategory {
	PAINTING, SCULPTOR, ORNAMENT;

	@JsonValue
	public String getName() {
		return this.name();
	}

	@JsonCreator
	public static ProductCategory getCategory(String categoryText) {
		ProductCategory category = null;
		for (ProductCategory cat : values()) {
			if (cat.name().equalsIgnoreCase(categoryText)) {
				category = cat;
				break;
			}
		}
		return category;
	}

}
