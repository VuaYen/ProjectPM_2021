import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from './user/user-list/user-list.component';
import {UserFormComponent} from './user/user-form/user-form.component';
import {NgModule} from '@angular/core';
import {VendorListComponent} from './vendor/vendor-list/vendor-list.component';
import {VendorFormComponent} from './vendor/vendor-form/vendor-form.component';
import {ProductListComponent} from './product/product-list/product-list.component';
import {ProductFormComponent} from './product/product-form/product-form.component';

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'adduser', component: UserFormComponent },
  { path: 'vendors', component: VendorListComponent },
  { path: 'addVendor', component: VendorFormComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'addProduct', component: ProductFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
