import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { concat } from 'rxjs';
import { CartItem } from 'src/app/common/cart-item';
import { Country } from 'src/app/common/country';
import { Email } from 'src/app/common/email';
import { Purchase } from 'src/app/common/purchase';
import { State } from 'src/app/common/state';
import { CartService } from 'src/app/services/cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { UserService } from 'src/app/services/user.service';
import { ShopValidators } from 'src/app/validators/shop-validators';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  [x: string]: any;

  cartItems: CartItem[] = [];
  totalPrice: number = 0;
  totalQuantity: number = 0;
  checkoutFormGroup: FormGroup;
  purchase: Purchase= new Purchase();
  purchases: Purchase[] = [];

  email: FormControl;
  emailString: string = "";
  name:string;



  creditCardYears: number[] = [];
  creditCardMonths: number[] = [];

  countries: Country[] = [];

  shippingAddressStates: State[] = [];
  billingAddressStates: State[] = [];

  constructor(private formBuilder: FormBuilder,
              private _cartService: CartService,
              private _checkoutService: CheckoutService,
              private _userService: UserService,
              private router: Router) { }

  ngOnInit() {
    this.cartDetails();

    this._userService.getEmail().subscribe(
      email => {

        this._userService.getName().subscribe(
          name =>{
            this.checkoutFormGroup = this.formBuilder.group({
              customer: this.formBuilder.group({
                firstName: new FormControl(name.match(/^\S*/)[0],[Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
                lastName: new FormControl(name.split(" ").pop(),[Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
                email: new FormControl(email,[Validators.required, Validators.minLength(2), Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])
              }),
              shippingAddress: this.formBuilder.group({
                country: new FormControl('', [Validators.required]),
                street: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
                city: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
                state: new FormControl('', [Validators.required]),
                zipcode: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces])
              }),
              billingAddress: this.formBuilder.group({
                country: new FormControl('', [Validators.required]),
                street: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
                city: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
                state: new FormControl('', [Validators.required]),
                zipcode: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces])
              }),
              creditCard: this.formBuilder.group({
                cardType: new FormControl('', [Validators.required]),
                nameOnCard:new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
                cardNumber: new FormControl('', [Validators.required, Validators.pattern('[0-9]{16}')]),
                cvv: new FormControl('', [Validators.required, Validators.pattern('[0-9]{3}')]),
                expirationMonth: [''],
                expirationYear: [''],
              }),
            })
          }
        )
      }
    );
    console.log(this.emailString);

    this.checkoutFormGroup = this.formBuilder.group({
      customer: this.formBuilder.group({
        firstName: new FormControl('',[Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
        lastName: new FormControl('',[Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
        email: new FormControl(`${this.emailString}`,[Validators.required, Validators.minLength(2), Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])
      }),
      shippingAddress: this.formBuilder.group({
        country: new FormControl('', [Validators.required]),
        street: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
        city: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
        state: new FormControl('', [Validators.required]),
        zipcode: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces])
      }),
      billingAddress: this.formBuilder.group({
        country: new FormControl('', [Validators.required]),
        street: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
        city: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
        state: new FormControl('', [Validators.required]),
        zipcode: new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces])
      }),
      creditCard: this.formBuilder.group({
        cardType: new FormControl('', [Validators.required]),
        nameOnCard:new FormControl('', [Validators.required, Validators.minLength(2), ShopValidators.notOnlyWhiteSpaces]),
        cardNumber: new FormControl('', [Validators.required, Validators.pattern('[0-9]{16}')]),
        cvv: new FormControl('', [Validators.required, Validators.pattern('[0-9]{3}')]),
        expirationMonth: [''],
        expirationYear: [''],
      }),
    })

    const startMonth: number = new Date().getMonth() + 1;

    console.log("startMonth: " + startMonth);

    this._checkoutService.getCreditCardMonths(startMonth).subscribe(
      data => {

        this.creditCardMonths = data;
      }
    );

    // populate credit card years

    this._checkoutService.getCreditCardYears().subscribe(
      data => {

        this.creditCardYears = data;
      }
    );

    // populate countries

    this._checkoutService.getCountries().subscribe(
      data => {

        this.countries = data;
      }
    );
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
  }



  onSubmit() {
    console.log('Purchase the books');
    console.log(this.checkoutFormGroup.get('customer').value);
    console.log("Emial is", this.checkoutFormGroup.get('customer').value.email);
    this.purchase.totalPrice= this.totalPrice;
    this._cartService.totalPrice.subscribe(
      data => this.purchase.totalPrice = data
    );
    this.purchase.purchaseTime = new Date();
    this._userService.getEmail().subscribe(
      email =>
        this.purchase.user = email

    )


    this._checkoutService.addPurchase(this.purchase).subscribe(
      data => {

        alert(`your order has been recieved.\n order tracking number: ${data.id}`);
        this.resetCart();
      },
      error =>{
        alert(`there was a error: ${error.message}`);
      }
    )

  }

  copyShippingAddressToBillingAddress(event) {

    if (event.target.checked) {
      this.checkoutFormGroup.controls.billingAddress
        .setValue(this.checkoutFormGroup.controls.shippingAddress.value);
    }else {
      this.checkoutFormGroup.controls.billingAddress.reset();
    }

  }

  handleMonthsAndYears() {

    const creditCardFormGroup = this.checkoutFormGroup.get('creditCard');

    const currentYear: number = new Date().getFullYear();
    const selectedYear: number = Number(creditCardFormGroup.value.expirationYear);

    // if the current year equals the selected year, then start with the current month

    let startMonth: number;

    if (currentYear === selectedYear) {
      startMonth = new Date().getMonth() + 1;
    }
    else {
      startMonth = 1;
    }

    this._checkoutService.getCreditCardMonths(startMonth).subscribe(
      data => {

        this.creditCardMonths = data;
      }
    );
  }

  getStates(formGroupName: string) {

    const formGroup = this.checkoutFormGroup.get(formGroupName);

    const countryCode = formGroup.value.country.code;
    const countryName = formGroup.value.country.name;

    console.log(`{formGroupName} country code: ${countryCode}`);
    console.log(`{formGroupName} country name: ${countryName}`);

    this._checkoutService.getStates(countryCode).subscribe(
      data => {

        if (formGroupName === 'shippingAddress') {
          this.shippingAddressStates = data;
        }
        else {
          this.billingAddressStates = data;
        }

        // select first item by default
        formGroup.get('state').setValue(data[0]);
      }
    );
  }

  resetCart() {
    // reset cart data
    this._cartService.cartItems = [];
    this._cartService.totalPrice.next(0);
    this._cartService.totalQuantity.next(0);

    // reset the form
    this.checkoutFormGroup.reset();

    // navigate back to the products page
    this.router.navigateByUrl('/category/1');
  }
}
