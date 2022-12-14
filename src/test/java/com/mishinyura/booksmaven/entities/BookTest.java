package com.mishinyura.booksmaven.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests Book")
@ActiveProfiles("test")
class BookTest {
    @DisplayName("tests getTitle()")
    @Test
    void shouldTestGetTitle() {
        // given
        var titleExpected = "Title";
        var book = new Book()
                .setTitle(titleExpected);

        // when
        var titleActual = book.getTitle();

        // then
        assertThat(titleActual).isEqualTo(titleExpected);
    }
}
