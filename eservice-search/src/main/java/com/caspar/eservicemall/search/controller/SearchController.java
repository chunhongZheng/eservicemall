package com.caspar.eservicemall.search.controller;

import com.caspar.eservicemall.search.service.impl.MallSearchServiceImpl;
import com.caspar.eservicemall.search.vo.SearchParam;
import com.caspar.eservicemall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 商城检索
 * @Author: wanzenghui
 * @Date: 2021/11/10 23:51
 */
@Controller
public class SearchController {

    @Autowired
    MallSearchServiceImpl mallSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {

//        param.set_queryString(request.getQueryString());
//        SearchResult result = mallSearchService.search(param);
//        model.addAttribute("result", result);
        return "list";
    }
}
