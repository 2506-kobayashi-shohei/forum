package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportMapper;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReportService {
    @Autowired
    ReportMapper reportMapper;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(LocalDate start, LocalDate end) {
        LocalDateTime startTime = LocalDateTime.of(2020, 01, 01,00,00);
        if(start != null){
            startTime = start.atStartOfDay();
        }
        LocalDateTime endTime = LocalDateTime.now();
        if (end != null){
            endTime = end.atTime(23, 59, 59);
        }
        List<Report> results = reportMapper.findAllReport(startTime,endTime);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (Report result : results) {
            ReportForm report = new ReportForm();
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setCreatedDate(result.getCreatedDate());
            report.setUpdatedDate(result.getUpdatedDate());
            reports.add(report);
        }
        return reports;
    }
    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        reqReport.setCreatedDate(LocalDateTime.now());
        reqReport.setUpdatedDate(LocalDateTime.now());
        Report saveReport = setReportEntity(reqReport);
        reportMapper.insert(saveReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        report.setCreatedDate(reqReport.getCreatedDate());
        report.setUpdatedDate(reqReport.getUpdatedDate());
        return report;
    }

    /*
    *削除機能
    */
    public void deleteReport(Integer id) {
        reportMapper.deleteById(id);
    }
    /*
    編集画面にデータを表示
    */
    /*
     * レコード1件取得
     */
    public ReportForm getReportById(Integer id) {
        List<Report> results = reportMapper.findById(id);
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }
    /*
     * レコード編集でも使う
     */
    public void editReport(ReportForm reqReport) {
        reqReport.setUpdatedDate(LocalDateTime.now());
        Report saveReport = setReportEntity(reqReport);
        reportMapper.save(saveReport);
    }
}
