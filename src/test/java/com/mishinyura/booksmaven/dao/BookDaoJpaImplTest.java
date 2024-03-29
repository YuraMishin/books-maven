package com.mishinyura.booksmaven.dao;

import com.mishinyura.booksmaven.entities.Book;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests BookDaoJpaImpl")
@DataJpaTest
@Import(BookDaoJpaImpl.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
class BookDaoJpaImplTest {
    private final TestEntityManager em;

    private final BookDaoJpaImpl bookDaoJpa;

    @DisplayName("tests getBooksCount()")
    @Test
    void shouldGetBooksCount() {
        // given
        var expectedCount = 4L;

        // when
        var actualCount = bookDaoJpa.getBooksCount();

        // then
        assertThat(actualCount)
                .as("Error in count()")
                .isEqualTo(expectedCount);
    }

    @DisplayName("tests findAllBooks()")
    @Test
    void shouldFindAllBooks() {
        // when
        var books = bookDaoJpa.findAllBooks();

        // then
        assertThat(books)
                .isNotEmpty()
                .hasSize(4)
                .map(Book::getTitle)
                .contains("Title_liquibase1");
    }
}
