package com.dominikazb.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "note")
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int id;

    @Column(name = "note_date")
    private Date date;

    @Column(name = "note_title")
    private String title;

    @Column(name="note_content", columnDefinition="TEXT")
    private String content;

    @Column(name="tags_string")
    private String tagsString;

    @Column(name="categories_string")
    private String categoriesString;

    @ManyToOne
    @JoinColumn(name="user_id_FK")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "notes_categories",
            joinColumns = {@JoinColumn(name = "id_note", referencedColumnName = "note_id")},
            inverseJoinColumns = {@JoinColumn(name = "id_category", referencedColumnName = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "notes_tags",
            joinColumns = {@JoinColumn(name = "id_note", referencedColumnName = "note_id")},
            inverseJoinColumns = {@JoinColumn(name = "id_tag", referencedColumnName = "tag_id")}
    )
    private Set<Tag> tags = new HashSet<>();

    public Note() {
    }

    public Note(Date date, String title, String content, String categoriesString, String tagsString,
                Set<Category> categories, Set<Tag> tags, User user) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.categoriesString = categoriesString;
        this.tagsString = tagsString;
        this.categories = categories;
        this.tags = tags;
        this.user = user;
    }

    public Note(int id, Date date, String title, String content, String categoriesString, String tagsString,
                Set<Category> categories, Set<Tag> tags, User user) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.categoriesString = categoriesString;
        this.tagsString = tagsString;
        this.categories = categories;
        this.tags = tags;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getTagsString() {
        return tagsString;
    }

    public void setTagsString(String tagsString) {
        this.tagsString = tagsString;
    }

    public String getCategoriesString() {
        return categoriesString;
    }

    public void setCategoriesString(String categoriesString) {
        this.categoriesString = categoriesString;
    }

}
