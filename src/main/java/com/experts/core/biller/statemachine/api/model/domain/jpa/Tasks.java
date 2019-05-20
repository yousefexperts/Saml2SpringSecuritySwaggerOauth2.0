package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.constants.TasksType;
import com.experts.core.biller.statemachine.api.persist.OpsEntityListener;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name  = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({AuditingEntityListener.class , OpsEntityListener.class})
@SpaceClass
public class Tasks extends AbstractEntity {

    @Column(name  = "task_name" , nullable = false)
    private String taskName;

    @Column(name  = "task_id" , nullable = false)
    private String taskId;

    @Column(name  = "type" , nullable = false)
    private TasksType type;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "tasks_" , cascade = CascadeType.ALL)
    private List<TaskVariables> variables;

    @Version
    private int version;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TasksType getType() {
        return type;
    }

    public void setType(TasksType type) {
        this.type = type;
    }

    public List<TaskVariables> getVariables() {
        return variables;
    }

    public void setVariables(List<TaskVariables> variables) {
        this.variables = variables;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
