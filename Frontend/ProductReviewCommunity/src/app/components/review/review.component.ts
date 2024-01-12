import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface Review {
  heading: string;
  rating: number;
  reviewText: string;
}

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.sass'],
})
export class ReviewComponent {
  review: Review = {
    heading: '',
    rating: 1,
    reviewText: '',
  };

  constructor(private http: HttpClient, private router: Router) {}

  submitReview(): void {
    const productCode = window.location.pathname.split('/').pop();
    const isReviewTextValid =
      this.review.reviewText.length >= 20 &&
      this.review.reviewText.length <= 400;

    if (!isReviewTextValid) {
      alert(
        'Heading and review text should be between 20 and 400 characters long.'
      );
      return;
    }

    this.http
      .post(`http://localhost:3002/reviews/${productCode}`, this.review)
      .subscribe({
        next: (response) => {
          console.log('Review submitted:', response);
          alert('Review submitted!');
          this.router.navigate(['/product-details', productCode]);
        },
        error: (error) => {
          console.log('Error submitting review:', error);
          alert('Could not submit review');
        },
      });
  }
}
