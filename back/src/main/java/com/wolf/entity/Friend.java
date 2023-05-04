package com.wolf.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wolf
 * @since 2023-04-23
 */
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private Integer friendId;

    private Integer intimacy;

    private String remark;

    private Integer dialogue;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }
    public Integer getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(Integer intimacy) {
        this.intimacy = intimacy;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getDialogue() {
        return dialogue;
    }

    public void setDialogue(Integer dialogue) {
        this.dialogue = dialogue;
    }

    @Override
    public String toString() {
        return "Friend{" +
            "uid=" + uid +
            ", friendId=" + friendId +
            ", intimacy=" + intimacy +
            ", remark=" + remark +
            ", dialogue=" + dialogue +
        "}";
    }
}
