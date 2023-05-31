import { Component } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent {
  email: string = "";
  password: string = "";

  onSubmit() {
    // do nothing
  }
}
