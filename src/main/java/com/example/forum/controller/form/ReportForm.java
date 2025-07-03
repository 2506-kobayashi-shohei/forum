package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/*Dto　ユーザー側に表示する用のbean*/
@Getter
@Setter
public class ReportForm {
    private int id;
    private String content;
    private LocalDateTime updatedDate;
}
