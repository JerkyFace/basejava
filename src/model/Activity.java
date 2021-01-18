package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity {
    private String organizationName;
    private String url;
    private List<Position> positions = new ArrayList<>();

    public Activity(String organizationName, String url, List<Position> positions) {
        this.organizationName = organizationName;
        this.url = url;
        this.positions = positions;
    }

    public Activity(String organizationName, String url, Position position) {
        this.organizationName = organizationName;
        this.url = url;
        positions.add(position);
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(organizationName, activity.organizationName) &&
                Objects.equals(url, activity.url) &&
                Objects.equals(positions, activity.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationName, url, positions);
    }

    @Override
    public String toString() {
        return organizationName + ":\n" +
                url + "\n" +
                positions + "\n";
    }

    public static class Position {
        private LocalDate startDate;
        private LocalDate endDate;
        private String positionName;
        private String description;

        public Position(LocalDate startDate, LocalDate endDate, String positionName, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.positionName = positionName;
            this.description = description;
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

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return startDate + "/" +
                    endDate + "\n" +
                    positionName + '\n' +
                    description + "\n";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!Objects.equals(startDate, position.startDate)) return false;
            if (!Objects.equals(endDate, position.endDate)) return false;
            if (!Objects.equals(positionName, position.positionName))
                return false;
            return Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            int result = startDate != null ? startDate.hashCode() : 0;
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            result = 31 * result + (positionName != null ? positionName.hashCode() : 0);
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }
}
