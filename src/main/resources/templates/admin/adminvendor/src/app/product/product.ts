import {Vendor} from '../vendor/vendor';
import {Category} from './Category';

export class Product {
  productnumber: string;
  name: string;
  description: string;
  price: string;
  photo: string;
  createdDate: string;
  status: string;
  category: Category;
  vendor: Vendor;

}
