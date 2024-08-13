import {
  Component,
  OnInit
} from '@angular/core';
import {
  UntypedFormControl,
  UntypedFormGroup,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/constants/app.constants';
import { ProductType } from 'src/app/enums/product-type';
import { Product } from 'src/app/models/product';
import { ImageService } from 'src/app/_services/image/image.service';
import { ProductService } from 'src/app/_services/product/product.service';
import { TokenStorageService } from 'src/app/_services/token/token-storage.service';

@Component({
  selector: 'app-product-add',
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.css']
})
export class ProductAddComponent implements OnInit {

  HOME = AppConstants.HOME_URL;

  foodTypes = ProductType;
  selectedFile: File;
  message: string;

  form = new UntypedFormGroup({
    name: new UntypedFormControl('', [Validators.required, Validators.minLength(3)]),
    content: new UntypedFormControl('', [Validators.required, Validators.minLength(5)]),
    volume: new UntypedFormControl('', [Validators.required , Validators.min(1)]),
    foodType: new UntypedFormControl('', [Validators.required]),
    price: new UntypedFormControl('', [Validators.required, Validators.min(0.01),Validators.max(10) ]) });

  constructor(
    private productService: ProductService,
    private router: Router,
    private tokenStorageService: TokenStorageService,
    private imageService: ImageService
  ) { }


  private roles: string[];
  isLoggedIn = false;
  isAdmin = false;
  username: string;

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
  
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.isAdmin = this.roles.includes('ROLE_ADMIN');
      this.username = user.displayName;
    }
  
    // Mark all controls as touched on initialization
    Object.keys(this.form.controls).forEach(control => {
      this.form.controls[control].markAsTouched();
    });
  }
  redirecting() {
    if (!this.isAdmin && !this.isLoggedIn) {
      this.router.navigate([this.HOME]);
    }
  }

  submitHandler(): void {
    let product = new Product();

    product.name = this.form.controls['name'].value;
    product.content = this.form.controls['content'].value;
    product.volume = this.form.controls['volume'].value;
    product.price = this.form.controls['price'].value;
    product.type = this.form.controls['foodType'].value;

    if (this.selectedFile) {
      this.onUpload(product.name);
    }
  
    this.productService.addProduct(product);
    this.router.navigate([AppConstants.HOME_URL]).then(() => {
      window.location.reload(),this.imageService.getImage(product.name);
    });
  }

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
  }

  onUpload(name: string) {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, name);
    this.imageService.uploadImage(uploadImageData);
  }

}
