import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface ReviewRequest {
  productName: string;
  productCode: string;
  brand: string;
  price: number;
}
@Component({
  selector: 'app-ask-review',
  templateUrl: './ask-review.component.html',
  styleUrls: ['./ask-review.component.sass'],
})
export class AskReviewComponent {
  reviewRequest: ReviewRequest = {
    productName: '',
    productCode: '',
    brand: '',
    price: 0,
  };

  constructor(private router: Router, private http: HttpClient) {}

  submitRequest(): void {
    const productCode = this.reviewRequest.productCode;
    const existingProductUrl = `http://localhost:3002/products/code/${productCode}`;

    this.http.get(existingProductUrl).subscribe({
      next: (response) => {
        alert('Product code already exists. Please choose a different code.');

        setTimeout(() => {
          this.router.navigate(['/reviews/', productCode]);
        }, 30000);
      },
      error: (error) => {
        const createProductUrl = 'http://localhost:3002/products';
        const headers = new HttpHeaders().set(
          'Content-Type',
          'application/json'
        );

        this.http
          .post(createProductUrl, this.reviewRequest, { headers })
          .subscribe({
            next: (response) => {
              console.log('Product created:', response);
              alert('Product added for review');

              this.router.navigate(['/reviews/', productCode]);
            },
            error: (error) => {
              console.log('Error creating product:', error);
              alert('Product added for review');
              this.router.navigate(['/reviews/', productCode]);
            },
          });
      },
    });
  }
}
