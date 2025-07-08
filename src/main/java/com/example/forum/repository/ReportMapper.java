package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReportMapper {
    List<Report> findAllReport(LocalDateTime start, LocalDateTime end);
}
