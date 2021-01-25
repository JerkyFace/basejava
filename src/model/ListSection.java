package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection<T> extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> list = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(List<T> list) {
        this.list = list;
    }

    public void add(T value) {
        list.add(value);
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
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
        return Objects.hash(list);
    }
}
