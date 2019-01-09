package org.javatraining.voteforlunch;

import org.javatraining.voteforlunch.model.User;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {
    public static <T> void assertMatch(T actual, T expected, String...fields ) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, fields);
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected, String...fields) {
        assertThat(actual).usingElementComparatorIgnoringFields(fields).isEqualTo(expected);

    }
}
