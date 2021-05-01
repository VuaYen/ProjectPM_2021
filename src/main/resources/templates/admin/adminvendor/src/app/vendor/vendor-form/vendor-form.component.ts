import { Component, OnInit } from '@angular/core';
import {Vendor} from '../vendor';
import {ActivatedRoute, Router} from '@angular/router';
import {VendorService} from '../vendor-service';


@Component({
  selector: 'app-vendor-form',
  templateUrl: './vendor-form.component.html',
  styleUrls: ['./vendor-form.component.css']
})
export class VendorFormComponent {
  vendor: Vendor;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vendorService: VendorService) {
    this.vendor = new Vendor();
  }

  onSubmit() {
    this.vendorService.save(this.vendor).subscribe(result => this.gotoVendorList());
  }

  gotoVendorList() {
    this.router.navigate(['/vendors']);
  }

}
