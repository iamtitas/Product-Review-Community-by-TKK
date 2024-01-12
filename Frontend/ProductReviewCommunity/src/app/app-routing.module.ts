import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SearchResultsComponent } from './components/search-result/search-result.component';
import { ProductPageComponent } from './components/product-details/product-details.component';
import { ReviewComponent } from './components/review/review.component';
import { AskReviewComponent } from './components/ask-review/ask-review.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  {
    path: 'product-details/:id',
    component: ProductPageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'add-review/:id',
    component: ReviewComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'search-results',
    component: SearchResultsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'ask-review',
    component: AskReviewComponent,
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
