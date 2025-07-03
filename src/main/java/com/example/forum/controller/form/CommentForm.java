package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;
/*Dto　ユーザー側に表示する用のbean*/
@Getter
@Setter
public class CommentForm {
    private int id;
    private int reportId;
    private String content;
}
