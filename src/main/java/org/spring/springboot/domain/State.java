/**
 * <p>Description: </p>
 * <p>Title: State.java</p>  
 * <p>Copyright: Copyright (c) 2018</p>  
 * <p>Company: cei</p>  
 * <p>@author zhaowei</p>
 * <p>@version 2018年5月8日 下午7:03:31</p>
 */
package org.spring.springboot.domain;

/**
 * Title: State  
 * Description: 
 * @author zhaowei 
 * @version 2018年5月8日 下午7:03:31
 */
public class State {
    private boolean opened;
	private boolean disabled;
	private boolean selected;
	
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
