package com.mishinyura.booksmaven.utils;

import com.mishinyura.booksmaven.dto.BookReqDto;
import com.mishinyura.booksmaven.dto.BookResDto;
import com.mishinyura.booksmaven.entities.Book;
import com.mishinyura.booksmaven.utils.mappers.BookMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests BookMapper")
@ActiveProfiles("test")
class BookMapperTest {
    @DisplayName("tests bookDtoReqToBook()")
    @Test
    void shouldTestBookDtoReqToBook() {
        // given
        var bookReqDto = new BookReqDto();
        bookReqDto.setTitle("Title1");

        // when
        var book = BookMapper.INSTANCE.mapToBook(bookReqDto);

        // then
        assertThat(book)
                .isNotNull()
                .isInstanceOf(Book.class);
        assertThat(book.getTitle()).isEqualTo(bookReqDto.getTitle());
    }

    @DisplayName("tests bookToBookResDto()")
    @Test
    void shouldTestBookToBookResDto() {
        // given
        var book = new Book().setTitle("Title1");

        // when
        var bookResDto = BookMapper.INSTANCE.mapToBookResDto(book);

        // then
        assertThat(bookResDto)
                .isNotNull()
                .isInstanceOf(BookResDto.class);
        assertThat(bookResDto.getTitle()).isEqualTo(book.getTitle());
    }

    @DisplayName("tests getListOfBookResDto()")
    @Test
    void shouldTestGetListOfBookResDto() {
        // given
        var books = new ArrayList<Book>(List.of(
                new Book().setTitle("Title1"))
        );

        // when
        var listBookResDto = BookMapper.INSTANCE.getListOfBookResDto(books);

        // then
        assertThat(listBookResDto)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .isNotEmpty()
                .hasSize(1)
                .map(BookResDto::getTitle)
                .contains("Title1");
    }
}
