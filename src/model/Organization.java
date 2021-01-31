package model;

import util.DateUtil;
import util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Position> positions = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + ", "
                + positions + ")";
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String positionName;
        private String description;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String positionName, String description) {
            this(DateUtil.of(startYear, startMonth), LocalDate.now(), positionName, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String positionName, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), positionName, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String positionName, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.positionName = positionName;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getPositionName() {
            return positionName;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Position(" + startDate + "," +
                    endDate + "," +
                    positionName + "," +
                    description + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return startDate.equals(position.startDate) &&
                    endDate.equals(position.endDate) &&
                    positionName.equals(position.positionName) &&
                    description.equals(position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, positionName, description);
        }
    }
}
