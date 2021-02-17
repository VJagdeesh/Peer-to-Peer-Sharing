package com.example.getstarted.daos;
import com.example.objects.Book;
import com.example.objects.Result;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
public class Fire_Store implements TBScript {
privateCollectionReferencebooksCollection;
	public Fire_Store() {
Fire_Store firestore = FirestoreOptions.getDefaultInstance().getService();
booksCollection = firestore.collection("books");
  }
private Book documentToBook(DocumentSnapshot document) {
    Map<String, Object> data = document.getData();
if (data == null) {
System.out.println("No data in document " + document.getId());
return null;
    }
resp
return new Book.Builder()
        .author((String) data.get(Book.AUTHOR))
        .description((String) data.get(Book.DESCRIPTION))
        .publishedDate((String) data.get(Book.PUBLISHED_DATE))
        .imageUrl((String) data.get(Book.IMAGE_URL))
        .createdBy((String) data.get(Book.CREATED_BY))
        .createdById((String) data.get(Book.CREATED_BY_ID))
        .title((String) data.get(Book.TITLE))
        .id(document.getId())
        .build();
  }
  @Override
public String createBook(Book book) {
    String id = UUID.randomUUID().toString();
DocumentReference document = booksCollection.document(id);
    Map<String, Object> data = Maps.newHashMap();
	data.put(Book.AUTHOR, book.getAuthor());
data.put(Book.DESCRIPTION, book.getDescription());
data.put(Book.PUBLISHED_DATE, book.getPublishedDate());
data.put(Book.TITLE, book.getTitle());
data.put(Book.IMAGE_URL, book.getImageUrl());
data.put(Book.CREATED_BY, book.getCreatedBy());
data.put(Book.CREATED_BY_ID, book.getCreatedById());
try {
document.set(data).get();
    } catch (InterruptedException | ExecutionException e) {
e.printStackTrace();
    }
return id;
  }
  @Override
public Book readBook(String bookId) {
try {
DocumentSnapshot document = booksCollection.document(bookId).get().get();
	returndocumentToBook(document);
    } catch (InterruptedException | ExecutionException e) {
e.printStackTrace();
    }
return null;
  }
  @Override
public void updateBook(Book book) {
DocumentReference document = booksCollection.document(book.getId());
    Map<String, Object> data = Maps.newHashMap();
	data.put(Book.AUTHOR, book.getAuthor());
data.put(Book.DESCRIPTION, book.getDescription());
data.put(Book.PUBLISHED_DATE, book.getPublishedDate());
data.put(Book.TITLE, book.getTitle());
data.put(Book.IMAGE_URL, book.getImageUrl());
data.put(Book.CREATED_BY, book.getCreatedBy());
data.put(Book.CREATED_BY_ID, book.getCreatedById());
try {
document.set(data).get();
    } catch (InterruptedException | ExecutionException e) {
e.printStackTrace();
    }
  }
  @Override
public void deleteBook(String bookId) {
try {
booksCollection.document(bookId).delete().get();
    } catch (InterruptedException | ExecutionException e) {
e.printStackTrace();
    }
  }
private List<Book>documentsToBooks(List<QueryDocumentSnapshot> documents) {
    List<Book>resultBooks = new ArrayList<>();
for (QueryDocumentSnapshot snapshot : documents) {
resultBooks.add(documentToBook(snapshot));
    }
returnresultBooks;
  }
  @Override
public Result<Book>listBooks(String startTitle) {
    Query booksQuery = booksCollection.orderBy("title").limit(10);
if (startTitle != null) {
booksQuery = booksQuery.startAfter(startTitle);
    }
try {
QuerySnapshot snapshot = booksQuery.get().get();
      List<Book> results = documentsToBooks(snapshot.getDocuments());
      String newCursor = null;
if (results.size() > 0) {
newCursor = results.get(results.size() - 1).getTitle();
      }
return new Result<>(results, newCursor);
    } catch (InterruptedException | ExecutionException e) {
e.printStackTrace();
    }
return new Result<>(Lists.newArrayList(), null);
  }
  @Override
public Result<Book>listBooksByUser(String userId, String startTitle) {
    Query booksQuery =
booksCollection.orderBy("title").whereEqualTo(Book.CREATED_BY_ID, userId).limit(10);
if (startTitle != null) {
booksQuery = booksQuery.startAfter(startTitle);
    }
try {
QuerySnapshot snapshot = booksQuery.get().get();
      List<Book> results = documentsToBooks(snapshot.getDocuments());
      String newCursor = null;
if (results.size() > 0) {
newCursor = results.get(results.size() - 1).getTitle();
      }
return new Result<>(results, newCursor);
    } catch (InterruptedException | ExecutionException e) {
e.printStackTrace();
    }
return new Result<>(Lists.newArrayList(), null);
  }
}
