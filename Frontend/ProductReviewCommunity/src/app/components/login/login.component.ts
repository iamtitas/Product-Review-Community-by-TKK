import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  loginError: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}

  login(): void {
    const authenticationRequest = {
      username: this.username,
      password: this.password,
    };

    this.http
      .post<boolean>('http://localhost:3002/login', authenticationRequest)
      .subscribe({
        next: (response) => {
          if (response) {
            console.log('Login successful');
            alert('Login successful');
            const user = {
              ...authenticationRequest,
              isLoggedIn: true,
            };
            sessionStorage.setItem('user', JSON.stringify(user));
            this.router.navigate(['/']).then(() => {
              window.location.reload();
            });
          } else {
            console.log('Invalid credentials');
            this.loginError = true;
          }
        },
        error: (error) => {
          console.log('Login error:', error);
        },
      });
  }
}
