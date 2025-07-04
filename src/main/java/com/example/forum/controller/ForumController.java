package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;
    @Autowired
    CommentService commentService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping/*コンテキストルートなので、URLパターンは載せない。*/
    public ModelAndView top(@RequestParam(value="start",required=false)LocalDate start,
                            @RequestParam(value="end",required=false)LocalDate end) {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport(start,end);
        //コメントフォームを表示するための、空のCommentFormを作成
        CommentForm commentForm = new CommentForm();
        //コメントを全件取得
        List<CommentForm> commetData = commentService.findAllComment();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        mav.addObject("commentModel", commentForm);
        mav.addObject("comments", commetData);
        mav.addObject("start", start);
        mav.addObject("end", end);
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView newContent() {
        /*Modelも、ModelAndViewもViewとの橋渡しをしている。
         * 記述方法に違いがあるだけで、役割としては変わらない*/
        /*Modelを使うとすれば、
         * public String XXXXX(Model model){}みたいになる。
         * この場合下みたいにnewはしなくていい。*/
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addContent(@Validated @ModelAttribute("formModel") ReportForm reportForm,
                                   BindingResult result) {
        if(result.hasErrors()){
            return new ModelAndView("/new");
        }
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id) {
        /*「@PathVariable」は、
         form タグ内の action 属性に記述されている { } 内で指定されたURLパラメータを取得できる
         */
        // 投稿をテーブルに格納
        reportService.deleteReport(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
    /*
     * 編集画面表示処理
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集する投稿を取得
        ReportForm report = reportService.getReportById(id);
        // 編集する投稿をセット
        mav.addObject("formModel", report);
        //セッションスコープ
        // 画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }
    @PutMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id,
                                      @Validated @ModelAttribute("formModel") ReportForm report,
                                      BindingResult result){
        /*@ModelAttributeはフロント側から何かもらってきたい時に使う。*/
        if(result.hasErrors()){
            return new ModelAndView("/edit");
        }
        report.setId(id);
        reportService.saveReport(report);
        return new ModelAndView("redirect:/");
    }

    /*
    コメントに関する機能
    */
    /*新規コメント追加*/
    @PostMapping("/addComment/{ReportId}")
    public ModelAndView addComment(@PathVariable Integer ReportId,
                                   @Validated @ModelAttribute("commentModel") CommentForm comment,
                                   BindingResult result){
        if(result.hasErrors()){
            return new ModelAndView("/top");
        }
        comment.setReportId(ReportId);
        commentService.saveComment(comment);
        ReportForm report = reportService.getReportById(ReportId);
        reportService.saveReport(report);
        return new ModelAndView("redirect:/");
    }
    /*コメントの編集*/
    @GetMapping("/editComment/{id}")
    public ModelAndView editComment(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集する投稿を取得
        CommentForm comment = commentService.getCommentById(id);
        // 編集する投稿をセット
        mav.addObject("formModel", comment);
        //セッションスコープ
        // 画面遷移先を指定
        mav.setViewName("/editComment");
        return mav;
    }
    @PutMapping("/updateComment/{id}")
    public ModelAndView updateComment(@PathVariable Integer id,
                                      @Validated @ModelAttribute("formModel") CommentForm comment,
                                      BindingResult result){
        /*@ModelAttributeはフロント側から何かもらってきたい時に使う。*/
        if(result.hasErrors()){
            return new ModelAndView("/editComment");
        }
        comment.setId(id);
        commentService.saveComment(comment);
        return new ModelAndView("redirect:/");
    }
    /* コメント削除処理 */
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return new ModelAndView("redirect:/");
    }
}
