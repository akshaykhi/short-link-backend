package com.example.Hackathon.Hackathon.entity;

import com.example.Hackathon.Hackathon.enums.UrlType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "golink_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoLinkDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "alias")
    private String alias;

    @Column(name = "destination_url")
    private String destinationUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "url_type")
    private UrlType urlType;

    @Column(name = "created_date")
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Timestamp updatedDate;
}
