package com.caspar.eservicemall.search.controller;

import com.caspar.eservicemall.search.service.impl.MallSearchServiceImpl;
import com.caspar.eservicemall.search.vo.SearchParam;
import com.caspar.eservicemall.search.vo.SearchResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.List;

/**
 * 商城检索
 * @Author: wanzenghui
 * @Date: 2021/11/10 23:51
 */
@Controller
public class SearchController {

    @Autowired
    MallSearchServiceImpl mallSearchService;
    @GetMapping(value = {"list.html"})
    public String listPage(SearchParam param, Model model, HttpServletRequest request) throws IOException {
        param.set_queryString(request.getQueryString());
        //1.根据传递来的页面的查询参数，去es中检索商品
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result", result);
        return "list";
    }
    @GetMapping(value = {"/","index.html"})
    private String indexPage(SearchParam param, Model model, HttpServletRequest request) throws IOException {
        //1.根据传递来的页面的查询参数，去es中检索商品
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result", result);
        return "index";
    }

}
