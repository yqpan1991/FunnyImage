package com.edus.apollo.funny.net;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by yqpan on 2015/10/13.
 */
public class ModuleClassifyHomeRjo extends RtNetworkEvent {
    @JSONField(name="cls_list")
    private List<ClsSet> clsList;

    public static class ClsSet {
        private String name;
        @JSONField(name="tag_list")
        private List<TagSet> tagList;

        public ClsSet(String str, List<TagSet> list) {
            this.name = str;
            this.tagList = list;
        }

        public String getName() {
            return this.name;
        }

        public List<TagSet> getTagList() {
            return this.tagList;
        }
    }

    public static class TagSet {
        private String cover;
        private int id;
        private String name;
        private int num;

        public TagSet(int i, String str, int i2, String str2) {
            this.id = i;
            this.name = str;
            this.num = i2;
            this.cover = str2;
        }

        public String getCover() {
            return this.cover;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int getNum() {
            return this.num;
        }
    }

    public ModuleClassifyHomeRjo(List<ClsSet> list) {
        this.clsList = list;
    }

    public List<ClsSet> getClsList() {
        return this.clsList;
    }

}
