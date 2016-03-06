package ru.achugr.spendingbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * author: achugr
 * date: 30.01.16.
 */

@Getter
public class User {
    @NotNull
    private final Integer id;

    @NotNull
    private String userName;

    @Nullable
    private String firstName;
    @Nullable
    private String lastName;

    public User(@NotNull Integer id, @NotNull String userName, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(@NotNull Integer id, @NotNull String userName) {
        this.id = id;
        this.userName = userName;
    }
}
