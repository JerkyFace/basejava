package model;

import java.time.LocalDate;

public class Activity {
    private String organizationName;
    private String positionName;
    private String url;
    private LocalDate startDate;
    private LocalDate endDate;
    private String activityDescription;

    public Activity(String organizationName, String positionName, String url, LocalDate startDate, LocalDate endDate) {
        this.organizationName = organizationName;
        this.positionName = positionName;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Activity(String organizationName, String url, LocalDate startDate, LocalDate endDate, String activityDescription) {
        this.organizationName = organizationName;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityDescription = activityDescription;
    }

    public Activity(String organizationName, String positionName, String url, LocalDate startDate, LocalDate endDate,
                    String activityDescription) {
        this.organizationName = organizationName;
        this.positionName = positionName;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityDescription = activityDescription;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "organizationName='" + organizationName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", url='" + url + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", activityDescription='" + activityDescription + '\'' +
                "}\n\n";
    }
}
