import { HttpClient, } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = "http://localhost:8080/users";

  constructor(private http: HttpClient) { }


  public getName():Observable<string>{
    const nameUrl = `${this.userUrl}/name`;
    return this.http.get(nameUrl, {responseType: 'text'});
  }

  public getEmail():Observable<string>{
    const emailUrl = `${this.userUrl}/email`;
    //{responseType: 'text'}
    return this.http.get(emailUrl,{responseType: 'text'});
  }


}

