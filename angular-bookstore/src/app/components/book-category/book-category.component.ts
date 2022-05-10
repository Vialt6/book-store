import { Component, OnInit } from '@angular/core';
import { BookCategory } from 'src/app/common/book-category';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-book-category',
  templateUrl: './book-category.component.html',
  styleUrls: ['./book-category.component.css']
})
export class BookCategoryComponent implements OnInit {
  bookCategories: BookCategory[];

  constructor(private _bookService: BookService) { }

  ngOnInit(): void {
    this.listBookCategories();
  }

  listBookCategories(){
    this._bookService.getBookCategories().subscribe(
      data => this.bookCategories = data
    );
    console.log(this.bookCategories);
  }
}
