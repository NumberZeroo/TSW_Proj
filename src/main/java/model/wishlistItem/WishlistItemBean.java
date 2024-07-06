package model.wishlistItem;

public class WishlistItemBean {
    private long id;
    private long wishlistId;
    private long productId;

    public WishlistItemBean() {
    }

    public WishlistItemBean(long id, long wishlistId, long productId) {
        this.id = id;
        this.wishlistId = wishlistId;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}