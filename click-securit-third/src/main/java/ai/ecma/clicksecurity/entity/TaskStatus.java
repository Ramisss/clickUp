package ai.ecma.clicksecurity.entity;


import ai.ecma.clicksecurity.entity.templete.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_id", "status_id"})})
public class TaskStatus extends AbsEntity {




    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Task task;


    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;






}
