package com.exenta.notification;

public class CustomNotificationItem {
	
	
	private String title;
    private int icon;

    public CustomNotificationItem(String title, int icon) {
        this.title = title;
        this.icon = icon;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
