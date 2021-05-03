import { Component, OnInit } from '@angular/core';
import {Vendor} from '../vendor';
import {VendorService} from '../vendor-service';

@Component({
  selector: 'app-vendor-list',
  templateUrl: './vendor-list.component.html',
  styleUrls: ['./vendor-list.component.css']
})
export class VendorListComponent implements OnInit {

  vendors: Vendor[];

  constructor(private vendorService: VendorService) {
  }

  ngOnInit() {
    this.vendorService.findAll().subscribe(data => {
      this.vendors = data;
    });
  }

}
