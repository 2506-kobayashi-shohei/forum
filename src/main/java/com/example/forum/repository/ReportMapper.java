package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReportMapper {
    @Select("SELECT * FROM report WHERE updated_date BETWEEN #{start} AND #{end} ORDER BY updated_date DESC")
    List<Report> findAllReport(LocalDateTime start, LocalDateTime end);

    @Select("SELECT * FROM report WHERE id = #{id}")
    List<Report> findById(int id);

    @Insert("INSERT INTO report(content, created_date, updated_date) VALUES(#{content}, #{createdDate}, #{updatedDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Report report);

    @Update("UPDATE report SET content = #{content}, updated_date = #{updatedDate} WHERE id = #{id}")
    void save(Report report);

    @Delete("DELETE FROM report WHERE id = #{id}")
    void deleteById(int id);
}
