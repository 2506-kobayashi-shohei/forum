package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentMapper;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    /*
     * レコード全件取得処理
     */
    public List<CommentForm> findAllComment() {
        List<Comment> results = commentMapper.findAll();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> reports = new ArrayList<>();

        for (Comment result : results) {
            CommentForm comment = new CommentForm();
            comment.setId(result.getId());
            comment.setReportId(result.getReportId());
            comment.setContent(result.getContent());
            reports.add(comment);
        }
        return reports;
    }
    /*新規コメント登録と*/
    public void saveComment(CommentForm reqComment){
        Comment saveComment = setCommentEntity(reqComment);
        commentMapper.insert(saveComment);
    }
    /*取得したコメントをEntityに変換*/
    private Comment setCommentEntity(CommentForm reqComment){
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setReportId(reqComment.getReportId());
        comment.setContent(reqComment.getContent());
        return comment;
    }
    /*コメント編集（画面表示）*/
    public CommentForm getCommentById(Integer id) {
        List<Comment> results = commentMapper.findById(id);
        List<CommentForm> comments = setCommentForm(results);
        return comments.get(0);
    }
    /*編集後のコメントの登録*/
    public void editComment(CommentForm reqComment){
        Comment saveComment = setCommentEntity(reqComment);
        commentMapper.save(saveComment);
    }
    /*コメント削除処理*/
    public void deleteComment(Integer id) {
        commentMapper.deleteById(id);
    }
}
