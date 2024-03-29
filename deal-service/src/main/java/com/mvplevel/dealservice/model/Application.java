package com.mvplevel.dealservice.model;

import com.mvplevel.conveyorservice.dto.LoanOfferDTO;
import com.mvplevel.dealservice.constants.ApplicationStatus;
import com.mvplevel.dealservice.dto.ApplicationStatusHistoryDTO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "application")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Application {
    @Id
    @SequenceGenerator(name = "application_sequence", sequenceName = "application_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_sequence")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "id")
    private Credit credit;

    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "applied_offer", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "ses_code")
    private Integer sesCode;

    @Column(name = "status_history", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<ApplicationStatusHistoryDTO> statusHistory;

}
