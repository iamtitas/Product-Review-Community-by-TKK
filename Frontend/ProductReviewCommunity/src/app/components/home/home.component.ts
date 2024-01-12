import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observer } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
})
export class HomeComponent implements OnInit {
  registeredUsersCount: number = 0;
  productsCount: number = 0;
  reviewsCount: number = 0;
  isLoggedIn: boolean = false;
  searchQuery: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.getHomePageStats();
    this.checkLoggedInStatus();
  }

  getHomePageStats(): void {
    this.http.get<any>('http://localhost:3002/api/stats/homepage').subscribe({
      next: (response) => {
        this.registeredUsersCount = response.userCount;
        this.productsCount = response.productCount;
        this.reviewsCount = response.reviewCount;
      },
      error: (error) => {
        console.log('Error getting homepage stats:', error);
      },
    });
  }

  checkLoggedInStatus(): boolean {
    const user = sessionStorage.getItem('user');

    if (user) {
      const parsedUser = JSON.parse(user);
      this.isLoggedIn = parsedUser.isLoggedIn;
      return this.isLoggedIn;
    }

    return false;
  }

  search() {
    this.router.navigate(['/search-results'], {
      queryParams: { q: this.searchQuery },
    });
  }
}
