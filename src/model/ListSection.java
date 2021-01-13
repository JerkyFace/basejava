package model;

import java.util.List;

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
}
