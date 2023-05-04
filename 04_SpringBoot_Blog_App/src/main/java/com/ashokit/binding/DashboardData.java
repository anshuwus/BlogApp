package com.ashokit.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DashboardData {
	
	private String title;
	private String description;
	private LocalDate createdOn;

}
