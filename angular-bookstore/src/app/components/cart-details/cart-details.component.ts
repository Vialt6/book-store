import { Component, OnInit } from '@angular/core';
import { CartItem } from 'src/app/common/cart-item';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart-details',
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.css']
})
export class CartDetailsComponent implements OnInit {
  cartItems: CartItem[] = [];
  totalPrice: number = 0;
  totalQuantity: number = 0;

  constructor(private _cartService: CartService) { }

  ngOnInit() {
    this.cartDetails();
  }

  cartDetails(){
    this.cartItems = this._cartService.cartItems;

    this._cartService.totalPrice.subscribe(
      data => this.totalPrice = data
    );

    this._cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data
    );

    this._cartService.calculateTotalPrice();


/*
    for (let i = 0; i < this.cartItems.length; i++) {
      this._cartService.addToCart(this.cartItems[i])
      //.subscribe(

        data => {
           this.cartItems[i]=data
        },
        error =>{
          console.log(`there was a error`);
        }

      //)
    }
    */

  }

  incrementQuantity(cartItem: CartItem){
    this._cartService.addToCart(cartItem).subscribe(

      data => {
         cartItem=data
      },
      error =>{
        console.log(`there was a error`);
      }

    )

  }

  decrementQuantity(cartItem: CartItem){
    this._cartService.decrementQuantity(cartItem);
  }

  remove(cartItem: CartItem){
    this._cartService.remove(cartItem);
  }

}
