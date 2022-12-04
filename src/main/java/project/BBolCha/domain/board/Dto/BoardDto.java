package project.BBolCha.domain.board.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.BBolCha.domain.board.Entity.Board;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BoardDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private Long id;
        private Long userId;
        private String name;
        private String title;
        private String note;
        private Integer views;
        private String subTitle;
        private String bimg;
        private LocalDateTime creatAt;
        private LocalDateTime updateAt;

        public BoardDto.Request Response(Board board){
        return Request.builder()
                .id(board.getId())
                .userId(board.getUserId())
                .name(board.getName())
                .title(board.getTitle())
                .bimg(board.getBimg())
                .views(board.getViews())
                .note(board.getNote())
                .subTitle(board.getSubTitle())
                .creatAt(board.getCreatAt())
                .updateAt(board.getUpdateAt())
                .build();
        }
    }
}