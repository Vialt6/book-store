import { Book } from "./book";
import { Purchase } from "./purchase";

export class CartItem {
  id: number;
  related:number;
  name: string;
  imageUrl: string;
  unitPrice: number;
  quantity: number;
  bookId:number;


  constructor(book: Book){
      this.id = book.id;
      this.name = book.name;
      this.imageUrl = book.imageUrl;
      this.unitPrice = book.unitPrice;
      this.quantity = 1;
      this.bookId = book.id;
      /*
      this.bookId = {
        id: book.id,
        sku : book.sku,
        name : book.name,
        description : book.description,
        unitPrice : book.unitPrice,
        imageUrl : book.imageUrl,
        active : book.active,
        unitsInStock : book.unitsInStock,
        createdOn : book.createdOn,
        updatedOn : book.updatedOn
      };
*/
  }
}
