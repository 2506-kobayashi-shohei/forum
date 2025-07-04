package com.example.forum.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/*Dto　ユーザー側に表示する用のbean*/
@Getter
@Setter
public class ReportForm {
    private int id;
    @NotBlank(message = "投稿内容を入力してください")
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
