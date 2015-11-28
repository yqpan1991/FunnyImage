package com.edus.apollo.funny.net.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Panda on 2015/11/9.
 */
public class ClassifyModule extends BaseResponse {
    private static final int PER_ROW_SIZE = 4;

    @JSONField(name="cls_list")
    public List<GroupItem> groupItemList;

    public static class GroupItem{
        @JSONField(name="id")
        public int id;
        @JSONField(name="name")
        public String name;
        @JSONField(name="tag_list")
        public List<SingleItem> singleItemList;

        public int calRowCount(){
            if(singleItemList != null && !singleItemList.isEmpty()){
                return (int) Math.ceil(singleItemList.size()*1.0/PER_ROW_SIZE);
            }
            return 0;
        }

        public SingleItem[] fetchRowData(int rowIndex){
            if(rowIndex >= calRowCount()){
                return null;
            }
            int startIndex = rowIndex*PER_ROW_SIZE;
            int endIndex = (rowIndex+1) * PER_ROW_SIZE;
            if(endIndex >= singleItemList.size()){
                endIndex = singleItemList.size();
            }
            return singleItemList.subList(startIndex,endIndex).toArray(new SingleItem[endIndex-startIndex]);
        }
    }

    public static class SingleItem implements Serializable{

        @JSONField(name="cover")
        public String coverUrl;

        @JSONField(name="id")
        public int id;

        @JSONField(name="name")
        public String name;

        @JSONField(name="num")
        public int num;
    }
}
