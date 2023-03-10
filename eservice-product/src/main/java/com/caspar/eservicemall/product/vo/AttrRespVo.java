package com.caspar.eservicemall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{

    /**
     * "catelogName" : "手机/数码/手机,//所属分类的名字"
     * "groupName" :“主体”，//所属分组名字
     */
    private String catelogName;
    private String groupName;
    private Long attrGroupId;
    private Long catelogId;
    private Long[] catelogPath;
}
