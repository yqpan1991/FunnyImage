package com.edus.apollo.funny.net.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Panda on 2015/11/16.
 */
public class ClassifyDetailModule extends BaseResponse {

    @JSONField(name="tag_list")
    public List<TagItem> tagList;

    @JSONField(name="templates")
    public List<MakeModule.Template> templates;


    public static class TagItem{

        @JSONField(name="id")
        public int id;

        @JSONField(name="name")
        public String name;

    }


}
