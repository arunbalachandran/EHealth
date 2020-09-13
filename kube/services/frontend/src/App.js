import React from 'react';
import logo from './logo.svg';

class App extends React.Component {
  render() {
    return (
      <div align="center">
        <h2>Welcome to the E-Health application</h2>
        <p>Login</p>
        <form action="#" method="post">
          <input type="text" name="emailid" placeholder="Email Id" />
          <input type="password" name="password" placeholder="Password" />
          <input type="submit" value="Login" />
          <p>OR</p>
        </form>
        {/* go to the doctors signup page */}
        <a href="/signup_d">Doctor Sign Up</a>
        {/* go to the patient signup page */}
        <a href="/signup_p">Patient Sign Up</a>
      </div>
    );
  }
}

export default App;
