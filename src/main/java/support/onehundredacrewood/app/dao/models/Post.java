package support.onehundredacrewood.app.dao.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 5500, nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private boolean locked;

    @Column(nullable = false)
    private boolean reported;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "post_topic",
            joinColumns = {
                    @JoinColumn(name = "post_id",
                            referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "topic_id",
                            referencedColumnName = "id")}
    )
    private List<Topic> topics = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "followedPosts", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> usersFollowing;

    public Post() {
    }

    public Post(User user, String title, String body, LocalDateTime created,
                boolean locked, boolean reported, List<Topic> topics) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.created = created;
        this.locked = locked;
        this.reported = reported;
        this.topics = topics;
    }

    public Post(User user, String title, String body, LocalDateTime created,
                boolean locked, boolean reported, List<Topic> topics,
                List<Comment> comments, List<User> usersFollowing) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.created = created;
        this.locked = locked;
        this.reported = reported;
        this.topics = topics;
        this.comments = comments;
        this.usersFollowing = usersFollowing;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Comment> getComments() {
        this.comments.sort(Comparator.comparing(Comment::getCreated).reversed());
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getUsersFollowing() {
        return usersFollowing;
    }

    public void setUsersFollowing(List<User> usersFollowing) {
        this.usersFollowing = usersFollowing;
    }

    public static boolean isFollowing(List<Post> following, final long postId) {
        return following.stream().anyMatch(p -> p.getId() == postId);
    }
}
