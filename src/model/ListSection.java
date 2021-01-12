package model;

import java.util.List;

public class ListSection<T> extends ResumeSection {
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
        return "ListSection{" +
                "list=" + list +
                "}\n\n";
    }
}
