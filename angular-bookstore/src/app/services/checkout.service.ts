import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, pipe } from 'rxjs';
import { Country } from '../common/country';
import { Purchase } from '../common/purchase';
import { State } from '../common/state';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  private countriesUrl = 'http://localhost:8080/countries';
  private statesUrl = 'http://localhost:8080/states';
  private purchaseUrl = 'http://localhost:8080/purchases';
  private pipUrl = 'http://localhost:8080/productInPurchase';

  constructor(private httpClient: HttpClient) { }

  getCountries(): Observable<GetResponseCountries[]> {
    return this.httpClient.get<GetResponseCountries[]>(this.countriesUrl);
  }

  getStates(theCountryCode: string): Observable<GetResponseStates[]> {

    // search url
    const searchStatesUrl = `${this.statesUrl}/search/findByCountryCode?code=${theCountryCode}`;

    return this.httpClient.get<GetResponseStates[]>(searchStatesUrl);
  }

  getCreditCardMonths(startMonth: number): Observable<number[]> {

    let data: number[] = [];

    // build an array for "Month" dropdown list
    // - start at current month and loop until

    for (let theMonth = startMonth; theMonth <= 12; theMonth++) {
      data.push(theMonth);
    }

    return of(data);
  }

  getCreditCardYears(): Observable<number[]> {

    let data: number[] = [];

    // build an array for "Year" downlist list
    // - start at current year and loop for next 10 years

    const startYear: number = new Date().getFullYear();
    const endYear: number = startYear + 10;

    for (let theYear = startYear; theYear <= endYear; theYear++) {
      data.push(theYear);
    }

    return of(data);
  }

  addPurchase(purchase: Purchase): Observable<Purchase> {
    const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});
  //  const newPurchase = {id: 1, putchaseTime: undefined, user : 1};

    //const body = JSON.stringify(purchase);
    return this.httpClient.post<Purchase>(this.purchaseUrl, purchase /*{'headers':headers}*/);
  }

  getPurchase(purchase: Purchase): Observable<GetResponsePurchases[]>{
    const searchStatesUrl = `${this.statesUrl}/purchase/${purchase.id}`
    return this.httpClient.get<GetResponsePurchases[]>(searchStatesUrl);
  }



}

interface GetResponseCountries{
  id: number;
  code: string;
  name: string;
}

interface GetResponseStates{
  id: number;
  name: string;
}

interface GetResponsePurchases{
  id:number;
  purchaseTime: Date;
  user: number;
}

interface GetResponseCart{
  id: number;
  related:Purchase;
  name: string;
  imageUrl: string;
  unitPrice: number;
  quantity: number;
  bookId:number;
}
