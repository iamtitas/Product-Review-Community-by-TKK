import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.sass'],
})
export class SearchResultsComponent implements OnInit {
  searchQuery: string;
  searchResults: SearchResult[];

  constructor(private http: HttpClient, private route: ActivatedRoute) {
    this.searchQuery = '';
    this.searchResults = [];
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.searchQuery = params['q'] || '';
      this.search();
    });
  }

  generateCacheBuster(): string {
    return new Date().getTime().toString();
  }

  search(): void {
    const brandSearch$ = this.http
      .get<any[]>('http://localhost:3002/products/brand/' + this.searchQuery)
      .pipe(catchError(() => of(null)));
    const codeSearch$ = this.http
      .get<any>('http://localhost:3002/products/code/' + this.searchQuery)
      .pipe(catchError(() => of(null)));
    const nameSearch$ = this.http
      .get<any[]>('http://localhost:3002/products/name/' + this.searchQuery)
      .pipe(catchError(() => of(null)));

    forkJoin([brandSearch$, codeSearch$, nameSearch$]).subscribe({
      next: ([brandResults, codeResult, nameResults]) => {
        let foundResults = false;

        if (brandResults && brandResults.length > 0) {
          this.searchResults = brandResults.map((item: any) => ({
            productName: item.productName,
            brand: item.brand,
            productCode: item.productCode,
            price: item.price,
            averageRating: null,
            totalReviews: null,
          }));
          foundResults = true;
        } else if (codeResult && !Array.isArray(codeResult)) {
          const item = codeResult;
          this.searchResults = [
            {
              productName: item.productName,
              brand: item.brand,
              productCode: item.productCode,
              price: item.price,
              averageRating: null,
              totalReviews: null,
            },
          ];
          foundResults = true;
        } else if (nameResults && nameResults.length > 0) {
          this.searchResults = nameResults.map((item: any) => ({
            productName: item.productName,
            brand: item.brand,
            productCode: item.productCode,
            price: item.price,
            averageRating: null,
            totalReviews: null,
          }));
          foundResults = true;
        }

        if (!foundResults) {
          console.log('No results found');
        } else {
          this.searchResults.forEach((result) => {
            if (result.productCode) {
              this.getAverageRating(result.productCode);
              this.getTotalReviews(result.productCode);
            }
          });
        }
      },
      error: (error) => {
        console.log('Error searching products:', error);
      },
    });
  }

  getAverageRating(productCode: string): void {
    this.http
      .get<number>(
        'http://localhost:3002/reviews/average-rating/' + productCode
      )
      .subscribe({
        next: (averageRating) => {
          console.log('Average Rating:', averageRating);

          const result = this.searchResults.find(
            (r) => r.productCode === productCode
          );
          if (result) {
            result.averageRating = averageRating;
          }
        },
        error: (error) => {
          console.log('Error fetching average rating:', error);
        },
      });
  }

  getTotalReviews(productCode: string): void {
    this.http
      .get<number>('http://localhost:3002/reviews/total-reviews/' + productCode)
      .subscribe({
        next: (totalReviews) => {
          console.log('Total Reviews:', totalReviews);

          const result = this.searchResults.find(
            (r) => r.productCode === productCode
          );
          if (result) {
            result.totalReviews = totalReviews;
          }
        },
        error: (error) => {
          console.log('Error fetching total reviews:', error);
        },
      });
  }
}

interface SearchResult {
  productName: string;
  brand: string;
  productCode: string;
  price: number;
  averageRating: number | null;
  totalReviews: number | null;
}
