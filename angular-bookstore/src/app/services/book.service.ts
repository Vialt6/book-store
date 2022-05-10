import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from '../common/book';
import { BookCategory } from '../common/book-category';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private baseUrl = "http://localhost:8080/books";
  private categoryUrl = "http://localhost:8080/book-category";

  constructor(private httpClient : HttpClient) { }


  getAllBooks(){
    return this.httpClient.get<Book[]>(this.baseUrl);
  }


  getBooks(theCategoryId: number): Observable<Book[]>{
    const searchUrl = `${this.baseUrl}/search/categoryid?id=${theCategoryId}`;
    return this.getBooksList(searchUrl);
  }

  getBooksPaginate(theCategoryId: number, currentPage: number, pageSize: number): Observable<Book[]>{
    const searchUrl = `${this.baseUrl}/search/categoryid?id=${theCategoryId}`;
    //&page=${currentPage}&size=${pageSize}
    return this.httpClient.get<Book[]>(searchUrl);
  }

  getBookCategories(): Observable<BookCategory[]>{
    return this.httpClient.get<BookCategory[]>(this.categoryUrl)
  }

  searchBooks(keyword: string, currentPage: number, pageSize: number): Observable<Book[]>{
    const searchUrl = `${this.baseUrl}/search/searchbykeyword?name=${keyword}`;
    //&page=${currentPage}&size=${pageSize}
    //return this.getBooksList(searchUrl);
    return this.httpClient.get<Book[]>(searchUrl);
  }


  private getBooksList(searchUrl: string): Observable<Book[]> {
    return this.httpClient.get<Book[]>(searchUrl);
  }

  get(bookId: number): Observable<Book> {
    const bookDetailsUrl = `${this.baseUrl}/${bookId}`;
    return this.httpClient.get<Book>(bookDetailsUrl);
  }



}

interface GetResponseBooks{
  id: number;
  sku: string;
  name: string;
  description: string;
  unitPrice: number;
  imageUrl: string;
  active: boolean;
  unitsInStock: number;
  createdOn: Date;
  updatedOn: Date;
}

interface GetResponseBookCategory{
  id: number;
  categoryName: string;
}
