package com.example.identityservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business")
public class Business {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "employer_name")
    private String employerName;
	@Column(name = "business_name")
    private String businessName;
	@Column(name = "business_phone")
    private String businessPhone;
	@Column(name = "business_industry")
    private String businessIndustry;
	@Column(name = "business_location")
    private String businessLocation;
	@Column(name = "month_salary_type")
    private String monthSalaryType;
	@Column(name = "work_hours")
    private String workHours;
	@CreationTimestamp
	@Column(updatable = false)
	private Instant created;
	@UpdateTimestamp
	private Instant updated;

}
