package model;

import java.util.List;
import java.util.Objects;

public class ListSection<T> extends AbstractSection {
    private List<T> list;

    public ListSection(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        list.forEach(v -> result.append(v).append(","));
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection<?> that = (ListSection<?>) o;

        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
