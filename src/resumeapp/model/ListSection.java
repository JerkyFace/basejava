package resumeapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> list = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
