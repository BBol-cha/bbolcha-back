package project.BBolCha.domain.board.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.BBolCha.domain.board.dto.service.request.HintServiceRequest;
import project.BBolCha.domain.board.dto.service.request.TagServiceRequest;
import project.BBolCha.domain.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Builder
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE board SET deleted_at = CURRENT_TIMESTAMP where id = ?")
public class Board extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 65)
    @NotNull
    private String title;

    @Column(length = 10000)
    @NotNull
    private String content;

    @NotNull
    private String correct;

    private String contentImageUrl;

    private Integer viewCount;

    @OneToMany(mappedBy = "board")
    @Builder.Default
    private List<Like> like = new ArrayList<>();

    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Tag tag;

    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Hint hint;

    private LocalDateTime deletedAt;

    public void updateBoard(
            String title, String content, String correct,
            String contentImageUrl, TagServiceRequest.Save tag, HintServiceRequest.Save hint
    ) {
        this.title = title;
        this.content = content;
        this.correct = correct;
        this.contentImageUrl = contentImageUrl;

        this.tag.updateTag(tag);
        this.hint.updateHint(hint);
    }

    public void saveTagAndHint(Tag tag, Hint hint) {
        this.tag = tag;
        this.hint = hint;
    }
}
