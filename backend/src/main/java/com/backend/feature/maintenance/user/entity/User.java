package com.backend.feature.maintenance.user.entity;

import com.backend.comment.entity.UUIDPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
}
