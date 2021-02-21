package com.backend.feature.maintenance.sportGround.entity;

import com.backend.comment.entity.FilePersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "picture")
@Entity
public class Picture extends FilePersistence {

}
