import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Book } from '../common/book';
import { CartItem } from '../common/cart-item';
import { Purchase } from '../common/purchase';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  cartItems: CartItem[] = [];
  totalPrice: Subject<number> = new Subject<number>();
  totalQuantity: Subject<number> = new Subject<number>();

  private pipUrl = 'http://localhost:8080/productInPurchase';

  constructor(private httpClient: HttpClient) { }

  addToCart(theCartItem: CartItem):Observable<CartItem>{
    //check already item in the cart
    let alreadyExistsInCart: boolean = false;
    let existingCartItem: CartItem = undefined;

    if(this.cartItems.length > 0){
      //find the item in the cart based on id

      existingCartItem = this.cartItems.find( tempCartItem => tempCartItem.id === theCartItem.id);

      //check if we found it
      alreadyExistsInCart = (existingCartItem != undefined)
    }

    if(alreadyExistsInCart){
      //increment the quantity
      existingCartItem.quantity++;
    }else {
      //add to cart item array
      this.cartItems.push(theCartItem);
    }

    this.calculateTotalPrice();


    const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});

    const body = JSON.stringify(theCartItem);
    return this.httpClient.post<CartItem>(this.pipUrl, theCartItem);

  }

  calculateTotalPrice() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for(let currentCartItem of this.cartItems){
      totalPriceValue += currentCartItem.quantity * currentCartItem.unitPrice;
      totalQuantityValue += currentCartItem.quantity;
    }

    console.log(`Total price: ${totalPriceValue}, Total quantity: ${totalQuantityValue}`);

    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
  }

  decrementQuantity(cartItem: CartItem){
    cartItem.quantity--;

    if (cartItem.quantity === 0) {
      this.remove(cartItem);
    }else {
      this.calculateTotalPrice();
    }

  }

  remove(cartItem: CartItem){
    const itemIndex = this.cartItems
                          .findIndex(
                            tempCartItem => tempCartItem.id === cartItem.id
                          );

    if (itemIndex > -1) {
      this.cartItems.splice(itemIndex, 1);
      this.calculateTotalPrice();
    }
    //return this.httpClient.delete<CartItem>(this.pipUrl);

  }

  getPip(cartItem:CartItem):Observable<CartItem>{
    const url = `${this.pipUrl}/${cartItem.id}`;
    return this.httpClient.get<CartItem>(url);
  }

  updatePip(book:Book,cartItem:CartItem):Observable<any>{
    cartItem.bookId=book.id;
    return this.httpClient.put(this.pipUrl,cartItem);
  }


}



interface GetResponseCart{
  id: number;
  related:Purchase;
  name: string;
  imageUrl: string;
  unitPrice: number;
  quantity: number;
  bookId:Book;
}
