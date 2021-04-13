package ai.ecma.clicksecurity.entity;

import ai.ecma.clicksecurity.entity.enums.StatusType;
import ai.ecma.clicksecurity.entity.templete.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "statuss")
public class Status  extends AbsEntity {



    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;



    public Status( StatusType statusType, String name, Project project) {

        this.statusType = statusType;
        this.name = name;
        this.project = project;
    }

}
