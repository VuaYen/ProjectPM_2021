import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Vendor } from '../../app/vendor/vendor';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class VendorService {
  private vendorsUrl: string;

  constructor(private http: HttpClient) {
    this.vendorsUrl = 'http://localhost:8081/api/vendors';
  }

  public findAll(): Observable<Vendor[]> {
    return this.http.get<Vendor[]>(this.vendorsUrl);
  }

  public save(vendor: Vendor) {
    return this.http.post<Vendor>(this.vendorsUrl, vendor);
  }
}
