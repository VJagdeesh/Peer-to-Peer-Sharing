package com.example.daos;
import com.example.objects.Book;
import com.example.objects.Result;
public interface TBScript {
  String createBook(Book book);
  Book readBook(String bookId);
void updateBook(Book book);
void deleteBook(String bookId);
  Result<Book>listBooks(String startcursor);
  Result<Book>listBooksByUser(String userId, String startCursor);
}

