package com.appmarket.mapleaf.applicationmarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.appmarket.mapleaf.applicationmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Mapleaf on 2016/7/13.
 */
public class MyOrderView extends ViewGroup{

    private int width;
    private int widthMode;
    private int height;
    private int heightMode;
    private int curMeasureWidth;
    private Line curLine;
    private ArrayList<Line> lineList= new ArrayList<>();
    private static final int HORDISTANCE = UIUtils.dp2px(10);
    private static final int VERDISTANCE = UIUtils.dp2px(7);
    public MyOrderView(Context context) {
        super(context);
    }

    public MyOrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyOrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int left = i+getPaddingLeft();
        int top = i1+getPaddingTop();
        for(int j=0;j<lineList.size();j++){
            Line line = lineList.get(j);
            line.layout(left,top);
            top+=line.maxHeight+VERDISTANCE;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = MeasureSpec.getSize(widthMeasureSpec) -getPaddingLeft()-getPaddingRight();
        height = MeasureSpec.getSize(heightMeasureSpec) -getPaddingBottom()-getPaddingTop();
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        widthMode=MeasureSpec.getMode(widthMeasureSpec);


        for(int i = 0;i<getChildCount();i++){
            View childView = getChildAt(i);
            int childWidthSpec = MeasureSpec.makeMeasureSpec(width, widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(height,heightMode ==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:heightMode);
            childView.measure(childWidthSpec,childHeightSpec);
            if(curLine==null){
                curLine = new Line();
            }
            curMeasureWidth+=childView.getMeasuredWidth();
            if(curMeasureWidth<width){
                //当前孩子宽度小于总宽度
                curLine.addChild(childView);
                if((curMeasureWidth+HORDISTANCE)<width){
                    //孩子宽度加上一个水平间距之后小于总宽度
                    curMeasureWidth=curMeasureWidth+HORDISTANCE;
                }
                else{
                    //孩子宽度加上一个水平间距之后大于总宽度，换行
                    newLine();
                }
            }else{
                //当前孩子宽度大于总宽度
                if(curLine.getChildCount()==0){
                    //孩子只有一个且宽度大于总宽度，压缩孩子宽度
                    curLine.addChild(childView);
                    newLine();
                }else{
                    //孩子有多个不够放，换行
                    newLine();
                    curLine.addChild(childView);
                    curMeasureWidth+=childView.getMeasuredWidth()+HORDISTANCE;
                }
            }
        }
        if(curLine!=null&&curLine.getChildCount()!=0&&!lineList.contains(curLine)){
            lineList.add(curLine);
        }
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int totalHeight=0;
        for(int i=0;i<lineList.size();i++){
            totalHeight+=lineList.get(i).maxHeight;
        }
        totalHeight=totalHeight+getPaddingTop()+getPaddingBottom()+VERDISTANCE*(lineList.size()-1);
        setMeasuredDimension(totalWidth,totalHeight);
    }
    public void newLine(){
        lineList.add(curLine);
        curLine = new Line();
        curMeasureWidth=0;
    }
    class Line{
        public Line(){
            childList = new ArrayList<>();
        }
        public ArrayList<View> childList;
        public int curLineWidth;
        public int maxHeight;
        public void addChild(View view){
            childList.add(view);
            curLineWidth+=view.getMeasuredWidth();
            maxHeight = maxHeight< view.getMeasuredHeight()?view.getMeasuredHeight():maxHeight;
        }
        private int getChildCount(){
            return childList.size();
        }
        public void layout(int left,int top){
            int availWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            int childTotalWidth = curLineWidth+(childList.size()-1)*HORDISTANCE;
            int leftWidth = (availWidth-childTotalWidth)/childList.size();
            if(leftWidth>=0){
                for(int i=0;i<childList.size();i++){
                    View child = childList.get(i);
                    int cHeight = child.getMeasuredHeight();
                    int topOff = (maxHeight-cHeight)/2;
                    int childWidth = MeasureSpec.makeMeasureSpec(child.getMeasuredWidth()+leftWidth,MeasureSpec.EXACTLY);
                    int childHeight = MeasureSpec.makeMeasureSpec(cHeight,MeasureSpec.EXACTLY);
                    child.measure(childWidth,childHeight);
                    child.layout(left,top+topOff,left+child.getMeasuredWidth()+leftWidth,top+topOff+child.getMeasuredHeight());
                    left += child.getMeasuredWidth()+leftWidth+HORDISTANCE;
                }
            }else{
                View longView = childList.get(0);
                longView.layout(left,top,left+longView.getMeasuredWidth(),top+longView.getMeasuredHeight());
            }


        }
    }
}
