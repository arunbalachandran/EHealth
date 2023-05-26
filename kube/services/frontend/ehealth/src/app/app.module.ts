import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { routing } from './app.routes';
import { SignupDoctorComponent } from './components/signup-doctor/signup-doctor.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { SignupPatientComponent } from './components/signup-patient/signup-patient.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupDoctorComponent,
    HomepageComponent,
    SignupPatientComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
