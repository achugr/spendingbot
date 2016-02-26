package ru.achugr.spendingbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * author: achugr
 * date: 30.01.16.
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @NotNull
    private final Integer id;
    @NotNull
    private final String userName;

    @Nullable
    private String firstName;
    @Nullable
    private String lastName;


}
