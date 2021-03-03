package com.backend.feature.maintenance.order.entity;

import com.backend.comment.entity.UUIDPersistence;
import com.backend.feature.maintenance.picture.entity.Picture;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orderComment")
public class OrderComment extends UUIDPersistence {

    @Column(name = "rate")
    private int rate;

    @Column(name = "COMMENT")
    private String comment;

    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ORDER_COMMENT_ID", referencedColumnName = "ID")
    private List<Picture> pictures = new ArrayList<>();
}
