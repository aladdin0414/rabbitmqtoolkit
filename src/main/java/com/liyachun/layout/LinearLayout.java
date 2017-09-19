package com.liyachun.layout;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author liyc
 * @date 2017年9月19日 下午2:29:40
 */
public class LinearLayout extends JPanel{

	/**
	 * 水平布局
	 */
	public static final int VERTICAL = 0;
	
	/**
	 * 垂直布局
	 */
	public static final int HORIZONTAL = 1;
	
	List<Component> subviews = new ArrayList<Component>();
	
	/**
	 * 内边距
	 * 上，右，下，左
	 */
	int paddingTop,paddingRight,paddingBottom,paddingLeft;
	
	/**
	 * 控件之间的间距
	 */
	int gap;
	
	int orientation = VERTICAL;
	
	int parentWidth;
	
	int parentHeight;
	
	public LinearLayout(int parentWidth,int parentHeight) {
		setLayout(null);
		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;
		this.setSize(parentWidth, parentHeight);
	}
	
	public void addChild(Component subview) {
		
		int lstX = paddingLeft;
		int lstY = paddingTop;
		int lstW = 0;
		int lstH = 0;
		int cgap = 0;
		if(subviews.size()>0) {
			Component lastView = subviews.get(subviews.size()-1);
			lstX = lastView.getX();
			lstY = lastView.getY();
			lstW = lastView.getWidth();
			lstH = lastView.getHeight();
			cgap = gap;
		}
		
		if(orientation == VERTICAL) {
			subview.setLocation(paddingLeft, lstY+lstH+cgap);
			subview.setSize(this.parentWidth - paddingLeft - paddingRight, subview.getHeight());
		}
		else {
			subview.setLocation(lstX+lstW+cgap, paddingTop);
			subview.setSize(subview.getWidth(), parentHeight-paddingTop-paddingBottom);
		}
		
		subviews.add(subview);
		
		add(subview);
		
		int maxWidth = 0;
		//计算子控件的最大宽度
		for(Component c:subviews) {
			if(c.getWidth() > maxWidth) {
				maxWidth = c.getWidth();
			}
		}
	}
	
	/**
	 * 设置布局方向
	 * @param orientation
	 */
	public void setOrientation(int orientation) {
		if(orientation==VERTICAL || orientation == HORIZONTAL) {
			this.orientation = orientation;
		}
	}
	
	public void setGap(int gap) {
		this.gap = gap;
	}
	
	/**
	 * 设置边界
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 */
	public void setPadding(int top,int right,int bottom,int left) {
		paddingTop = top;
		paddingRight = right;
		paddingBottom = bottom;
		paddingLeft = left;
	}
}
