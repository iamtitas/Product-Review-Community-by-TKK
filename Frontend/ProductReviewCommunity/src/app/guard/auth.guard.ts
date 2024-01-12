import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const user = sessionStorage.getItem('user');

    if (user) {
      return true; // Allow access to the route
    } else {
      this.router.navigate(['/login']); // Redirect to the login page
      return false; // Deny access to the route
    }
  }
}
