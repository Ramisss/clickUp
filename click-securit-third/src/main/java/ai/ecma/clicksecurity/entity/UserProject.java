package ai.ecma.clicksecurity.entity;


import ai.ecma.clicksecurity.entity.enums.ProjectPermissionEnum;
import ai.ecma.clicksecurity.entity.templete.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserProject extends AbsEntity  {


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;//1,1,1,2,2


    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;//1,2,3,1,3


    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<ProjectPermissionEnum> projectPermissionEnums;



}
