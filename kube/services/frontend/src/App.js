import React from 'react';
import logo from './logo.svg';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      emailid: "",
      password: "" 
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.value
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(event) {
    console.log("Email : " + this.state.emailid + " Password : " + this.state.password);
    console.log("Now trying the fetch api...")
    fetch("http://localhost:8080")
    .then(function(response) {
      return response.text();
    })
    .then(function(parsedData) {
      console.log("data from promise: " + parsedData);
    })
    event.preventDefault();
  }

  // https://reactjs.org/docs/forms.html
  render() {
    return (
      <div align="center">
        <h2>Welcome to the E-Health application</h2>
        <p>Login</p>
        <form action="#" method="post" onSubmit={this.handleSubmit}>
          <input type="text" name="emailid" value={this.state.emailid} onChange={this.handleInputChange} placeholder="Email Id" />
          <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange} placeholder="Password" />
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
