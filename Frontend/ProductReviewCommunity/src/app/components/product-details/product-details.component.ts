import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';

interface Product {
  productName: string;
  productCode: string;
  brand: string;
  price: number;
}

interface Review {
  heading: string;
  rating: number;
  review: string;
}

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.sass'],
})
export class ProductPageComponent implements OnInit {
  product: Product | undefined;
  reviews: Review[] = [];
  private productSubscription: Subscription | undefined;
  private reviewSubscription: Subscription | undefined;

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      console.log(params['id']);
      const productCode = params['id'];
      if (productCode) {
        this.fetchProduct(productCode);
        this.fetchReviews(productCode);
      }
    });
  }

  fetchProduct(productCode: string): void {
    console.log('Fetching product...');
    this.productSubscription = this.http
      .get<Product>(`http://localhost:3002/products/code/${productCode}`)
      .subscribe({
        next: (product) => {
          console.log('Product fetched:', product);
          this.product = product;
        },
        error: (error) => {
          console.log('Error fetching product:', error);
        },
      });
  }

  fetchReviews(productCode: string): void {
    console.log('Fetching reviews...');
    this.reviewSubscription = this.http
      .get<Review[]>(`http://localhost:3002/reviews/${productCode}`)
      .subscribe({
        next: (reviews) => {
          console.log('Reviews fetched:', reviews);
          this.reviews = reviews;
        },
        error: (error) => {
          console.log('Error fetching reviews:', error);
        },
      });
  }

  ngOnDestroy(): void {
    if (this.productSubscription) {
      this.productSubscription.unsubscribe();
    }
    if (this.reviewSubscription) {
      this.reviewSubscription.unsubscribe();
    }
  }
}
