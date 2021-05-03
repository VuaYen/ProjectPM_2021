import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { VendorFormComponent } from './vendor/vendor-form/vendor-form.component';
import { VendorListComponent } from './vendor/vendor-list/vendor-list.component';
import { ProductFormComponent } from './product/product-form/product-form.component';
import { ProductListComponent } from './product/product-list/product-list.component';
import { UserFormComponent } from './user/user-form/user-form.component';
import { UserListComponent } from './user/user-list/user-list.component';
import {UserService} from './user/user-service';
import {FormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import {VendorService} from './vendor/vendor-service';
import {ProductService} from './product/product-service';

@NgModule({
  declarations: [
    AppComponent,
    VendorFormComponent,
    VendorListComponent,
    ProductFormComponent,
    ProductListComponent,
    UserFormComponent,
    UserListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [UserService, VendorService, ProductService],
  bootstrap: [AppComponent]
})
export class AppModule { }
