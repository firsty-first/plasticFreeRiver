package com.example.plasticfreeriver;

public class postWithoutstatus {
    private String postId;
    private String img;
    private String postedBy;
    private String title;
    private String postedAt;

    private String geotag_url;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private  String count;

public  postWithoutstatus()
{

}
    public postWithoutstatus(String postId, String img, String postedBy, String title, String postedAt, String status, String geotag_url) {
        this.postId = postId;
        this.img = img;
        this.postedBy = postedBy;
        this.title = title;
        this.postedAt = postedAt;

        this.geotag_url = geotag_url;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }


    public String getGeotag_url() {
        return geotag_url;
    }

    public void setGeotag_url(String geotag_url) {
        this.geotag_url = geotag_url;
    }
}

