import { HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookCategoryComponent } from './components/book-category/book-category.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { BookService } from './services/book.service';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import { SearchComponent } from './components/search/search.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { CartStatusComponent } from './components/cart-status/cart-status.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializeKeycloak } from './utility/app.init';
import { AuthGuard } from './utility/app.guard';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';

const routes: Routes = [
  {path: 'checkout', component: CheckoutComponent},
  {path: 'cart-details', component: CartDetailsComponent},
  {path: 'books/:id', component: BookDetailsComponent},
  {path: 'books', component: BookListComponent},
  {path: 'search/:keyword', component: BookListComponent},
  {path: 'category/:id', component: BookListComponent},
  {path: '', component: BookListComponent ,canActivate: [AuthGuard]},
  {path: '**', component: PageNotFoundComponent}
];





@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    BookCategoryComponent,
    BookDetailsComponent,
    SearchComponent,
    PageNotFoundComponent,
    CartDetailsComponent,
    CartStatusComponent,
    CheckoutComponent,
    EditProfileComponent,

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    NgbModule,
    ReactiveFormsModule,
    KeycloakAngularModule

  ],
  providers: [
    BookService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

