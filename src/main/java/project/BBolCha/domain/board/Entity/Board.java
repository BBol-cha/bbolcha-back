package project.BBolCha.domain.board.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "board")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    @NotNull
    private Long userId;

    @NotNull
    private String name;
    @Column(name = "title", length = 65)
    @NotNull
    private String title;

    @Column(name = "note", length = 10000)
    @NotNull
    private String note;
    @Column(name = "views")
    @NotNull
    private Integer views;

    @Column(name = "answer")
    @NotNull
    private String answer;

    @Column(name = "hints")
    private String hints;

    private String bimg;

    private String tag;

    @Column(name = "create_at")
    @CreatedDate
    @NotNull
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @LastModifiedDate
    @NotNull
    private LocalDateTime updateAt;

    private Board() {
    }
}
