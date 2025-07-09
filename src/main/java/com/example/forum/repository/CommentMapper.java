package com.example.forum.repository;

import com.example.forum.repository.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM comment")
    List<Comment> findAll();

    @Select("SELECT * FROM comment WHERE id = #{id}")
    List<Comment> findById(int id);

    @Insert("INSERT INTO comment(report_id, content) VALUES(#{reportId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Comment comment);

    @Update("UPDATE comment SET content = #{content} WHERE id = #{id}")
    void save(Comment comment);

    @Delete("DELETE FROM report WHERE id = #{id}")
    void deleteById(int id);
}
