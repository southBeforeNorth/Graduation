package com.backend.feature.maintenance.user.entity;

import com.backend.comment.entity.UUIDPersistence;
import com.backend.feature.maintenance.picture.entity.Picture;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "USER_NAME")
})
public class User extends UUIDPersistence {

    @Column(name = "USER_NAME", length = 64)
    private String name;

    @Column(name="PASSWORD", length = 16)
    private String password;

    @Column(name = "SEX", length = 2)
    private String sex;

    @Column(name = "BIRTH_DAY")
    private Date birthDay;

    @Column(name="PHONE", length = 20)
    private String phone;

    @Column(name = "TYPE")
    private String type = "user";

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PICTURE_ID", referencedColumnName = "ID")
    private Picture header;
}
