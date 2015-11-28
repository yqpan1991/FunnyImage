package com.edus.apollo.funny.net.model;

import android.graphics.Rect;

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

        /**是否是热门*/
        @JSONField(name = "is_hot")
        public boolean isHot;

        /**使用次数*/
        @JSONField(name = "num")
        public int num;

        @JSONField(name = "pic")
        public String pic;

        @JSONField(name = "position")
        public List<Integer> positions;

        @JSONField(name = "text")
        public String text;

        @JSONField(name = "thumb")
        public String thumb;

        /**服务器上更新的时间*/
        @JSONField(name = "time")
        public long time;

        @JSONField(name = "title")
        public String title;

        @JSONField(name = "with_text")
        public String[] withText;

        /**
         * style:0 black style
         * style: 2 white style
         * */
        @JSONField(name = "style")
        public int style;

        public Template() {

        }

        public Rect fetchRect(){
            if(positions == null || positions.isEmpty() || positions.size() != 4){
                return null;
            }
            return new Rect(positions.get(0),positions.get(1),positions.get(2),positions.get(3));
        }

    }
}
