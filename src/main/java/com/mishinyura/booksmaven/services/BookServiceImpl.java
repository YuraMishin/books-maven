package com.mishinyura.booksmaven.services;

import com.mishinyura.booksmaven.dto.BookReqDto;
import com.mishinyura.booksmaven.dto.BookResDto;
import com.mishinyura.booksmaven.exceptions.BookNotCreatedException;
import com.mishinyura.booksmaven.exceptions.BookNotFoundException;
import com.mishinyura.booksmaven.models.Book;
import com.mishinyura.booksmaven.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long getBooksCount() {
        return bookRepository.count();
    }

    @Override
    public List<BookResDto> findAllBooks() {
        var books = bookRepository.findAll();
        return modelMapper
                .map(books, new TypeToken<List<BookResDto>>() {
                }.getType());
    }

    @Transactional
    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public BookResDto findBookById(Long id) {
        var bookFound = bookRepository.findById(id);
        var book = bookFound.orElseThrow(BookNotFoundException::new);
        return modelMapper.map(book, BookResDto.class);
    }

    @Override
    public String findBookByIdMVC(Model model, Long id, String page) {
        var bookFound = bookRepository.findById(id);
        if (bookFound.isPresent()) {
            var bookResDto = modelMapper.map(bookFound.get(), BookResDto.class);
            model.addAttribute("book", bookResDto);
            return page;
        }
        return "redirect:/books/";
    }

    @Transactional
    @Override
    public BookResDto createBook(BookReqDto book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new BookNotCreatedException(errorMsg.toString().trim());
        }
        var bookToSave = modelMapper.map(book, Book.class);
        var bookSaved = bookRepository.save(bookToSave);
        return modelMapper.map(bookSaved, BookResDto.class);
    }

    @Transactional
    @Override
    public void createBookMVC(BookReqDto book) {
        bookRepository.save(modelMapper.map(book, Book.class));
    }

    @Transactional
    @Override
    public void updateBook(Long id, BookReqDto book) {
        bookRepository.findById(id)
                .ifPresent(bookToSave -> {
                    modelMapper.map(book, bookToSave);
                    bookRepository.save(bookToSave);
                });
    }
}
