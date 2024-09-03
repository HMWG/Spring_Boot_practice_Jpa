package com.grepp.jpa.model.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class UserChatRoomId implements Serializable {

    private Long user;
    private Long room;

    public UserChatRoomId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChatRoomId that = (UserChatRoomId) o;
        if(!Objects.equals(user, that.user)) return false;

        return Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }
}
