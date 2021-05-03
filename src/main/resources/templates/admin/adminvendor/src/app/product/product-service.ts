import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import {Product} from './product';

@Injectable()
export class ProductService {
  private productssUrl: string;

  constructor(private http: HttpClient) {
    this.productssUrl = 'http://localhost:8081/api/product';
  }

  public findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productssUrl);
  }

  // tslint:disable-next-line:typedef
  public save(product: Product) {
    return this.http.post<Product>(this.productssUrl, product);
  }
}
