import { CartItem } from "./cart-item";

export class Purchase {
  id:number;
  totalPrice:number;
  purchaseTime: Date;
  user: string;
  cartItems:CartItem[];

  constructor(){

  }

}
