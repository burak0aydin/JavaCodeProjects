/**
 * Burak Aydın
 * 20190808047
 * 2023-06-25
 */

import java.util.*;
import java.util.Date;

public class Ex10_20190808047 {
}

class User {
    private int id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.id = hashCode();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.likedPosts = new HashSet<>();
        this.messages = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void message(User recipient, String content) {
        if (!messages.containsKey(recipient))
            messages.put(recipient, new LinkedList<>());

        if (!recipient.getMessages().containsKey(this))
            recipient.getMessages().put(this, new LinkedList<>());

        Message newMessage = new Message(this, content);
        messages.get(recipient).add(newMessage);
        recipient.getMessages().get(this).add(newMessage);
        read(recipient);
    }

    public void read(User user) {
        if (messages.containsKey(user)) {
            Queue<Message> messageQueue = messages.get(user);
            for (Message message : messageQueue) {
                System.out.println(message.read(this));
            }
            messageQueue.clear();
        }
    }

    public void follow(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.getFollowers().remove(this);
        } else {
            following.add(user);
            user.getFollowers().add(this);
        }
    }

    public void like(Post post) {
        if (likedPosts.contains(post)) {
            likedPosts.remove(post);
            post.likedBy(this);
        } else {
            likedPosts.add(post);
            post.likedBy(this);
        }
    }

    public Post post(String content) {
        Post newPost = new Post(content);
        return newPost;
    }

    public Comment comment(Post post, String content) {
        Comment newComment = new Comment(this, content);
        post.commentBy(this, newComment);
        return newComment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User otherUser = (User) obj;
        return id == otherUser.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Map<User, Queue<Message>> getMessages() {
        return messages;
    }
}

class Message {
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.dateSent = new Date();
        this.seen = false;
    }

    public String read(User reader) {
        if (!sender.equals(reader)) {
            seen = true;
        }
        System.out.println("Sent at: " + dateSent);
        return content;
    }

    public boolean hasRead() {
        return seen;
    }
}

class Post {
    private Date datePosted;
    private String content;
    private Set<User> likes;
    private Map<User, List<Comment>> comments;

    public Post(String content) {
        this.datePosted = new Date();
        this.content = content;
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
    }

    public boolean likedBy(User user) {
        if (likes.contains(user)) {
            likes.remove(user);
            return false;
        } else {
            likes.add(user);
            return true;
        }
    }

    public boolean commentBy(User user, Comment comment) {
        if (!comments.containsKey(user))
            comments.put(user, new ArrayList<>());

        List<Comment> userComments = comments.get(user);
        return userComments.add(comment);
    }

    public String getContent() {
        System.out.println("Posted at: " + datePosted);
        return content;
    }

    public Comment getComment(User user, int index) {
        if (comments.containsKey(user)) {
            List<Comment> userComments = comments.get(user);
            if (index >= 0 && index < userComments.size()) {
                return userComments.get(index);
            }
        }
        return null;
    }

    public int getCommentCount() {
        int count = 0;
        for (List<Comment> userComments : comments.values()) {
            count += userComments.size();
        }
        return count;
    }

    public int getCommentCountByUser(User user) {
        if (comments.containsKey(user)) {
            List<Comment> userComments = comments.get(user);
            return userComments.size();
        }
        return 0;
    }
}


class Comment extends Post {
    public Comment(String content) {
        super(content);
    }

    public Comment(User user, String content) {
        super(content);
    }

}

class SocialNetwork {
    private static Map<User, List<Post>> postsByUsers = new HashMap<>();

    public static User register(String username, String email) {
        User user = new User(username, email);
        if (!postsByUsers.containsKey(user)) {
            postsByUsers.put(user, new ArrayList<>());
            return user;
        }
        return null;
    }

    public static Post post(User user, String content) {
        if (postsByUsers.containsKey(user)) {
            Post post = new Post(content);
            List<Post> userPosts = postsByUsers.get(user);
            userPosts.add(post);
            return post;
        }
        return null;
    }


    public static User getUser(String email) {
    for (User user : postsByUsers.keySet()) {
        if (user.getEmail().equals(email)) {
            return user;
        }
    }
    return null;
    }

    public static Set<Post> getFeed(User user) {
        Set<Post> feed = new HashSet<>();
        Set<User> following = user.getFollowing();
        for (User followedUser : following) {
            if (postsByUsers.containsKey(followedUser)) {
                List<Post> userPosts = postsByUsers.get(followedUser);
                feed.addAll(userPosts);
            }
        }
        return feed;
    }

    public static Map<User, String> search(String keyword) {
        Map<User, String> results = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            if (user.getUsername().contains(keyword)) {
                results.put(user, user.getUsername());
            }
        }
        return results;
    }

    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reversedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            Set<K> reverseSet = reversedMap.getOrDefault(value, new HashSet<>());
            reverseSet.add(key);
            reversedMap.put(value, reverseSet);
        }
        return reversedMap;
    }
}



