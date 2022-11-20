package com.mvplevel.dealservice.model;

import com.mvplevel.conveyorservice.constants.enums.EmploymentStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.mvplevel.conveyorservice.constants.enums.Gender;
import com.mvplevel.conveyorservice.constants.enums.MaritalStatus;
import com.mvplevel.conveyorservice.constants.enums.PositionStatus;
import com.mvplevel.conveyorservice.dto.EmploymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_number")
    private Integer dependentNumber;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "issue_branch")
    private String issueBranch;

    @Column(name = "employment_dto")
    @Type(type = "jsonb")
    private EmploymentDTO employment;

    @Column(name = "employer")
    private String employer;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "position")
    private PositionStatus position;

    @Column(name = "work_experience_total")
    private Integer workExperienceTotal;

    @Column(name = "work_experience_current")
    private Integer workExperienceCurrent;

    @Column(name = "account")
    private String account;
}
