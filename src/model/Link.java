package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String homePageUrl;

    public Link() {
    }

    public Link(String name, String homePageUrl) {
        this.name = name;
        this.homePageUrl = homePageUrl;
    }

    public String getName() {
        return name;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    @Override
    public String toString() {
        return "Link(" + name + ", " + homePageUrl + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(name, link.name) &&
                Objects.equals(homePageUrl, link.homePageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, homePageUrl);
    }
}
