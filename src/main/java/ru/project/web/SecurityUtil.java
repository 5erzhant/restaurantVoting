package ru.project.web;

import ru.project.model.AbstractBaseEntity;

public class SecurityUtil {
    private static int id = AbstractBaseEntity.START_SEQ;
    private static int lateTime = 11;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static int getLateTime() {
        return SecurityUtil.lateTime;
    }

    public static void setLateTime(int lateTime) {
        SecurityUtil.lateTime = lateTime;
    }
}
