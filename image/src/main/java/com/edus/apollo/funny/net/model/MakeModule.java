package com.edus.apollo.funny.net.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Panda on 2015/10/27.
 */
public class MakeModule extends BaseResponse {

    @JSONField(name = "templates")
    public List<Template> templates;

    @JSONField(name = "title")
    private String title;

    public static class Template implements Serializable{

        @JSONField(name = "id")
        public int id;

        @JSONField(name = "is_hot")
        public boolean isHot;

        @JSONField(name = "num")
        public int num;

        @JSONField(name = "pic")
        public String pic;

        @JSONField(name = "position")
        public List<Integer> position;

        @JSONField(name = "text")
        public String text;

        @JSONField(name = "thumb")
        public String thumb;

        @JSONField(name = "time")
        public long time;

        @JSONField(name = "title")
        public String title;

        @JSONField(name = "with_text")
        public String[] withText;

        public Template() {

        }

    }
}
