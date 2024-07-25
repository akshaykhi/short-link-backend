package com.example.Hackathon.Hackathon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "golink_details")
@NoArgsConstructor
@AllArgsConstructor
public class GoLinkDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "alias")
    private String alias;
    @Column(name = "destination_url")
    private String destinationUrl;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "url_type")
    private String urlType;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "updated_date")
    private Timestamp updatedDate;
}
