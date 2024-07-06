package model.wishlist;

public class WishlistBean {
    private long id;
    private long userId;

    public WishlistBean() {
    }

    public WishlistBean(long id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}