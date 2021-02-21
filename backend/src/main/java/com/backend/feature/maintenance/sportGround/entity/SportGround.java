package com.backend.feature.maintenance.sportGround.entity;

import com.backend.comment.entity.UUIDPersistence;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sportGround")
public class SportGround extends UUIDPersistence {

    @Column(name = "NAME", length = 128, nullable = false)
    private String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DETAILED_ADDRESS")
    private String detailedAddress;

    @Column(name = "WEEKS")
    private String weeks;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "TIME_AREA")
    private String timeArea;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AREA_ID")
    private List<Area> areas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "MERCHANT_ID")
    private Merchant merchant;

    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "SPORT_GROUND_ID", referencedColumnName = "ID")
    private List<Picture> pictures = new ArrayList<>();
}
