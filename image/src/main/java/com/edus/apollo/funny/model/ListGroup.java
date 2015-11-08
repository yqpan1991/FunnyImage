package com.edus.apollo.funny.model;

import java.util.ArrayList;
import java.util.List;
import com.edus.apollo.funny.net.model.MakeModule.Template;

/**
 * Created by Panda on 2015/11/2.
 */
public class ListGroup {

    private int mPerSize;
    public static final int DEFAULT_PER_SIZE = 1;

    private List<Template> mModelList = new ArrayList<>();

    public ListGroup(int perSize) {
        if(perSize > DEFAULT_PER_SIZE){
            mPerSize = perSize;
        }else{
            mPerSize = DEFAULT_PER_SIZE;
        }
    }

    public void setData(List<Template> modelList) {
        mModelList.clear();
        if (modelList != null && !modelList.isEmpty()) {
            mModelList.addAll(modelList);
        }
        calculate();
    }

    private void calculate() {

    }

    public Integer[] getListPositionByGroupPosition(int groupPosition) {
        if(groupPosition < getMaxGroupPosition() && groupPosition >= 0){
            //get data
            int startPosition = groupPosition * mPerSize;
            int endPosition = (groupPosition +1) * mPerSize;
            int maxValidListPosition = getMaxValidListPositon();
            if(endPosition > maxValidListPosition){
                endPosition = maxValidListPosition;
            }
            List<Integer> list = new ArrayList<>();
            for(int index = startPosition ; index < endPosition ; index++){
                list.add(index);
            }
            return  list.toArray(new Integer[list.size()]);
        }else{
            return null;
        }
    }

    public Template[] getModelListByGroupPosition(int groupPosition){
        Integer[] listPositions = getListPositionByGroupPosition(groupPosition);
        if(listPositions == null){
            return null;
        }

        Template[] model = new Template[listPositions.length];
        List<Template> modelList = new ArrayList<>();
        for(int index = 0; index < listPositions.length; index++){
            modelList.add(mModelList.get(listPositions[index]));
        }
        return modelList.toArray(new Template[modelList.size()]);

    }

    public int getGroupPositionByListPosition(int listPosition) {
        int rawGroupPosition = (int) Math.ceil(listPosition * 1.0 / mPerSize);
        int curValidaGroupPosition = getMaxValidListPositon();
        if(rawGroupPosition > curValidaGroupPosition){//invalidate
            return -1;
        }else{
            return rawGroupPosition;
        }
    }

    public int getGroupCount() {
        return (int) Math.ceil(mModelList.size() * 1.0 / mPerSize);
    }

    public int getListCount() {
        return mModelList.size();
    }

    private int getMaxValidListPositon(){
        return mModelList.size() - 1;
    }

    private int getMaxGroupPosition(){
        return getGroupCount() - 1;
    }


}
