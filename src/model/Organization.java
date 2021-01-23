package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String url;
    private List<Position> positions = new ArrayList<>();

    public Organization(String name, String url, List<Position> positions) {
        this.name = name;
        this.url = url;
        this.positions = positions;
    }

    public Organization(String name, String url, Position position) {
        this.name = name;
        this.url = url;
        positions.add(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization organization = (Organization) o;
        return Objects.equals(name, organization.name) &&
                Objects.equals(url, organization.url) &&
                Objects.equals(positions, organization.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, positions);
    }

    @Override
    public String toString() {
        return name + ":\n" +
                url + "\n" +
                positions + "\n";
    }

    public static class Position implements Serializable {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String positionName;
        private final String description;

        public Position(LocalDate startDate, LocalDate endDate, String positionName, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.positionName = positionName;
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
