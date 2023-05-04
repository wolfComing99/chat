package com.wolf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wolf
 * @since 2023-04-28
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer messageId;

    private Integer sendId;

    private Integer receiveId;

    private String content;

    private LocalDateTime createdDate;

    /**
     * 1已读  0未读
     */
    private Integer isRead;

    private Integer dialogue;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }
    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
    public Integer getDialogue() {
        return dialogue;
    }

    public void setDialogue(Integer dialogue) {
        this.dialogue = dialogue;
    }

    @Override
    public String toString() {
        return "Message{" +
            "messageId=" + messageId +
            ", sendId=" + sendId +
            ", receiveId=" + receiveId +
            ", content=" + content +
            ", createdDate=" + createdDate +
            ", isRead=" + isRead +
            ", dialogue=" + dialogue +
        "}";
    }
}
