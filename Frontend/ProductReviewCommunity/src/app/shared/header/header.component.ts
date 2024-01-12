import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  username: string = '';

  ngOnInit(): void {
    this.checkLoggedInStatus();
  }

  constructor(private router: Router) {}

  navigateToLogin() {
    this.router.navigate(['/login']);
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }

  checkLoggedInStatus(): void {
    const user = sessionStorage.getItem('user');

    if (user) {
      const parsedUser = JSON.parse(user);
      this.isLoggedIn = parsedUser.isLoggedIn;
      this.username = parsedUser.username;
    }
  }

  logout(): void {
    sessionStorage.removeItem('user');
    this.isLoggedIn = false;
    this.router.navigate(['/']);
    window.location.reload();
  }
}
