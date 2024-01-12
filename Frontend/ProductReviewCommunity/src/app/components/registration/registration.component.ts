import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.sass'],
})
export class RegistrationComponent {
  name: string = '';
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  registerUser(): void {
    const registrationRequest = {
      name: this.name,
      email: this.email,
      password: this.password,
    };

    this.http
      .post('http://localhost:3002/register', registrationRequest)
      .subscribe({
        next: () => {
          console.log('User registered successfully');
          alert('User registered successfully');
          this.router.navigate(['/']);
        },
        error: (error: any) => {
          console.error('Registration failed:', error);
          console.log('Error status:', error.status);
          console.log('Error message:', error.error);
          alert('User registered successfully');
          this.router.navigate(['/']);
        },
      });
  }
}
